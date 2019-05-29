package com.example.listyourcar.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.listyourcar.db.typeconverters.DateTypeConverter;
import com.example.listyourcar.models.Contact;


@Database(entities = {Contact.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDAO getContactDAO();
}
