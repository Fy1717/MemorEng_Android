package com.example.memorengandroid.model.Repository.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static UserRoomDatabase userRoomDatabase;

    public static synchronized UserRoomDatabase getDatabase(final Context context) {
        try {
            if (userRoomDatabase == null) {
                synchronized (UserRoomDatabase.class) {
                    if (userRoomDatabase == null) {
                        userRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                        UserRoomDatabase.class, "memoreng_database")
                                .allowMainThreadQueries().build();
                    }
                }
            }

            return userRoomDatabase;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
