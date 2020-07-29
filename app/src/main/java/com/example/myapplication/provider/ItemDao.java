package com.example.myapplication.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("select * from Items")
    LiveData<List<Item>> getAllItems();

    @Query("select * from Items where itemName = :name")
    List<Item> getItem(String name);

    @Insert
    void addItem(Item item);

    @Query("delete from Items where itemName = :name")
    void deleteItem(String name);

    @Query("delete from Items")
    void deleteAllItems();

}
