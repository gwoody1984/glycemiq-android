package com.eveningoutpost.dexdrip.Glycemiq.Models;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.eveningoutpost.dexdrip.Glycemiq.Utils.DateUtils;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greg on 7/18/2017.
 */

@Table(name = "Food", id = BaseColumns._ID)
public class Food extends Model {

    @Column(name = "created")
    public Long created;

    @Column(name = "quantity")
    public double quantity;

    @Column(name = "measurement")
    public String measurement;

    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "glycemicIndex")
    public double glycemicIndex;

    @Column(name = "calories")
    public double calories;

    @Column(name = "carbs")
    public double carbs;

    @Column(name = "carbsUnit")
    public String carbsUnit;
    
    @Column(name = "protein")
    public double protein;
    
    @Column(name = "proteinUnit")
    public String proteinUnit;

    @Column(name = "totalFat")
    public double totalFat;

    @Column(name = "totalFatUnit")
    public String totalFatUnit;

    @Column(name = "sugar")
    public double sugar;
    
    @Column(name = "sugarUnit")
    public String sugarUnit;

    @Column(name = "fiber")
    public double fiber;

    @Column(name = "fiberUnit")
    public String fiberUnit;

    @Column(name = "calcium")
    public double calcium;

    @Column(name = "calciumUnit")
    public String calciumUnit;

    @Column(name = "iron")
    public double iron;

    @Column(name = "ironUnit")
    public String ironUnit;

    @Column(name = "magnesium")
    public double magnesium;

    @Column(name = "magnesiumUnit")
    public String magnesiumUnit;

    @Column(name = "phosphorus")
    public double phosphorus;

    @Column(name = "phosphorusUnit")
    public String phosphorusUnit;

    @Column(name = "potassium")
    public double potassium;

    @Column(name = "potassiumUnit")
    public String potassiumUnit;

    @Column(name = "sodium")
    public double sodium;

    @Column(name = "sodiumUnit")
    public String sodiumUnit;

    @Column(name = "zinc")
    public double zinc;

    @Column(name = "zincUnit")
    public String zincUnit;

    @Column(name = "vitaminA")
    public double vitaminA;

    @Column(name = "vitaminAUnit")
    public String vitaminAUnit;

    @Column(name = "vitaminE")
    public double vitaminE;

    @Column(name = "vitaminEUnit")
    public String vitaminEUnit;

    @Column(name = "vitaminC")
    public double vitaminC;

    @Column(name = "vitaminCUnit")
    public String vitaminCUnit;

    @Column(name = "vitaminB6")
    public double vitaminB6;

    @Column(name = "vitaminB6Unit")
    public String vitaminB6Unit;

    @Column(name = "vitaminK")
    public double vitaminK;

    @Column(name = "vitaminKUnit")
    public String vitaminKUnit;

    @Column(name = "thiamin")
    public double thiamin;

    @Column(name = "thiaminUnit")
    public String thiaminUnit;

    @Column(name = "riboflavin")
    public double riboflavin;

    @Column(name = "riboflavinUnit")
    public String riboflavinUnit;

    @Column(name = "niacin")
    public double niacin;

    @Column(name = "niacinUnit")
    public String niacinUnit;

    @Column(name = "folate")
    public double folate;

    @Column(name = "folateUnit")
    public String folateUnit;

    @Column(name = "saturatedFat")
    public double saturatedFat;

    @Column(name = "saturatedFatUnit")
    public String saturatedFatUnit;

    @Column(name = "monounsaturatedFat")
    public double monounsaturatedFat;

    @Column(name = "monounsaturatedFatUnit")
    public String monounsaturatedFatUnit;

    @Column(name = "polyunsaturatedFat")
    public double polyunsaturatedFat;

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
                .where("created>=?",startOfDay)
                .where("created<=?",endOfDay)
                .orderBy("created asc")
                .execute();

        arrayList.addAll(list);

        return arrayList;
    }

    // this method SUCKS!
    public void setNutrients(Nutrients nutrients){
        if (nutrients != null) {
            ENERCKCAL calorie = nutrients.getENERCKCAL();
            if (calorie != null){
                this.calories = calorie.getQuantity();
            }

            CHOCDF carb = nutrients.getCHOCDF();
            if (carb != null) {
                this.carbs = carb.getQuantity();
                this.carbsUnit = carb.getUnit();
            }

            PROCNT protein = nutrients.getPROCNT();
            if (protein != null){
                this.protein = protein.getQuantity();
                this.proteinUnit = protein.getUnit();
            }

            FAT fat = nutrients.getFAT();
            if (fat != null){
                this.totalFat = fat.getQuantity();
                this.totalFatUnit = fat.getUnit();
            }

            SUGAR sugars = nutrients.getSUGAR();
            if (sugars != null){
                this.sugar = sugars.getQuantity();
                this.sugarUnit = sugars.getUnit();
            }

            FIBTG fibtg = nutrients.getFIBTG();
            if (fibtg != null){
                this.fiber = fibtg.getQuantity();
                this.fiberUnit = fibtg.getUnit();
            }

            CA ca = nutrients.getCA();
            if (ca != null){
                this.calcium = ca.getQuantity();
                this.calciumUnit = ca.getUnit();
            }

            FE fe = nutrients.getFE();
            if (fe != null){
                this.iron = fe.getQuantity();
                this.ironUnit = fe.getUnit();
            }

            MG mg = nutrients.getMG();
            if (mg != null){
                this.magnesium = mg.getQuantity();
                this.magnesiumUnit = mg.getUnit();
            }

            P p = nutrients.getP();
            if (p != null){
                this.phosphorus = p.getQuantity();
                this.phosphorusUnit = p.getUnit();
            }

            K k = nutrients.getK();
            if (k != null){
                this.potassium = k.getQuantity();
                this.potassiumUnit = k.getUnit();
            }

            NA na = nutrients.getNA();
            if (na != null){
                this.sodium = na.getQuantity();
                this.sodiumUnit = na.getUnit();
            }

            ZN zn = nutrients.getZN();
            if (zn != null){
                this.zinc = zn.getQuantity();
                this.zincUnit = zn.getUnit();
            }

            VITARAE vitarae = nutrients.getVITARAE();
            if (vitarae != null){
                this.vitaminA = vitarae.getQuantity();
                this.vitaminAUnit = vitarae.getUnit();
            }

            TOCPHA tocpha = nutrients.getTOCPHA();
            if (tocpha != null){
                this.vitaminE = tocpha.getQuantity();
                this.vitaminEUnit = tocpha.getUnit();
            }

            VITC vitc = nutrients.getVITC();
            if (vitc != null){
                this.vitaminC = vitc.getQuantity();
                this.vitaminCUnit = vitc.getUnit();
            }

            THIA thia = nutrients.getTHIA();
            if (thia != null){
                this.thiamin = thia.getQuantity();
                this.thiaminUnit = thia.getUnit();
            }

            RIBF ribf = nutrients.getRIBF();
            if (ribf != null){
                this.riboflavin = ribf.getQuantity();
                this.riboflavinUnit = ribf.getUnit();
            }

            NIA nia = nutrients.getNIA();
            if (nia != null){
                this.niacin = nia.getQuantity();
                this.niacinUnit = nia.getUnit();
            }

            VITB6A vitb6a = nutrients.getVITB6A();
            if (vitb6a != null){
                this.vitaminB6 = vitb6a.getQuantity();
                this.vitaminB6Unit = vitb6a.getUnit();
            }

            VITK1 vitk1 = nutrients.getVITK1();
            if (vitk1 != null){
                this.vitaminK = vitk1.getQuantity();
                this.vitaminKUnit = vitk1.getUnit();
            }

            FOLDFE foldfe = nutrients.getFOLDFE();
            if (foldfe != null){
                this.folate = foldfe.getQuantity();
                this.folateUnit = foldfe.getUnit();
            }

            FASAT fasat = nutrients.getFASAT();
            if (fasat != null){
                this.saturatedFat = fasat.getQuantity();
                this.saturatedFatUnit = fasat.getUnit();
            }

            FAMS fams = nutrients.getFAMS();
            if (fams != null){
                this.monounsaturatedFat = fams.getQuantity();
                this.monounsaturatedFatUnit = fams.getUnit();
            }

            FAPU fapu = nutrients.getFAPU();
            if (fapu != null){
                this.polyunsaturatedFat = fapu.getQuantity();
                this.polyunsaturatedFatUnit = fapu.getUnit();
            }
        }
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, Food.class);
    }
}
