package com.eveningoutpost.dexdrip.Glycemiq.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eveningoutpost.dexdrip.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Greg on 7/18/2017.
 */

public class FoodAdapter extends ArrayAdapter<Food> {

    private ArrayList<Food> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtFoodDescription;
        TextView txtTimestamp;
        TextView txtCarbs;
        TextView txtProteins;
        TextView txtFats;
    }

    public FoodAdapter(ArrayList<Food> data, Context context) {
        super(context, R.layout.food_row_item, data);

        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Food food = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.food_row_item, parent, false);
            viewHolder.txtFoodDescription = (TextView) convertView.findViewById(R.id.food_description);
            viewHolder.txtTimestamp = (TextView) convertView.findViewById(R.id.food_timestamp);
            viewHolder.txtCarbs = (TextView) convertView.findViewById(R.id.food_carbs);
            viewHolder.txtProteins = (TextView) convertView.findViewById(R.id.food_proteins);
            viewHolder.txtFats = (TextView) convertView.findViewById(R.id.food_fats);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String description = String.format("%s %s %s", food.quantity, food.measurement, food.name);
        viewHolder.txtFoodDescription.setText(description);

        DateFormat format = new SimpleDateFormat("hh:mm a");
        String timestamp = format.format(new Date(food.created));
        viewHolder.txtTimestamp.setText(timestamp);

        DecimalFormat df = new DecimalFormat("#.##");
        viewHolder.txtCarbs.setText(df.format(food.carbs));
        viewHolder.txtProteins.setText(df.format(food.protein));
        viewHolder.txtFats.setText(df.format(food.totalFat));

        return convertView;
    }
}
