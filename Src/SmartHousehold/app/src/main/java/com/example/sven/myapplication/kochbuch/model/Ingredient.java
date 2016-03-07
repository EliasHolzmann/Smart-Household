package com.example.sven.myapplication.kochbuch.model;

import java.io.Serializable;

/**
 * Created by elias on 28.02.16.
 */
public class Ingredient implements Serializable {
    public final int amount;
    public final String amountType;
    public final String name;
    public final int price;
    public final String priceType;
    public Ingredient(int amount, String amountType, String name, int price, String priceType) {
        this.amount = amount;
        this.amountType = amountType;
        this.name = name;
        this.price = price;
        this.priceType = priceType;
    }

}
