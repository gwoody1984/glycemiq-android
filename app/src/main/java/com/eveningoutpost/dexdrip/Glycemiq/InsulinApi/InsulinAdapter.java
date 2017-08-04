package com.eveningoutpost.dexdrip.Glycemiq.InsulinApi;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.eveningoutpost.dexdrip.Glycemiq.Models.InsulinDose;
import com.eveningoutpost.dexdrip.R;
import com.eveningoutpost.dexdrip.databinding.InsulinRowItemBinding;

/**
 * Created by woodb on 8/3/2017.
 */

public class InsulinAdapter extends BaseAdapter {
    public ObservableArrayList<InsulinDose> list;
    private LayoutInflater inflater;

    public InsulinAdapter(ObservableArrayList<InsulinDose> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        InsulinRowItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.insulin_row_item, parent, false);
        binding.setItem(list.get(position));

        return binding.getRoot();
    }
}
