package com.example.currency3.pojo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Currency {
    @SerializedName("alpha3")
    @Expose
    private String alpha3;
    @SerializedName("currencyId")
    @Expose
    private String currencyId;
    @SerializedName("currencyName")
    @Expose
    private String currencyName;
    @SerializedName("currencySymbol")
    @Expose
    private String currencySymbol;
    @PrimaryKey
    @SerializedName("id")
    @Expose
    @NonNull
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
