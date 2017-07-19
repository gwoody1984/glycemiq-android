package com.eveningoutpost.dexdrip.Glycemiq.FoodApi;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.eveningoutpost.dexdrip.Glycemiq.Models.CHOCDF;
import com.eveningoutpost.dexdrip.Glycemiq.Models.EdamamFood;
import com.eveningoutpost.dexdrip.Glycemiq.Models.FAT;
import com.eveningoutpost.dexdrip.Glycemiq.Models.Food;
import com.eveningoutpost.dexdrip.Glycemiq.Models.FoodAdapter;
import com.eveningoutpost.dexdrip.Glycemiq.Models.Ingredient;
import com.eveningoutpost.dexdrip.Glycemiq.Models.Nutrients;
import com.eveningoutpost.dexdrip.Glycemiq.Models.PROCNT;
import com.eveningoutpost.dexdrip.Glycemiq.Models.Parsed;
import com.google.gson.Gson;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by woodb on 7/18/2017.
 */

public class FoodApiClient {
    private static final String TAG = FoodApiClient.class.getSimpleName();

    private static final String edamamAppId = "3737dce6";
    private static final String edamamKey = "cef870cd5f8d141823111927e8240c8a";

    private Handler mHandler;

    public void searchFood(String foodDescription, final FoodAdapter adapter) {

        mHandler = new Handler(Looper.getMainLooper());

        OkHttpClient client = new OkHttpClient();
        RequestBuilder builder = new RequestBuilder(edamamAppId, edamamKey);
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                .url(builder.getUrl(foodDescription))
                .build();

        client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                Log.e(TAG, "ERROR: " + e.toString());
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                String json = response.body().string();
                Log.d(TAG, "SUCCESS: " + json);

                Gson gson = new Gson();
                EdamamFood food = gson.fromJson(json, EdamamFood.class);

                Long tsLong = System.currentTimeMillis();
                DateFormat format = new SimpleDateFormat("hh:mm a");
                String timestamp = format.format(new Date(tsLong));

                String measurement = "";
                String description = "";
                double quantity = 0;
                double carbs = 0;
                double proteins = 0;
                double fats = 0;

                if (!food.getIngredients().isEmpty()) {
                    Ingredient ingredient = food.getIngredients().get(0);
                    if (!ingredient.getParsed().isEmpty()) {
                        Parsed parsed = ingredient.getParsed().get(0);
                        quantity = parsed.getQuantity();
                        measurement = parsed.getMeasure();
                        description = parsed.getFoodMatch();

                        Nutrients nutrients = parsed.getNutrients();
                        if (nutrients != null) {
                            CHOCDF carb = nutrients.getCHOCDF();
                            if (carb != null) carbs = carb.getQuantity();

                            PROCNT protein = nutrients.getPROCNT();
                            if (protein != null) proteins = protein.getQuantity();

                            FAT fat = nutrients.getFAT();
                            if (fat != null) fats = fat.getQuantity();
                        }

                        Food foodToAdd = new Food(quantity, measurement, description, carbs, proteins,
                                fats, timestamp);

                        mHandler.post(new UpdateAdapter(foodToAdd, adapter));
                    }
                }
            }
        });
    }

    class UpdateAdapter implements Runnable {
        private Food food;
        private FoodAdapter adapter;

        UpdateAdapter(Food food, FoodAdapter adapter) {
            this.food = food;
            this.adapter = adapter;
        }

        @Override
        public void run() {
            adapter.add(food);
        }
    }
}


