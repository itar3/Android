package com.example.myapplication.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;



@Entity(tableName = "Items")
public class Item {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "itemId")
    private int id;

    @ColumnInfo(name = "itemName")
    private String name;
    @ColumnInfo(name = "itemPrice")
    private String price;
    @ColumnInfo(name = "itemQuantity")
    private String quantity;
    @ColumnInfo(name = "itemDescription")
    private String description;
    @ColumnInfo(name = "itemFrozen")
    private String frozen;






    public Item(String name, String price, String quantity, String description, String frozen){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.frozen = frozen;


    }



    public int getId(){
        return id;
    }
    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;

    }
    public String getPrice(){
        return price;
    }
    public void setPrice(String price){
        this.name = price;

    }
    public String getQuantity(){
        return quantity;
    }
    public void setQuantity(String quantity){
        this.quantity = quantity;

    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;

    }
    public String getFrozen(){
        return frozen;
    }
    public void setFrozen(String frozen){
        this.frozen = frozen;

    }


}
