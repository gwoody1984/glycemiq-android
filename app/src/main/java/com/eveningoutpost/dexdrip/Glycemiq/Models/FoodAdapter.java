package com.eveningoutpost.dexdrip.Glycemiq.Models;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eveningoutpost.dexdrip.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.food_row_item, parent, false);
            viewHolder.txtFoodDescription = (TextView) convertView.findViewById(R.id.food_description);
            viewHolder.txtTimestamp = (TextView) convertView.findViewById(R.id.food_timestamp);
            viewHolder.txtCarbs = (TextView) convertView.findViewById(R.id.food_carbs);
            viewHolder.txtProteins = (TextView) convertView.findViewById(R.id.food_proteins);
            viewHolder.txtFats = (TextView) convertView.findViewById(R.id.food_fats);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        String description = String.format("%s %s %s", food.quantity, food.measurement, food.description);
        viewHolder.txtFoodDescription.setText(description);
        viewHolder.txtTimestamp.setText(food.timestamp);

        DecimalFormat df = new DecimalFormat("#.##");
        viewHolder.txtCarbs.setText(df.format(food.carbs));
        viewHolder.txtProteins.setText(df.format(food.proteins));
        viewHolder.txtFats.setText(df.format(food.fats));

        return convertView;
    }
}
