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
package com.eveningoutpost.dexdrip.Glycemiq.FoodApi.android;

import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;


import com.eveningoutpost.dexdrip.Glycemiq.FoodApi.RequestBuilder;
import com.eveningoutpost.dexdrip.Glycemiq.FoodApi.utils.FoodUtility;
import com.eveningoutpost.dexdrip.Glycemiq.FoodApi.model.Food;




/**
 * This class helps in sending requests to fatsecret rest api on android
 *
 * @author Saurabh Rane
 * @version 2.0
 */
public class Request {

    /**
     * Request Builder
     */
    private RequestBuilder builder;

    /**
     * Listener interface for response
     */
    private ResponseListener responseListener;

    /**
     * Constructor to set values for APP_KEY and APP_SECRET
     *
     * @param APP_KEY          a value FatSecret API issues to you which helps this API identify you
     * @param APP_SECRET       a secret FatSecret API issues to you which helps this API establish that it really is you
     * @param responseListener a callback listener interface for delivering parsed response
     */
    public Request(String APP_KEY, String APP_SECRET, ResponseListener responseListener) {
        builder = new RequestBuilder(APP_KEY, APP_SECRET);
        this.responseListener = responseListener;
    }

    /**
     * Supported Methods
     */
    public interface Method {
        int SEARCH_FOODS = 1;
        int GET_FOOD = 2;
        int SEARCH_RECIPES = 3;
        int GET_RECIPE = 4;
    }

    /**
     * Returns the food items at zeroth page number based on the query
     *
     * @param queue the request queue for android requests
     * @param query search terms for querying food items
     */
    public void searchFoods(RequestQueue queue, String query) {
        searchFoods(queue, query, 0);
    }

    /**
     * Returns the food items at a particular page number based on the query
     *
     * @param queue      the request queue for android requests
     * @param query      search terms for querying food items
     * @param pageNumber page Number to search the food items
     */
    public void searchFoods(RequestQueue queue, String query, int pageNumber) {

        try {
            String apiUrl = builder.buildFoodsSearchUrl(query, pageNumber);
            getResponse(queue, apiUrl, Method.SEARCH_FOODS);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Returns food based on the identifier with nutritional information
     *
     * @param queue the request queue for android requests
     * @param id    the unique food identifier
     */
    public void getFood(RequestQueue queue, Long id) {

        try {
            String apiUrl = builder.buildFoodGetUrl(id);
            getResponse(queue, apiUrl, Method.GET_FOOD);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Returns the recipes at zeroth page number based on the query
     *
     * @param queue the request queue for android requests
     * @param query search terms for querying recipes
     */
    public void searchRecipes(RequestQueue queue, String query) {
        searchRecipes(queue, query, 0);
    }

    /**
     * Returns the recipes at a particular page number based on the query
     *
     * @param queue      the request queue for android requests
     * @param query      search terms for querying recipes
     * @param pageNumber page Number to search the recipes
     */
    public void searchRecipes(RequestQueue queue, String query, int pageNumber) {

        try {
            String apiUrl = builder.buildRecipesSearchUrl(query, pageNumber);
            getResponse(queue, apiUrl, Method.SEARCH_RECIPES);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Returns the recipe based on the identifier with detailed nutritional information for the standard serving
     *
     * @param queue the request queue for android requests
     * @param id    the unique recipe identifier
     */
    public void getRecipe(RequestQueue queue, Long id) {

        try {
            String apiUrl = builder.buildRecipeGetUrl(id);
            getResponse(queue, apiUrl, Method.GET_RECIPE);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Handles the response from fatsecret api for given url
     *
     * @param queue  the volley request dispatch queue
     * @param apiUrl the rest url which will be sent to fatsecret platform server
     * @param method the method for which the request will be sent
     */
    public void getResponse(final RequestQueue queue, String apiUrl, final int method) {
        try {
            URL url = new URL(apiUrl);

            final StringRequest request = new StringRequest(com.android.volley.Request.Method.GET, url.toString(),
                    new Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject responseJson = new JSONObject(response);

                                switch (method) {

                                    case Method.GET_FOOD:
                                        JSONObject foodJson = responseJson.getJSONObject("food");
                                        Food food = FoodUtility.parseFoodFromJSONObject(foodJson);

                                        responseListener.onFoodResponse(food);

                                        break;
                                }
                            } catch (JSONException e) {
                                System.out.println("Exception: " + e.getMessage());
                            }
                        }
                    }, new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });

            queue.add(request);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}