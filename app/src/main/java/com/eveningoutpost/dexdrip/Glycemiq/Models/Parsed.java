package com.eveningoutpost.dexdrip.Glycemiq.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by woodb on 7/19/2017.
 */
public class Parsed {

    private Double quantity;
    private String measure;
    private String foodMatch;
    private String food;
    private String foodId;
    private String foodURI;
    private Double weight;
    private Double retainedWeight;
    private Nutrients nutrients;
    private String measureURI;
    private String status;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getFoodMatch() {
        return foodMatch;
    }

    public void setFoodMatch(String foodMatch) {
        this.foodMatch = foodMatch;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodURI() {
        return foodURI;
    }

    public void setFoodURI(String foodURI) {
        this.foodURI = foodURI;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getRetainedWeight() {
        return retainedWeight;
    }

    public void setRetainedWeight(Double retainedWeight) {
        this.retainedWeight = retainedWeight;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }

    public String getMeasureURI() {
        return measureURI;
    }

    public void setMeasureURI(String measureURI) {
        this.measureURI = measureURI;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
