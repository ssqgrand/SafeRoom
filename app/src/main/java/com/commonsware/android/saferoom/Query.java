package com.commonsware.android.saferoom;

import android.arch.persistence.db.SupportSQLiteProgram;
import android.arch.persistence.db.SupportSQLiteQuery;

import net.sqlcipher.database.SQLiteQuery;

public class Query implements SupportSQLiteQuery {
    private final SQLiteQuery safeQuery;
    private final String      query;

    public Query(String query, SQLiteQuery safeQuery) {
        this.query = query;
        this.safeQuery = safeQuery;
    }

    @Override
    public String getSql() {
        return (query);
    }

    @Override
    public void bindTo(SupportSQLiteProgram statement) {

    }
}
