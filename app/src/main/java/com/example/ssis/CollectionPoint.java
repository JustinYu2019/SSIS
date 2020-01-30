package com.example.ssis;

import java.io.Serializable;
import java.util.ArrayList;

public class CollectionPoint implements Serializable {
    private String name;
    private ArrayList<Department> departmentList;


    public CollectionPoint(){

    }

    public CollectionPoint(String name, ArrayList<Department> departmentList) {
        this.name = name;
        this.departmentList = departmentList;
    }

    // getters setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(ArrayList<Department> departmentList) {
        this.departmentList = departmentList;
    }
}
