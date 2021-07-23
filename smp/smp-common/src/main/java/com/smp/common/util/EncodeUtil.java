package com.smp.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class EncodeUtil {

    private static final String codeSource = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZY";

    //按传入长度获取随机码
    public static String SafeCode(int length) {
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            Random random = new Random();
            int index = random.nextInt(codeSource.length());
            result[i] = codeSource.charAt(index);
        }
        return String.valueOf(result);
    }

    public static String MD5(String str) {
        String newStr = null;

        // 确定计算方法
        MessageDigest MD5;
        try {
            MD5 = MessageDigest.getInstance("MD5");
            // 加密后的字符串
            MD5.update(str.getBytes());
            newStr = new BigInteger(1, MD5.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return newStr.toUpperCase();
    }


    public static String BCryp(String str) {
        return  new BCryptPasswordEncoder().encode(str);
    }


    public static String getAccessToken(String authToken){
        String authTokenPrefix="Bearer";
        if(authToken.contains(authTokenPrefix)){
            return authToken.substring(7);
        }else {
            return authToken;
        }
    }

    public static String getBinary(byte[] bytes, int radix) {
        return new BigInteger(1,bytes).toString(radix);
    }
}
