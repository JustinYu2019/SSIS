package com.example.ssis;

import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Requisition implements Serializable {
    private String name;  // Employee name

    // use datetime formatter to do it
    private String raiseDate; // date of requisition
    private ArrayList<Item> itemList; // list of item raised

    public Requisition(String name, String raiseDate, ArrayList<Item> itemList) {
        this.name = name;
        this.raiseDate = raiseDate;
        this.itemList = itemList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRaiseDate() {
        return raiseDate;
    }

    public void setRaiseDate(String raiseDate) {
        this.raiseDate = raiseDate;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }


}
