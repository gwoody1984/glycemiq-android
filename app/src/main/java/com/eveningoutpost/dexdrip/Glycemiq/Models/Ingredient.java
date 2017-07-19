package com.eveningoutpost.dexdrip.Glycemiq.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by woodb on 7/19/2017.
 */
public class Ingredient {

    private String text;
    private List<Parsed> parsed = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Parsed> getParsed() {
        return parsed;
    }

    public void setParsed(List<Parsed> parsed) {
        this.parsed = parsed;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
