package com.denarced.repository;

import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.query.Mapper;
import org.codejargon.fluentjdbc.api.query.UpdateResult;
import org.infinispan.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class NoteRepositoryImpl implements NoteRepository {
    private static final String INSERT_SQL = insertSql();
    private static final Mapper<Note> MAPPER = createMapper();

    @Autowired
    private Cache<String,String> cache;
    @Autowired
    private FluentJdbc fluent;

    private boolean cacheInitialized = false;

    @Override
    public void addNote(String value) {
        final String uuid = UUID.randomUUID().toString();
        UpdateResult result = fluent
            .query()
            .update(INSERT_SQL)
            .namedParam("uuid", uuid)
            .namedParam("value", value)
            .run();
        if (0 == result.affectedRows()) {
            throw new RuntimeException("Insert failed");
        }

        cache.put(uuid, value);
    }

    @Override
    public List<String> fetchNotes() {
        if (cacheInitialized) {
            return new ArrayList<>(cache.values());
        }

        fluent
            .query()
            .select("select c_uuid, c_value from t_note;")
            .listResult(MAPPER)
            .forEach(note -> cache.put(note.getUuid(), note.getValue()));

        cacheInitialized = true;
        return new ArrayList<>(cache.values());
    }

    private static String insertSql() {
        return "insert into t_note (c_uuid, c_value) \n" +
            "values (CAST(:uuid as uuid), :value);";
    }

    private static Mapper<Note> createMapper() {
        return rs -> new Note(
            rs.getString("c_uuid"),
            rs.getString("c_value"));
    }
}
