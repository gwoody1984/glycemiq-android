package com.eveningoutpost.dexdrip.Glycemiq.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by woodb on 7/19/2017.
 */
public class TotalNutrients {

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
