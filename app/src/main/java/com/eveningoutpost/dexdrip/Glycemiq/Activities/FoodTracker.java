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

                Food food = (Food) mFoodList.getItemAtPosition(position);

                GsonBuilder builder = new GsonBuilder();
                builder.excludeFieldsWithoutExposeAnnotation();
                Gson gson = builder.create();

                String foodString = gson.toJson(food);

                Log.d(TAG, foodString);

                Intent intent = new Intent(FoodTracker.this, FoodTrackerEdit.class);
                intent.putExtra("food", foodString);

                startActivity(intent);
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
    public void onNavigationDrawerItemSelected(int position) {
        mNavigationDrawerFragment.swapContext(position);
    }
}
