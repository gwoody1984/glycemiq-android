/*
 * Copyright (C) 2016 Saurabh Rane
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.eveningoutpost.dexdrip.Glycemiq.FoodApi.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eveningoutpost.dexdrip.Glycemiq.FoodApi.model.Food;
import com.eveningoutpost.dexdrip.Glycemiq.FoodApi.model.Serving;


/**
 * This utility class helps to get detailed information about food item(s) from fatsecret rest api
 *
 * @author Saurabh Rane
 * @version 2.0
 */
public class FoodUtility {

    /**
     * Returns detailed information about the food
     *
     * @param json json object representing of the food
     * @return detailed information about the food
     */
    public static Food parseFoodFromJSONObject(JSONObject json) {
        String name = "";
        try {
            name = json.getString("food_name");
        } catch (JSONException ignore) {
        }
        String url = "";
        try {
            url = json.getString("food_url");
        } catch (JSONException ignore) {
        }
        String type = "";
        try {
            type = json.getString("food_type");
        } catch (JSONException ignore) {
        }
        Long id = 0L;
        try {
            id = Long.parseLong(json.getString("food_id"));
        } catch (JSONException ignore) {
        }
        String brandName = "";

        try {
            brandName = json.getString("brand_name");
        } catch (Exception ignore) {
        }

        JSONObject servingsObj = null;
        try {
            servingsObj = json.getJSONObject("servings");
        }
        catch (JSONException ignore){
        }

        JSONArray array = null;
        List<Serving> servings = new ArrayList<Serving>();

        try {
            array = servingsObj.getJSONArray("serving");
            servings = ServingUtility.parseServingsFromJSONArray(array);
        } catch (Exception ignore) {
            System.out.println("Servings not found");
            array = null;
        }

        if (array == null) {
            try {
                JSONObject servingObj = servingsObj.getJSONObject("serving");
                Serving serving = ServingUtility.parseServingFromJSONObject(servingObj);
                servings.add(serving);
            } catch (Exception ignore) {
                System.out.println("Serving not found");
            }
        }

        Food food = new Food();

        food.setName(name);
        food.setUrl(url);
        food.setType(type);
        food.setId(id);
        food.setBrandName(brandName);
        food.setServings(servings);

        return food;
    }
}
