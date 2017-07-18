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

import com.eveningoutpost.dexdrip.NavigationDrawerFragment;
import com.eveningoutpost.dexdrip.R;

import java.util.ArrayList;

public class FoodTracker extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ListAdapter mAdapter;
    private ListView mFoodList;
    private String menu_name = "Food Tracker";
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), menu_name, this);

        final EditText foodText = (EditText) findViewById(R.id.food_description);

        mFoodList = (ListView) findViewById(R.id.food_list);
        String[] columnNames = new String[]{BaseColumns._ID, "Title", "Subtext"};
        final MatrixCursor cursor = new MatrixCursor(columnNames);
        cursor.addRow(new String[]{String.valueOf(counter), "First", "Second"});
        counter++;

        createListView(cursor);

        final Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = foodText.getText().toString().trim();
                if (!TextUtils.isEmpty(food)) {
                    cursor.addRow(new String[]{String.valueOf(counter), food, new StringBuffer(food).reverse().toString()});
                    createListView(cursor);
                    foodText.setText("");

                    counter++;
                }
            }
        });
    }

    private void createListView(Cursor cursor) {
        String[] columnNames = new String[]{BaseColumns._ID, "Title", "Subtext"};
        mAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                columnNames,
                new int[]{0, android.R.id.text1, android.R.id.text2},
                0);

        mFoodList.setAdapter(mAdapter);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        mNavigationDrawerFragment.swapContext(position);
    }
}
