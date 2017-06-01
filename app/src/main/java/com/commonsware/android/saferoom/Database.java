package com.commonsware.android.saferoom;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import net.sqlcipher.database.SQLiteCursor;
import net.sqlcipher.database.SQLiteCursorDriver;
import net.sqlcipher.database.SQLiteQuery;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteTransactionListener;

public class Database implements SupportSQLiteDatabase {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private final net.sqlcipher.database.SQLiteDatabase safeDb;

    public Database(net.sqlcipher.database.SQLiteDatabase safeDb) {
        this.safeDb = safeDb;
    }

    @Override
    public SupportSQLiteStatement compileStatement(String sql) {
        return (new Statement(safeDb.compileStatement(sql)));
    }

    @Override
    public void beginTransaction() {
        safeDb.beginTransaction();
    }

    @Override
    public void beginTransactionNonExclusive() {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
    }

    @Override
    public void beginTransactionWithListener(
            SQLiteTransactionListener transactionListener) {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
    }

    @Override
    public void beginTransactionWithListenerNonExclusive(
            SQLiteTransactionListener transactionListener) {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
    }

    @Override
    public void endTransaction() {
        safeDb.endTransaction();
    }

    @Override
    public void setTransactionSuccessful() {
        safeDb.setTransactionSuccessful();
    }

    @Override
    public boolean inTransaction() {
        return (safeDb.inTransaction());
    }

    @Override
    public boolean isDbLockedByCurrentThread() {
        return (safeDb.isDbLockedByCurrentThread());
    }

    @Override
    public boolean yieldIfContendedSafely() {
        return (safeDb.yieldIfContendedSafely());
    }

    @Override
    public boolean yieldIfContendedSafely(long sleepAfterYieldDelay) {
        return (safeDb.yieldIfContendedSafely(sleepAfterYieldDelay));
    }

    @Override
    public int getVersion() {
        return (safeDb.getVersion());
    }

    @Override
    public void setVersion(int version) {
        safeDb.setVersion(version);
    }

    @Override
    public long getMaximumSize() {
        return (safeDb.getMaximumSize());
    }

    @Override
    public long setMaximumSize(long numBytes) {
        return (safeDb.setMaximumSize(numBytes));
    }

    @Override
    public long getPageSize() {
        return (safeDb.getPageSize());
    }

    @Override
    public void setPageSize(long numBytes) {
        safeDb.setPageSize(numBytes);
    }

    @Override
    public Cursor query(boolean distinct, String table, String[] columns,
                        String selection, String[] selectionArgs, String groupBy,
                        String having, String orderBy, String limit) {
        return (safeDb.query(distinct, table, columns, selection, selectionArgs,
                groupBy, having, orderBy, limit));
    }

    @Override
    public Cursor query(boolean distinct, String table, String[] columns,
                        String selection, String[] selectionArgs, String groupBy,
                        String having, String orderBy, String limit,
                        CancellationSignal cancellationSignal) {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
/*
    return(safeDb.query(distinct, table, columns, selection, selectionArgs,
      groupBy, having, orderBy, limit, cancellationSignal));
*/
    }

    @Override
    public Cursor queryWithFactory(SQLiteDatabase.CursorFactory cursorFactory,
                                   boolean distinct, String table,
                                   String[] columns, String selection,
                                   String[] selectionArgs, String groupBy,
                                   String having, String orderBy, String limit) {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
    }

    @Override
    public Cursor queryWithFactory(SQLiteDatabase.CursorFactory cursorFactory,
                                   boolean distinct, String table,
                                   String[] columns, String selection,
                                   String[] selectionArgs, String groupBy,
                                   String having, String orderBy, String limit,
                                   CancellationSignal cancellationSignal) {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
    }

