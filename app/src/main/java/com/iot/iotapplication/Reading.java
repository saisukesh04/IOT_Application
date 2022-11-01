package com.iot.iotapplication;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "readingDb")
public class Reading {

    private String created_at, field1, field2;
    @PrimaryKey
    private int entry_id;

    @Ignore
    public Reading() {
    }

    public Reading(String created_at, String field1, String field2, int entry_id) {
        this.created_at = created_at;
        this.field1 = field1;
        this.field2 = field2;
        this.entry_id = entry_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public int getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(int entry_id) {
        this.entry_id = entry_id;
    }
}
