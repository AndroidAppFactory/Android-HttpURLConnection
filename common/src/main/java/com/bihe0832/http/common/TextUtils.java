package com.bihe0832.http.common;

import java.io.UnsupportedEncodingException;

public class TextUtils {
    /**
     * 判断字符串是否为空
     *
     * @param s
     *            需要判断的字符串
     * @return boolean 为空返回true
     */
    public static boolean ckIsEmpty(String s) {
        if (s == null || s.trim().equals("") || s.trim().equals("null")) {
            return true;
        }
        return false;
    }

    public static byte[] getBytesUTF8(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