    @Override
    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        return (safeDb.query(table, columns, selection, selectionArgs,
                groupBy, having, orderBy));
    }

    @Override
    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy, String limit) {
        return (safeDb.query(table, columns, selection, selectionArgs,
                groupBy, having, orderBy, limit));
    }

    @Override
    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return (safeDb.rawQuery(sql, selectionArgs));
    }

    @Override
    public Cursor rawQuery(String sql, String[] selectionArgs, CancellationSignal cancellationSignal) {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
//    return(safeDb.rawQuery(sql, selectionArgs, cancellationSignal));
    }

    @Override
    public Cursor rawQueryWithFactory(SQLiteDatabase.CursorFactory cursorFactory,
                                      String sql, String[] selectionArgs,
                                      String editTable) {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
    }

    @Override
    public Cursor rawQueryWithFactory(SQLiteDatabase.CursorFactory cursorFactory,
                                      String sql, String[] selectionArgs,
                                      String editTable,
                                      CancellationSignal cancellationSignal) {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
    }

    @Override
    public Cursor rawQuery(final SupportSQLiteQuery supportQuery) {
        return (safeDb.rawQueryWithFactory(
                new net.sqlcipher.database.SQLiteDatabase.CursorFactory() {
                    @Override
                    public net.sqlcipher.Cursor newCursor(
                            net.sqlcipher.database.SQLiteDatabase db,
                            SQLiteCursorDriver masterQuery, String editTable,
                            SQLiteQuery query) {
                        supportQuery.bindTo(new Program(query));
                        return new SQLiteCursor(db, masterQuery, editTable, query);
                    }
                }, supportQuery.getSql(), EMPTY_STRING_ARRAY, null));
    }

    @Override
    public long insert(String table, String nullColumnHack,
                       ContentValues values) {
        return (safeDb.insert(table, nullColumnHack, values));
    }

    @Override
    public long insertOrThrow(String table, String nullColumnHack,
                              ContentValues values) throws SQLException {
        return (safeDb.insertOrThrow(table, nullColumnHack, values));
    }

    @Override
    public long replace(String table, String nullColumnHack,
                        ContentValues initialValues) {
        return (safeDb.replace(table, nullColumnHack, initialValues));
    }

    @Override
    public long replaceOrThrow(String table, String nullColumnHack,
                               ContentValues initialValues) throws SQLException {
        return (safeDb.replaceOrThrow(table, nullColumnHack, initialValues));
    }

    @Override
    public long insertWithOnConflict(String table, String nullColumnHack,
                                     ContentValues initialValues,
                                     int conflictAlgorithm) {
        return (safeDb.insertWithOnConflict(table, nullColumnHack, initialValues, conflictAlgorithm));
    }

    @Override
    public int delete(String table, String whereClause, String[] whereArgs) {
        return (safeDb.delete(table, whereClause, whereArgs));
    }

    @Override
    public int update(String table, ContentValues values, String whereClause,
                      String[] whereArgs) {
        return (safeDb.update(table, values, whereClause, whereArgs));
    }

    @Override
    public int updateWithOnConflict(String table, ContentValues values,
                                    String whereClause, String[] whereArgs,
                                    int conflictAlgorithm) {
        return (safeDb.updateWithOnConflict(table, values, whereClause, whereArgs, conflictAlgorithm));
    }

    @Override
    public void execSQL(String sql) throws SQLException {
        safeDb.execSQL(sql);
    }

    @Override
    public void execSQL(String sql, Object[] bindArgs) throws SQLException {
        safeDb.execSQL(sql, bindArgs);
    }

    @Override
    public void validateSql(@NonNull String sql,
                            @Nullable CancellationSignal cancellationSignal) {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
//    safeDb.validateSql(sql, cancellationSignal);
    }

    @Override
    public boolean isReadOnly() {
        return (safeDb.isReadOnly());
    }

    @Override
    public boolean isOpen() {
        return (safeDb.isOpen());
    }

    @Override
    public boolean needUpgrade(int newVersion) {
        return (safeDb.needUpgrade(newVersion));
    }

    @Override
    public String getPath() {
        return (safeDb.getPath());
    }

    @Override
    public void setLocale(Locale locale) {
        safeDb.setLocale(locale);
    }

    @Override
    public void setMaxSqlCacheSize(int cacheSize) {
        safeDb.setMaxSqlCacheSize(cacheSize);
    }

    @Override
    public void setForeignKeyConstraintsEnabled(boolean enable) {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
//    safeDb.setForeignKeyConstraintsEnabled(enable);
    }

    @Override
    public boolean enableWriteAheadLogging() {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
//    return(safeDb.enableWriteAheadLogging());
    }

    @Override
    public void disableWriteAheadLogging() {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
//    safeDb.disableWriteAheadLogging();
    }

    @Override
    public boolean isWriteAheadLoggingEnabled() {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
//    return(safeDb.isWriteAheadLoggingEnabled());
    }

    @Override
    public List<Pair<String, String>> getAttachedDbs() {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
//    return(safeDb.getAttachedDbs());
    }

    @Override
    public boolean isDatabaseIntegrityOk() {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
//    return(safeDb.isDatabaseIntegrityOk());
    }

    @Override
    public void close() throws IOException {
        safeDb.close();
    }
}
