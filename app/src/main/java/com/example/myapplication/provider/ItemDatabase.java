package com.example.myapplication.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Item.class}, version = 2, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {

    public static final String ITEM_DATABASE_NAME = "item_database";

    public abstract ItemDao itemDao();

    private static volatile ItemDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ItemDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemDatabase.class, ITEM_DATABASE_NAME).fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
