package com.eveningoutpost.dexdrip.Glycemiq.InsulinApi;

import android.databinding.ObservableArrayList;
import android.view.View;
import android.widget.EditText;

import com.eveningoutpost.dexdrip.Glycemiq.Activities.InsulinTracker;
import com.eveningoutpost.dexdrip.Glycemiq.Models.InsulinDose;
import com.eveningoutpost.dexdrip.R;

/**
 * Created by woodb on 8/3/2017.
 */

public class InsulinList {
    public ObservableArrayList<InsulinDose> list = new ObservableArrayList<>();
    private InsulinTracker tracker;

    public InsulinList(InsulinTracker tracker) {
        this.tracker = tracker;
        list = InsulinDose.getTodays();
    }

    public void add(View v) {
        InsulinDose dose = tracker.dose;
        dose.created = System.currentTimeMillis();
        dose.save();

        list.add(dose);

        tracker.dose = new InsulinDose();
    }

    public void add(InsulinDose dose) {
        list.add(dose);
    }

    public void remove(View v) {
        if (!list.isEmpty()) {
            InsulinDose dose = list.get(list.size() - 1);
            list.remove(dose);
            dose.delete();
        }
    }
}
