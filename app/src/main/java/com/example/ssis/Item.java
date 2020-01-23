package com.example.ssis;

import java.io.Serializable;

public class Item implements Serializable {
    private String description;
    private int unit;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}
