package com.example.myapplication.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class itemRepository {

    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;

    itemRepository(Application application) {
        ItemDatabase db = ItemDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItems();
    }

    LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    void insert(Item item) {
        ItemDatabase.databaseWriteExecutor.execute(() -> mItemDao.addItem(item));
    }

    void deleteAll(){
        ItemDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.deleteAllItems();
        });
    }
}
