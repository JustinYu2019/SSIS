package com.example.ssis;

import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Requisition implements Serializable {
    private int requisitionId;
    private String name;  // Employee name
    private String remarks;
    // use datetime formatter to do it
    private String raiseDate; // date of requisition
    private ArrayList<Item> itemList; // list of item raised

    public Requisition(String name,String raiseDate){
        this.name=name;
        this.raiseDate=raiseDate;
        // for notification page where name and date is required only
    }
    public Requisition(String name, String raiseDate, ArrayList<Item> itemList) {
        this.name = name;
        this.raiseDate = raiseDate;
        this.itemList = itemList;
    }
    public void setReqId(int requisitionId) {
        this.requisitionId=requisitionId;
    }
    public int getReqId() {
        return requisitionId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
