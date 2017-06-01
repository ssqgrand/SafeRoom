package com.commonsware.android.saferoom;

import android.arch.persistence.db.SupportSQLiteOpenHelper;

public class HelperFactory implements SupportSQLiteOpenHelper.Factory {
    final private char[] passphrase;

    public HelperFactory(char[] passphrase) {
        this.passphrase = passphrase;
    }

    @Override
    public SupportSQLiteOpenHelper create(
            SupportSQLiteOpenHelper.Configuration configuration) {
        return (new Helper(configuration.context, configuration.name,
                configuration.version, configuration.callback, passphrase));
    }
}
