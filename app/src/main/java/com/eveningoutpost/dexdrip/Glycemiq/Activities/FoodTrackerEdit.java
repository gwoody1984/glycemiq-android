package com.eveningoutpost.dexdrip.Glycemiq.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.EditText;

import com.eveningoutpost.dexdrip.R;

/**
 * Created by woodb on 7/20/2017.
 */

public class FoodTrackerEdit extends Activity {
    private final static String TAG = FoodTrackerEdit.class.getSimpleName();

    private String menu_name = "Food Tracker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker_edit);
    }
}
