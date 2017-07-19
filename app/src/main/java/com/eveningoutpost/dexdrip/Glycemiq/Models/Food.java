package com.eveningoutpost.dexdrip.Glycemiq.Models;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Greg on 7/18/2017.
 */

@Table(name = "Food", id = BaseColumns._ID)
public class Food extends Model {

    @Column(name="Quantity")
    public double quantity;

    @Column(name="measurement")
    public String measurement;

    @Column(name="description")
    public String description;

    @Column(name="carbs")
    public double carbs;

    @Column(name="proteins")
    public double proteins;

    @Column(name="fats")
    public double fats;

    @Column(name="timestamp")
    public String timestamp;

    public Food(double quantity, String measurement, String description, double carbs, double proteins,
                double fats, String timestamp){
        this.quantity = quantity;
        this.measurement = measurement;
        this.description = description;
        this.carbs = carbs;
        this.proteins = proteins;
        this.fats = fats;
        this.timestamp = timestamp;
    }
}
