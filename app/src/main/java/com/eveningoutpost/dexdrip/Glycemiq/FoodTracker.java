package com.eveningoutpost.dexdrip.Glycemiq;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.BaseColumns;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.eveningoutpost.dexdrip.Glycemiq.Models.Food;
import com.eveningoutpost.dexdrip.Glycemiq.Models.FoodAdapter;
import com.eveningoutpost.dexdrip.NavigationDrawerFragment;
import com.eveningoutpost.dexdrip.R;

import java.util.ArrayList;
import java.util.Date;

public class FoodTracker extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ListView mFoodList;

    private static FoodAdapter adapter;
    private ArrayList<Food> data;

    private String menu_name = "Food Tracker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), menu_name, this);

        final EditText foodText = (EditText) findViewById(R.id.food_enter_description);

        mFoodList = (ListView) findViewById(R.id.food_list);

        data = new ArrayList<>();
        data.add(new Food(.5, "c", "Black Beans", 25, 15, 1, "09:05 AM"));

        adapter = new FoodAdapter(data, getApplicationContext());
        mFoodList.setAdapter(adapter);

        final Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = foodText.getText().toString().trim();
                if (!TextUtils.isEmpty(food)) {
                    long now = (new Date()).getTime();

                    foodText.setText("");
                }
            }
        });
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        mNavigationDrawerFragment.swapContext(position);
    }
}
