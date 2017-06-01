package info.hannes.saferoom;

import android.arch.persistence.db.SupportSQLiteProgram;

import net.sqlcipher.database.SQLiteProgram;

class Program implements SupportSQLiteProgram {
    private final SQLiteProgram delegate;

    Program(SQLiteProgram delegate) {
        this.delegate = delegate;
    }

    @Override
    public void bindNull(int index) {
        delegate.bindNull(index);
    }

    @Override
    public void bindLong(int index, long value) {
        delegate.bindLong(index, value);
    }

    @Override
    public void bindDouble(int index, double value) {
        delegate.bindDouble(index, value);
    }

    @Override
    public void bindString(int index, String value) {
        delegate.bindString(index, value);
    }

    @Override
    public void bindBlob(int index, byte[] value) {
        delegate.bindBlob(index, value);
    }

    @Override
    public void clearBindings() {
        delegate.clearBindings();
    }
}
