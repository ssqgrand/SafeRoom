package info.hannes.saferoom;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.os.ParcelFileDescriptor;

import net.sqlcipher.database.SQLiteStatement;

public class Statement implements SupportSQLiteStatement {
    private final SQLiteStatement safeStatement;

    Statement(SQLiteStatement safeStatement) {
        this.safeStatement = safeStatement;
    }

    @Override
    public void execute() {
        safeStatement.execute();
    }

    @Override
    public int executeUpdateDelete() {
        return (safeStatement.executeUpdateDelete());
    }

    @Override
    public long executeInsert() {
        return (safeStatement.executeInsert());
    }

    @Override
    public long simpleQueryForLong() {
        return (safeStatement.simpleQueryForLong());
    }

    @Override
    public String simpleQueryForString() {
        return (safeStatement.simpleQueryForString());
    }

    @Override
    public ParcelFileDescriptor simpleQueryForBlobFileDescriptor() {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
    }

    @Override
    public void bindNull(int index) {
        safeStatement.bindNull(index);
    }

    @Override
    public void bindLong(int index, long value) {
        safeStatement.bindLong(index, value);
    }

    @Override
    public void bindDouble(int index, double value) {
        safeStatement.bindDouble(index, value);
    }

    @Override
    public void bindString(int index, String value) {
        safeStatement.bindString(index, value);
    }

    @Override
    public void bindBlob(int index, byte[] value) {
        safeStatement.bindBlob(index, value);
    }

    @Override
    public void clearBindings() {
        safeStatement.clearBindings();
    }
}
