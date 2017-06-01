/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.commonsware.android.saferoom;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

class Helper implements SupportSQLiteOpenHelper {
    private final OpenHelper mDelegate;
    private final char[]     passphrase;

    Helper(Context context, String name,
           int version,
           SupportSQLiteOpenHelper.Callback callback,
           char[] passphrase) {
        SQLiteDatabase.loadLibs(context);
        mDelegate = createDelegate(context, name, version, callback);
        this.passphrase = passphrase;
    }

    private OpenHelper createDelegate(Context context, String name,
                                      int version,
                                      final Callback callback) {
        return new OpenHelper(context, name, version) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                mWrappedDb = new Database(sqLiteDatabase);
                callback.onCreate(mWrappedDb);
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
                callback.onUpgrade(getWrappedDb(sqLiteDatabase), oldVersion, newVersion);
            }

/*
      @Override
      public void onConfigure(SQLiteDatabase db) {
        callback.onConfigure(getWrappedDb(db));
      }

      @Override
      public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        callback.onDowngrade(getWrappedDb(db), oldVersion, newVersion);
      }
*/

            @Override
            public void onOpen(SQLiteDatabase db) {
                callback.onOpen(getWrappedDb(db));
            }
        };
    }

    @Override
    public String getDatabaseName() {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
//    return mDelegate.getDatabaseName();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setWriteAheadLoggingEnabled(boolean enabled) {
        // TODO not supported in SQLCipher for Android
        throw new UnsupportedOperationException("I kinna do it, cap'n!");
//    mDelegate.setWriteAheadLoggingEnabled(enabled);
    }

    @Override
    public SupportSQLiteDatabase getWritableDatabase() {
        SupportSQLiteDatabase result =
                mDelegate.getWritableSupportDatabase(passphrase);

        for (int i = 0; i < passphrase.length; i++) {
            passphrase[i] = (char) 0;
        }

        return (result);
    }

    @Override
    public SupportSQLiteDatabase getReadableDatabase() {
        //return mDelegate.getReadableSupportDatabase();
        return (getWritableDatabase());
    }

    @Override
    public void close() {
        mDelegate.close();
    }

    abstract static class OpenHelper extends SQLiteOpenHelper {

        Database mWrappedDb;

        OpenHelper(Context context, String name,
                   int version) {
            super(context, name, null, version, null);
        }

        SupportSQLiteDatabase getWritableSupportDatabase(char[] passphrase) {
            SQLiteDatabase db = super.getWritableDatabase(passphrase);
            return getWrappedDb(db);
        }

        SupportSQLiteDatabase getReadableSupportDatabase(char[] passphrase) {
            SQLiteDatabase db = super.getReadableDatabase(passphrase);
            return getWrappedDb(db);
        }

        Database getWrappedDb(SQLiteDatabase sqLiteDatabase) {
            if (mWrappedDb == null) {
                mWrappedDb = new Database(sqLiteDatabase);
            }
            return mWrappedDb;
        }

        @Override
        public synchronized void close() {
            super.close();
            mWrappedDb = null;
        }
    }
}
