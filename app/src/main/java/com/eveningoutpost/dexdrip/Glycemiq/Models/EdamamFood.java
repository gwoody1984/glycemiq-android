package com.eveningoutpost.dexdrip.Glycemiq.Models;

/**
 * Created by woodb on 7/19/2017.
 */


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdamamFood {

    private String uri;
    private Integer calories;
    private Double glycemicIndex;
    private Double totalWeight;
    private List<Object> dietLabels = null;
    private List<Object> healthLabels = null;
    private List<Object> cautions = null;
    private TotalNutrients totalNutrients;
    private TotalDaily totalDaily;
    private List<Ingredient> ingredients = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Double getGlycemicIndex() {
        return glycemicIndex;
    }

    public void setGlycemicIndex(Double glycemicIndex) {
        this.glycemicIndex = glycemicIndex;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public List<Object> getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(List<Object> dietLabels) {
        this.dietLabels = dietLabels;
    }

    public List<Object> getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(List<Object> healthLabels) {
        this.healthLabels = healthLabels;
    }

    public List<Object> getCautions() {
        return cautions;
    }

    public void setCautions(List<Object> cautions) {
        this.cautions = cautions;
    }

    public TotalNutrients getTotalNutrients() {
        return totalNutrients;
    }

    public void setTotalNutrients(TotalNutrients totalNutrients) {
        this.totalNutrients = totalNutrients;
    }

    public TotalDaily getTotalDaily() {
        return totalDaily;
    }

    public void setTotalDaily(TotalDaily totalDaily) {
        this.totalDaily = totalDaily;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
