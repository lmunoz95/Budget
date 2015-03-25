package com.cyanococcus.budget.app.data;

public class Expense {
    private long id;
    private String location;
    private double quantity;
    private long date;
    private String description;

    public Expense() {  }

    public Expense(long id, String location, double quantity, long date, String description) {
        this.id = id;
        this.location = location;
        this.quantity = quantity;
        this.date = date;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
