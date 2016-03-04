package com.example.sven.myapplication.kochbuch.model;

import java.io.Serializable;

/**
 * Created by elias on 28.02.16.
 */
public class Ingredient implements Serializable {
    public final int amount;
    public final int amountType;
    public final String name;
    public final int price;
    public final int priceType;
    public Ingredient(int amount, int amountType, String name, int price, int priceType) {
        this.amount = amount;
        this.amountType = amountType;
        this.name = name;
        this.price = price;
        this.priceType = priceType;
    }

}
