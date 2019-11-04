package com.example.expensetrackingsystem.MyExpenseHistory.DTO;

public class MyExpenseHisotryDTO {

    String name;
    String particulars;
    String price;


    public String getParticulars() {
        return particulars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
