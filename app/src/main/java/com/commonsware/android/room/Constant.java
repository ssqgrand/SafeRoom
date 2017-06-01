package com.commonsware.android.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "constants")
public class Constant {
    @PrimaryKey(autoGenerate = true)
    public int id;
    String title;
    double value;

    @Override
    public String toString() {
        return (title);
    }
}
