package com.aeroways.ragnarok.aeroways.utils;

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
}
