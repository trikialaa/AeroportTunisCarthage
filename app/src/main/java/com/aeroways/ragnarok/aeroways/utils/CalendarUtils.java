package com.aeroways.ragnarok.aeroways.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtils {

    public static String monthNumberToMonthName(int monthNumber){
        String result="";
        switch (monthNumber) {
            case 1:
                result="JAN";
                break;
            case 2:
                result="FEV";
                break;
            case 3:
                result="MARS";
                break;
            case 4:
                result="AVR";
                break;
            case 5:
                result="MAI";
                break;
            case 6:
                result="JUIN";
                break;
            case 7:
                result="JUIL";
                break;
            case 8:
                result="AOUT";
                break;
            case 9:
                result="SEP";
                break;
            case 10:
                result="OCT";
                break;
            case 11:
                result="NOV";
                break;
            case 12:
                result="DEC";
                break;
           default:
                break;
        }

        return result;
    }

    public static boolean compareDate(String sdate1,String sdate2) { // Returns True if date1 < date2
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(sdate1);
            Date date2 = sdf.parse(sdate2);

            System.out.println("date1 : " + sdf.format(date1));
            System.out.println("date2 : " + sdf.format(date2));

            if (date1.compareTo(date2) > 0) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e){
            return false;
        }


    }
}
