package com.denarced.repository;

import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.query.Mapper;
import org.codejargon.fluentjdbc.api.query.UpdateResult;
import org.infinispan.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class NoteRepositoryImpl implements NoteRepository {
    private static final String INSERT_SQL = insertSql();
    private static final String FETCH_WITH_UUID_SQL = fetchNoteWithUuidSql();
    private static final Mapper<Note> HALF_NOTE_MAPPER = createHalfNoteMapper();
    private static final Mapper<Note> NOTE_MAPPER = createNoteMapper();

    @Autowired
    private Cache<String,Note> cache;
    @Autowired
    private FluentJdbc fluent;

    @Override
    public void addNote(Note note) {
        final String uuid = UUID.randomUUID().toString();
        UpdateResult result = fluent
            .query()
            .update(INSERT_SQL)
            .namedParam("uuid", uuid)
            .namedParam("title", note.getTitle())
            .namedParam("value", note.getValue())
            .run();
        if (0 == result.affectedRows()) {
            throw new RuntimeException("Insert failed");
        }

        note.setUuid(uuid);
        cache.put(uuid, note);
    }

    @Override
    public List<Note> fetchNotes() {
        return fluent
            .query()
            .select("select c_uuid, c_title from t_note;")
            .listResult(HALF_NOTE_MAPPER);
    }

    @Override
    public Note fetchNote(String uuid) {
        Note note = cache.get(uuid);
        if (note == null) {
            note = fluent
                .query()
                .select(FETCH_WITH_UUID_SQL)
                .namedParam("uuid", uuid)
                .singleResult(NOTE_MAPPER);
        }
        // Use putForExternalRead instead of put because in this
        // way matching cache entries on other nodes are not
        // invalidated. There's no reason for it anyway because
        // the note's value isn't changed, we have merely also
        // fetched it into cache in another node.
        cache.putForExternalRead(uuid, note);
        return note;
    }

    private static String insertSql() {
        return "insert into t_note (c_uuid, c_title, c_value) \n" +
            "values (CAST(:uuid as uuid), :title, :value);";
    }

    private static String fetchNoteWithUuidSql() {
        return "select c_uuid, c_title, c_value " +
            "from t_note " +
            "where c_uuid=CAST(:uuid as uuid)";
    }

    private static Mapper<Note> createHalfNoteMapper() {
        return rs -> new Note(
            rs.getString("c_uuid"),
            rs.getString("c_title"),
            null);
    }

    private static Mapper<Note> createNoteMapper() {
        return rs -> new Note(
            rs.getString("c_uuid"),
            rs.getString("c_title"),
            rs.getString("c_value"));
    }
}
