package com.eveningoutpost.dexdrip.Glycemiq.Models;

import android.databinding.ObservableArrayList;
import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.eveningoutpost.dexdrip.Glycemiq.Utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by woodb on 8/3/2017.
 */

@Table(name="InsulinDose", id = BaseColumns._ID)
public class InsulinDose extends Model {

    @Column(name = "created")
    public Long created;

    @Column(name = "units")
    public double units;

    // Basal or Bolus
    @Column(name = "unitType")
    public String unitType;

    // most likely humalog
    @Column(name = "insulinType")
    public String insulinType;

    public InsulinDose() {
        unitType = "Bolus";
        insulinType = "Humalog";
    }

    public String getDateFormatted(){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String dateString = formatter.format(new Date(created));
        return dateString;
    }

    public static ObservableArrayList<InsulinDose> getTodays() {
        ObservableArrayList<InsulinDose> arrayList = new ObservableArrayList<>();

        Long startOfDay = DateUtils.getStartOfDay();
        Long endOfDay = DateUtils.getEndOfDay();

        List<InsulinDose> list = new Select()
                .from(InsulinDose.class)
                .where("created>=?", startOfDay)
                .where("created<=?", endOfDay)
                .orderBy("created asc")
                .execute();

        arrayList.addAll(list);

        return arrayList;
    }
}
