package com.eveningoutpost.dexdrip.Glycemiq.FoodApi;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.eveningoutpost.dexdrip.Glycemiq.FoodApi.model.Food;
import com.eveningoutpost.dexdrip.Glycemiq.FoodApi.model.Serving;
import com.eveningoutpost.dexdrip.Glycemiq.Models.FoodAdapter;
import com.eveningoutpost.dexdrip.Glycemiq.FoodApi.android.Request;
import com.eveningoutpost.dexdrip.Glycemiq.FoodApi.android.ResponseListener;


/**
 * Created by woodb on 7/18/2017.
 */

public class FoodApiClient {
    private static final String TAG = FoodApiClient.class.getSimpleName();
    private static final String key = "96de8e0dc90b4fe4816e38318cab5aab";
    private static final String secret = "233f17b8bb5e4c25944074e7c3e22b17";

    public void getFood(String foodDescription, FoodAdapter adapter, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        FoodListener listener = new FoodListener(adapter);
        Request request = new Request(key, secret, listener);

        request.getFood(queue,29304L);
    }

    private class FoodListener implements ResponseListener {
        private FoodAdapter adapter;

        public FoodListener(FoodAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onFoodResponse(Food food) {
            Log.i(TAG, food.getName());

            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();

            String description = food.getName();

            double qty = 0d;
            String measurement = "";
            double carbs = 0;
            double proteins = 0;
            double fats = 0;
            if (!food.getServings().isEmpty()) {
                Serving serving = food.getServings().iterator().next();
                qty = serving.getMetricServingAmount().doubleValue();
                measurement = serving.getMetricServingUnit();
                carbs = serving.getCarbohydrate().doubleValue();
                proteins = serving.getProtein().doubleValue();
                fats = serving.getFat().doubleValue();
            }

            adapter.add(new com.eveningoutpost.dexdrip.Glycemiq.Models.Food(qty,measurement,description,carbs,proteins,fats,ts));
        }
    }
}
