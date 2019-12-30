package com.jaiz.study.utils;

import java.util.Arrays;
import java.util.Random;

public class RandomString {

    private static final char[] charValueRange;

    private static final Random random=new Random(System.currentTimeMillis());

    static {
        var arrayLengthUpperCase='Z'-'A'+1;
        var arrayLengthLowerCase='z'-'a'+1;
        charValueRange=new char[arrayLengthLowerCase+arrayLengthUpperCase];
        for (int i = 0; i < charValueRange.length; i++) {
            var c=i<arrayLengthUpperCase?('a'+i):('A'+i-arrayLengthLowerCase);
            charValueRange[i]=(char)c;
        }
    }

    public String next(int length) {
        char[] chars=new char[length];
        for (int i=0;i<length;i++){
            var pos=random.nextInt(charValueRange.length);
            chars[i]=charValueRange[pos];
        }
        return String.valueOf(chars);
    }
}
