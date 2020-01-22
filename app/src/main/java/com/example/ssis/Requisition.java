package com.example.ssis;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Requisition {
    private String name;  // Employee name
    private LocalDateTime raiseDate; // date of requisition
    private ArrayList<Item> itemList; // list of item raised

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getRaiseDate() {
        return raiseDate;
    }

    public void setRaiseDate(LocalDateTime raiseDate) {
        this.raiseDate = raiseDate;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }
}
