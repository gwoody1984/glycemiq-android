package com.eveningoutpost.dexdrip.Glycemiq.Models;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.widget.EditText;
import android.widget.TextView;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.eveningoutpost.dexdrip.Glycemiq.Utils.DateUtils;
import com.google.gson.annotations.Expose;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Greg on 7/18/2017.
 */

@Table(name = "Food", id = BaseColumns._ID)
public class Food extends Model {

    @Expose
    @Column(name = "created")
    public Long created;

    @Expose
    @Column(name = "quantity")
    public double quantity;

    @Expose
    @Column(name = "measurement")
    public String measurement;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "description")
    public String description;

    @Expose
    @Column(name = "glycemicIndex")
    public double glycemicIndex;

    @Expose
    @Column(name = "calories")
    public double calories;

    @Expose
    @Column(name = "carbs")
    public double carbs;

    @Expose
    @Column(name = "carbsUnit")
    public String carbsUnit;

    @Expose
    @Column(name = "protein")
    public double protein;

    @Expose
    @Column(name = "proteinUnit")
    public String proteinUnit;

    @Expose
    @Column(name = "totalFat")
    public double totalFat;

    @Expose
    @Column(name = "totalFatUnit")
    public String totalFatUnit;

    @Expose
    @Column(name = "sugar")
    public double sugar;

    @Expose
    @Column(name = "sugarUnit")
    public String sugarUnit;

    @Expose
    @Column(name = "fiber")
    public double fiber;

    @Expose
    @Column(name = "fiberUnit")
    public String fiberUnit;

    @Expose
    @Column(name = "calcium")
    public double calcium;

    @Expose
    @Column(name = "calciumUnit")
    public String calciumUnit;

    @Expose
    @Column(name = "iron")
    public double iron;

    @Expose
    @Column(name = "ironUnit")
    public String ironUnit;

    @Expose
    @Column(name = "magnesium")
    public double magnesium;

    @Expose
    @Column(name = "magnesiumUnit")
    public String magnesiumUnit;

    @Expose
    @Column(name = "phosphorus")
    public double phosphorus;

    @Expose
    @Column(name = "phosphorusUnit")
    public String phosphorusUnit;

    @Expose
    @Column(name = "potassium")
    public double potassium;

    @Expose
    @Column(name = "potassiumUnit")
    public String potassiumUnit;

    @Expose
    @Column(name = "sodium")
    public double sodium;

    @Expose
    @Column(name = "sodiumUnit")
    public String sodiumUnit;

    @Expose
    @Column(name = "zinc")
    public double zinc;

    @Expose
    @Column(name = "zincUnit")
    public String zincUnit;

    @Expose
    @Column(name = "vitaminA")
    public double vitaminA;

    @Expose
    @Column(name = "vitaminAUnit")
    public String vitaminAUnit;

    @Expose
    @Column(name = "vitaminE")
    public double vitaminE;

    @Expose
    @Column(name = "vitaminEUnit")
    public String vitaminEUnit;

    @Expose
    @Column(name = "vitaminC")
    public double vitaminC;

    @Expose
    @Column(name = "vitaminCUnit")
    public String vitaminCUnit;

    @Expose
    @Column(name = "vitaminB6")
    public double vitaminB6;

    @Expose
    @Column(name = "vitaminB6Unit")
    public String vitaminB6Unit;

    @Expose
    @Column(name = "vitaminK")
    public double vitaminK;

    @Expose
    @Column(name = "vitaminKUnit")
    public String vitaminKUnit;

    @Expose
    @Column(name = "thiamin")
    public double thiamin;

    @Expose
    @Column(name = "thiaminUnit")
    public String thiaminUnit;

    @Expose
    @Column(name = "riboflavin")
    public double riboflavin;

    @Expose
    @Column(name = "riboflavinUnit")
    public String riboflavinUnit;

    @Expose
    @Column(name = "niacin")
    public double niacin;

    @Expose
    @Column(name = "niacinUnit")
    public String niacinUnit;

    @Expose
    @Column(name = "folate")
    public double folate;

    @Expose
    @Column(name = "folateUnit")
    public String folateUnit;

    @Expose
    @Column(name = "saturatedFat")
    public double saturatedFat;

    @Expose
    @Column(name = "saturatedFatUnit")
    public String saturatedFatUnit;

    @Expose
    @Column(name = "monounsaturatedFat")
    public double monounsaturatedFat;

    @Expose
    @Column(name = "monounsaturatedFatUnit")
    public String monounsaturatedFatUnit;

    @Expose
    @Column(name = "polyunsaturatedFat")
    public double polyunsaturatedFat;

    @Expose
    @Column(name = "polyunsaturatedFatUnit")
    public String polyunsaturatedFatUnit;

    public Food() {
    }

    public static ArrayList<Food> getTodays() {
        ArrayList<Food> arrayList = new ArrayList<>();

        Long startOfDay = DateUtils.getStartOfDay();
        Long endOfDay = DateUtils.getEndOfDay();

        List<Food> list = new Select()
                .from(Food.class)
                .where("created>=?", startOfDay)
                .where("created<=?", endOfDay)
                .orderBy("created asc")
                .execute();

        arrayList.addAll(list);

        return arrayList;
    }

    public void setNutrients(Nutrients nutrients) {
        if (nutrients != null) {
            ENERCKCAL calorie = nutrients.getENERCKCAL();
            if (calorie != null) {
                this.calories = calorie.getQuantity();
            }

            CHOCDF carb = nutrients.getCHOCDF();
            if (carb != null) {
                this.carbs = carb.getQuantity();
                this.carbsUnit = carb.getUnit();
            }

            PROCNT protein = nutrients.getPROCNT();
            if (protein != null) {
                this.protein = protein.getQuantity();
                this.proteinUnit = protein.getUnit();
            }

            FAT fat = nutrients.getFAT();
            if (fat != null) {
                this.totalFat = fat.getQuantity();
                this.totalFatUnit = fat.getUnit();
            }

            SUGAR sugars = nutrients.getSUGAR();
            if (sugars != null) {
                this.sugar = sugars.getQuantity();
                this.sugarUnit = sugars.getUnit();
            }

            FIBTG fibtg = nutrients.getFIBTG();
            if (fibtg != null) {
                this.fiber = fibtg.getQuantity();
                this.fiberUnit = fibtg.getUnit();
            }

            CA ca = nutrients.getCA();
            if (ca != null) {
                this.calcium = ca.getQuantity();
                this.calciumUnit = ca.getUnit();
            }

            FE fe = nutrients.getFE();
            if (fe != null) {
                this.iron = fe.getQuantity();
                this.ironUnit = fe.getUnit();
            }

            MG mg = nutrients.getMG();
            if (mg != null) {
                this.magnesium = mg.getQuantity();
                this.magnesiumUnit = mg.getUnit();
            }

            P p = nutrients.getP();
            if (p != null) {
                this.phosphorus = p.getQuantity();
                this.phosphorusUnit = p.getUnit();
            }

            K k = nutrients.getK();
            if (k != null) {
                this.potassium = k.getQuantity();
                this.potassiumUnit = k.getUnit();
            }

            NA na = nutrients.getNA();
            if (na != null) {
                this.sodium = na.getQuantity();
                this.sodiumUnit = na.getUnit();
            }

            ZN zn = nutrients.getZN();
            if (zn != null) {
                this.zinc = zn.getQuantity();
                this.zincUnit = zn.getUnit();
            }

            VITARAE vitarae = nutrients.getVITARAE();
            if (vitarae != null) {
                this.vitaminA = vitarae.getQuantity();
                this.vitaminAUnit = vitarae.getUnit();
            }

            TOCPHA tocpha = nutrients.getTOCPHA();
            if (tocpha != null) {
                this.vitaminE = tocpha.getQuantity();
                this.vitaminEUnit = tocpha.getUnit();
            }

            VITC vitc = nutrients.getVITC();
            if (vitc != null) {
                this.vitaminC = vitc.getQuantity();
                this.vitaminCUnit = vitc.getUnit();
            }

            THIA thia = nutrients.getTHIA();
            if (thia != null) {
                this.thiamin = thia.getQuantity();
                this.thiaminUnit = thia.getUnit();
            }

            RIBF ribf = nutrients.getRIBF();
            if (ribf != null) {
                this.riboflavin = ribf.getQuantity();
                this.riboflavinUnit = ribf.getUnit();
            }

            NIA nia = nutrients.getNIA();
            if (nia != null) {
                this.niacin = nia.getQuantity();
                this.niacinUnit = nia.getUnit();
            }

            VITB6A vitb6a = nutrients.getVITB6A();
            if (vitb6a != null) {
                this.vitaminB6 = vitb6a.getQuantity();
                this.vitaminB6Unit = vitb6a.getUnit();
            }

            VITK1 vitk1 = nutrients.getVITK1();
            if (vitk1 != null) {
                this.vitaminK = vitk1.getQuantity();
                this.vitaminKUnit = vitk1.getUnit();
            }

            FOLDFE foldfe = nutrients.getFOLDFE();
            if (foldfe != null) {
                this.folate = foldfe.getQuantity();
                this.folateUnit = foldfe.getUnit();
            }

            FASAT fasat = nutrients.getFASAT();
            if (fasat != null) {
                this.saturatedFat = fasat.getQuantity();
                this.saturatedFatUnit = fasat.getUnit();
            }

            FAMS fams = nutrients.getFAMS();
            if (fams != null) {
                this.monounsaturatedFat = fams.getQuantity();
                this.monounsaturatedFatUnit = fams.getUnit();
            }

            FAPU fapu = nutrients.getFAPU();
            if (fapu != null) {
                this.polyunsaturatedFat = fapu.getQuantity();
                this.polyunsaturatedFatUnit = fapu.getUnit();
            }
        }
    }

    @BindingAdapter("android:text")
    public static void setText(EditText view, double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        view.setText(df.format(value));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static double getText(EditText view) {
        return Double.parseDouble(view.getText().toString());
    }
}
