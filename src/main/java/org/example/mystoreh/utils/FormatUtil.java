package org.example.mystoreh.utils;

public class FormatUtil {
    public static String leftPAD(String str, int length, String padChar) {
        if (str.length() >= length) {
            return str;
        }
        return "KYO"+String.valueOf(padChar).repeat(length - str.length()) + str;
    }
}
