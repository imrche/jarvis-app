package org.rch.jarvisapp.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Util {
    public static String toUTF8(String str) {
        if (str != null) {
            byte[] bArr = new byte[0];
            try {
                bArr = str.getBytes("cp1251");
                return new String(bArr, StandardCharsets.UTF_8);
            } catch (UnsupportedEncodingException e) {
                return str;
            }
        }
        return null;
    }

    public static int timeStr2Int(String str) {
        String[] resultArr = str.replace(" ", "")
                .replace("sec", "_1000")
                .replace("min", "_1000_60")
                .replace("hour", "_1000_60_60")
                .split("_");

        int result = 1;
        for (String s : resultArr)
            result = result * Integer.parseInt(s);

        return result;
    }
}

