package com.eveningoutpost.dexdrip.Glycemiq.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eveningoutpost.dexdrip.Glycemiq.Models.Food;
import com.eveningoutpost.dexdrip.R;
import com.eveningoutpost.dexdrip.databinding.ActivityFoodTrackerEditBinding;
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
public class FoodTrackerEdit extends AppCompatActivity {
    private final static String TAG = FoodTrackerEdit.class.getSimpleName();

    private Food food;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");

    private Button editFoodCreatedButton;
    private TextView editFoodCreated;

    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            String dateString = mFormatter.format(date);
            Log.i(TAG, "Date set to: " + dateString);
            editFoodCreated.setText(dateString);
            food.created = date.getTime();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFoodTrackerEditBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_food_tracker_edit);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getControls();
        setInitialText(getIntent().getExtras());

        binding.setFood(food);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_food, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_save_food || id == R.id.menu_delete_food) {
            Intent intent = new Intent();
            intent.putExtra(FoodTrackerConstants.EDIT_RESULT_BUNDLE_ACTION,
                    id == R.id.menu_save_food ?
                            FoodTrackerConstants.EDIT_RESULT_ACTION_SAVE :
                            FoodTrackerConstants.EDIT_RESULT_ACTION_DELETE);
            setResult(Activity.RESULT_OK, intent);
            finish();

            Log.d(TAG, "Sent result");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setInitialText(Bundle extras) {
        if (extras != null) {
            Long id = extras.getLong("id");
            food = Food.load(Food.class, id);

            editFoodCreated.setText(mFormatter.format(food.created));
        }
    }

    private void getControls() {
        editFoodCreatedButton = (Button) findViewById(R.id.edit_food_created_button);
        editFoodCreated = (TextView) findViewById(R.id.edit_food_created);
    }
}
