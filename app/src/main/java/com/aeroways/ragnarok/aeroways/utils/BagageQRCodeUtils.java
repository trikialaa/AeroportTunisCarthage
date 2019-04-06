package com.aeroways.ragnarok.aeroways.utils;

import com.aeroways.ragnarok.aeroways.Entities.Luggage;

public class BagageQRCodeUtils {

    public static Luggage decodeQR(String code){

        Luggage result = new Luggage();

        try{
            String[] codeArray = code.split("\n");
            if (!(codeArray[0].equals("123456789"))) return null;
            result.setId(codeArray[1]);
            result.setType(codeArray[2]);
            result.setColor(codeArray[3]);
            result.setWeight(Double.parseDouble(codeArray[4]));
            result.setPicUrl(codeArray[5]);
            result.setStatus(codeArray[6]);
            result.setLastSeen(codeArray[7]);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }
}
