package com.eveningoutpost.dexdrip.Glycemiq.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eveningoutpost.dexdrip.Glycemiq.Models.Food;
import com.eveningoutpost.dexdrip.R;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by woodb on 7/20/2017.
 */

@SuppressLint("SimpleDateFormat")
public class FoodTrackerEdit extends FragmentActivity {
    private final static String TAG = FoodTrackerEdit.class.getSimpleName();

    private Food food;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");

    private Button editFoodCreatedButton;
    private TextView editFoodCreated;
    private EditText editFoodName;

    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            String dateString = mFormatter.format(date);
            Log.i(TAG, "Date set to: " + dateString);
            editFoodCreated.setText(dateString);

            food.created = date.getTime();
            food.save();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker_edit);
        getControls();
        setInitialText(getIntent().getExtras());


        editFoodCreatedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .setTheme(SlideDateTimePicker.HOLO_LIGHT)
                        .build()
                        .show();
            }
        });
    }

    private void setInitialText(Bundle extras) {
        if (extras != null) {
            String foodString = extras.getString("food", "");
            Log.d(TAG, foodString);

            if (foodString != "") {
                GsonBuilder builder = new GsonBuilder();
                builder.excludeFieldsWithoutExposeAnnotation();
                Gson gson = builder.create();

                food = gson.fromJson(foodString, Food.class);
                food = Food.getByCreated(food.created);

                editFoodCreated.setText(mFormatter.format(food.created));
                editFoodName.setText(food.name);
            }
        }
    }

    private void getControls() {
        editFoodCreatedButton = (Button) findViewById(R.id.edit_food_created_button);
        editFoodCreated = (TextView) findViewById(R.id.edit_food_created);
        editFoodName = (EditText) findViewById(R.id.edit_food_name);
    }
}
