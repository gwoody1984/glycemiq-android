package com.eveningoutpost.dexdrip.Glycemiq.Activities;

import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingAdapter;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.eveningoutpost.dexdrip.Glycemiq.InsulinApi.InsulinAdapter;
import com.eveningoutpost.dexdrip.Glycemiq.InsulinApi.InsulinList;
import com.eveningoutpost.dexdrip.Glycemiq.Models.InsulinDose;
import com.eveningoutpost.dexdrip.R;
import com.eveningoutpost.dexdrip.databinding.ActivityInsulinTrackerBinding;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by woodb on 8/3/2017.
 */

public class InsulinTracker extends AppCompatActivity {

    public InsulinDose dose = new InsulinDose();
    private InsulinList list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInsulinTrackerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_insulin_tracker);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        list = new InsulinList(this);

        binding.setDoses(list);
        binding.setDose(dose);
    }

    @BindingAdapter("bind:items")
    public static void bindList(ListView view, ObservableArrayList<InsulinDose> list) {
        InsulinAdapter adapter = new InsulinAdapter(list);
        view.setAdapter(adapter);
    }

    @BindingAdapter("android:text")
    public static void setText(TextView view, Long value) {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String timestamp = format.format(new Date(value));
        view.setText(timestamp);
    }

    @BindingAdapter("android:text")
    public static void setText(TextView view, double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        view.setText(df.format(value));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static double getText(TextView view) {
        return Double.parseDouble(view.getText().toString());
    }
}
