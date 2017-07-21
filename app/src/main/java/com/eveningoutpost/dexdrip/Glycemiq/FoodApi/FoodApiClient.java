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

    private static final String EDAMAM_APP_ID = "3737dce6";
    private static final String EDAMAM_KEY = "cef870cd5f8d141823111927e8240c8a";

    private Handler mHandler;

    public void searchFood(String foodDescription, final FoodAdapter adapter) {

        mHandler = new Handler(Looper.getMainLooper());

        OkHttpClient client = new OkHttpClient();
        RequestBuilder builder = new RequestBuilder(EDAMAM_APP_ID, EDAMAM_KEY);
        Request request = new Request.Builder()
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

                if (!food.getIngredients().isEmpty()) {
                    Ingredient ingredient = food.getIngredients().get(0);
                    if (!ingredient.getParsed().isEmpty()) {

                        Parsed parsed = ingredient.getParsed().get(0);
                        Nutrients nutrients = parsed.getNutrients();

                        Food foodToAdd = new Food();
                        foodToAdd.setNutrients(nutrients);
                        foodToAdd.created = System.currentTimeMillis();
                        foodToAdd.quantity = parsed.getQuantity();
                        foodToAdd.measurement = parsed.getMeasure();
                        foodToAdd.name = parsed.getFoodMatch();
                        foodToAdd.description = parsed.getFood();
                        foodToAdd.glycemicIndex = food.getGlycemicIndex();

                        foodToAdd.save();

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


