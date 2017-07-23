package com.eveningoutpost.dexdrip.Glycemiq.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.eveningoutpost.dexdrip.Glycemiq.FoodApi.FoodApiClient;
import com.eveningoutpost.dexdrip.Glycemiq.Models.Food;
import com.eveningoutpost.dexdrip.Glycemiq.Models.FoodAdapter;
import com.eveningoutpost.dexdrip.NavigationDrawerFragment;
import com.eveningoutpost.dexdrip.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class FoodTracker extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private final static String TAG = FoodTracker.class.getSimpleName();

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ListView mFoodList;

    private static FoodAdapter adapter;
    private ArrayList<Food> data;

    private String menu_name = "Food Tracker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker);

        final EditText foodText = (EditText) findViewById(R.id.food_enter_description);

        mFoodList = (ListView) findViewById(R.id.food_list);

        data = Food.getTodays();

        adapter = new FoodAdapter(data, getApplicationContext());
        mFoodList.setAdapter(adapter);

        final Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = foodText.getText().toString().trim();
                if (!TextUtils.isEmpty(food)) {
                    FoodApiClient client = new FoodApiClient();
                    client.searchFood(food, adapter);
                    foodText.setText("");
                }
            }
        });

        mFoodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Food food = adapter.getItem(position);

                Intent intent = new Intent(FoodTracker.this, FoodTrackerEdit.class);
                intent.putExtra("id", food.getId());

                startActivityForResult(intent, position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), menu_name, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult Result: " + resultCode);
        Food food = adapter.getItem(requestCode);
        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult.RESULT_OK");
            Bundle b = data.getExtras();
            if (b != null) {
                String action = b.getString(FoodTrackerConstants.EDIT_RESULT_BUNDLE_ACTION, FoodTrackerConstants.EDIT_RESULT_ACTION_SAVE);
                if (action.equals(FoodTrackerConstants.EDIT_RESULT_ACTION_SAVE))
                    food.save();
                else if (action.equals(FoodTrackerConstants.EDIT_RESULT_ACTION_DELETE))
                    food.delete();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.d(TAG, "onActivityResult.RESULT_CANCELED");
            // reload changes
            food = Food.load(Food.class, food.getId());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        mNavigationDrawerFragment.swapContext(position);
    }
}
