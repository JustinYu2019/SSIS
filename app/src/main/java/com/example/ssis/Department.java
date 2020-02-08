package com.example.ssis;

import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {
    // collectionPoint is string instead of CollectionPoint c
    private String deptName;
    //private String collectionPoint;
    private String deptRep;
    private String disbursementId;
    private String contactNumber;
    private ArrayList<Item> itemList;


    public Department(){

    }

    public Department(String deptName, String deptRep, String disbursementId, String contactNumber, ArrayList<Item> itemList) {
        this.deptName=deptName;
        //this.collectionPoint = collectionPoint;
        this.deptRep = deptRep;
        this.disbursementId = disbursementId;
        this.contactNumber = contactNumber;
        this.itemList=itemList;
    }

    // getters setters
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

   /* public String getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }*/

    public String getDeptRep() {
        return deptRep;
    }

    public void setDeptRep(String deptRep) {
        this.deptRep = deptRep;
    }

    public String getDisbursementId() {
        return disbursementId;
    }

    public void setDisbursementId(String disbursementId) {
        this.disbursementId = disbursementId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
