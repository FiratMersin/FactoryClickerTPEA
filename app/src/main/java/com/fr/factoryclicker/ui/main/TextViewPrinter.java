package com.fr.factoryclicker.ui.main;

import android.widget.TextView;

public class TextViewPrinter {


    public static void printInventory(TextView tv, double value, double max){
        double v = Math.floor(value);
        double m = Math.floor(max);

        String toPrint = "";

        if(v < 1000.0){
            toPrint += String.format ("%.0f", v);
        }else if (v < 1000000.0){
            v = v / 1000.0;
            toPrint += String.format ("%.1f", v)+"k";
        }else if (v < 1000000000.0){
            v = v / 1000000.0;
            toPrint += String.format ("%.1f", v)+"M";
        }else{
            v = v / 1000000000.0;
            toPrint += String.format ("%.1f", v)+"B";
        }
        toPrint += "/";
        if(m < 1000.0){
            toPrint += String.format ("%.0f", m);
        }else if (m < 1000000.0){
            m = m / 1000;
            toPrint += String.format ("%.1f", m)+"k";
        }else if (m < 1000000000.0){
            m = m / 1000000;
            toPrint += String.format ("%.1f", m)+"M";
        }else{
            m = m / 1000000000.0;
            toPrint += String.format ("%.1f", m)+"B";
        }
        tv.setText(toPrint);
    }

    public static void printNbMachines(TextView tv, int value){
        double v = value *1.0;

        String toPrint = "";

        if(v < 1000.0){
            toPrint += String.format ("%.0f", v);
        }else if (v < 1000000.0){
            v = v / 1000.0;
            toPrint += String.format ("%.1f", v)+"k";
        }else if (v < 1000000000.0){
            v = v / 1000000.0;
            toPrint += String.format ("%.1f", v)+"M";
        }else{
            v = v / 1000000000.0;
            toPrint += String.format ("%.1f", v)+"B";
        }
        tv.setText(toPrint);
    }

    public static void printValue(TextView tv, double value){
        double v = value;

        String toPrint = "";

        if(v < 1000.0){
            toPrint += String.format ("%.0f", v);
        }else if (v < 1000000.0){
            v = v / 1000.0;
            toPrint += String.format ("%.1f", v)+"k";
        }else if (v < 1000000000.0){
            v = v / 1000000.0;
            toPrint += String.format ("%.1f", v)+"M";
        }else{
            v = v / 1000000000.0;
            toPrint += String.format ("%.1f", v)+"B";
        }
        tv.setText(toPrint);
    }

    public static void printPPM(TextView tv, double value){
        double v = Math.floor(value);

        String toPrint = "";

        if(v < 1000.0){
            toPrint += String.format ("%.0f", v);
        }else if (v < 1000000.0){
            v = v / 1000.0;
            toPrint += String.format ("%.1f", v)+"k";
        }else if (v < 1000000000.0){
            v = v / 1000000.0;
            toPrint += String.format ("%.1f", v)+"M";
        }else{
            v = v / 1000000000.0;
            toPrint += String.format ("%.1f", v)+"B";
        }
        toPrint += "/min";

        tv.setText(toPrint);
    }

    public static void printConsProd(TextView tv, double prod, double cons){
        double v = prod;
        double m = cons;

        String toPrint = "";

        if(v < 1000.0){
            toPrint += String.format ("%.2f", v);
        }else if (v < 1000000.0){
            v = v / 1000.0;
            toPrint += String.format ("%.2f", v)+"k";
        }else if (v < 1000000000.0){
            v = v / 1000000.0;
            toPrint += String.format ("%.2f", v)+"M";
        }else{
            v = v / 1000000000.0;
            toPrint += String.format ("%.2f", v)+"B";
        }
        toPrint += "/";
        if(m < 1000.0){
            toPrint += String.format ("%.2f", m);
        }else if (m < 1000000.0){
            m = m / 1000;
            toPrint += String.format ("%.2f", m)+"k";
        }else if (m < 1000000000.0){
            m = m / 1000000;
            toPrint += String.format ("%.2f", m)+"M";
        }else{
            m = m / 1000000000.0;
            toPrint += String.format ("%.2f", m)+"B";
        }
        tv.setText(toPrint);
    }
}
