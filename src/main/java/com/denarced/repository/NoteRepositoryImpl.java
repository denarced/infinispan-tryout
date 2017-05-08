package com.denarced.repository;

import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class NoteRepositoryImpl implements NoteRepository {
    private static final String INSERT_SQL = insertSql();

    @Autowired
    private FluentJdbc fluent;

    @Override
    public void addNote(String value) {
        UpdateResult result = fluent
            .query()
            .update(INSERT_SQL)
            .namedParam("uuid", UUID.randomUUID().toString())
            .namedParam("value", value)
            .run();
        if (0 == result.affectedRows()) {
            throw new RuntimeException("Insert failed");
        }
    }

    @Override
    public List<String> fetchNotes() {
        return fluent
            .query()
            .select("select c_value from t_note;")
            .listResult(Mappers.singleString());
    }

    private static String insertSql() {
        return "insert into t_note (c_uuid, c_value) \n" +
            "values (CAST(:uuid as uuid), :value);";
    }
}
