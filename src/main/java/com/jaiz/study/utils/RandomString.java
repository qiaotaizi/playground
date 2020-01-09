package com.jaiz.study.utils;

import java.util.Arrays;
import java.util.Random;

public class RandomString {

    private static final char[] charValueRange;

    private static final Random random=new Random(System.currentTimeMillis());

    static {
        int arrayLengthUpperCase='Z'-'A'+1;
        int arrayLengthLowerCase='z'-'a'+1;
        charValueRange=new char[arrayLengthLowerCase+arrayLengthUpperCase];
        for (int i = 0; i < charValueRange.length; i++) {
            int c=i<arrayLengthUpperCase?('a'+i):('A'+i-arrayLengthLowerCase);
            charValueRange[i]=(char)c;
        }
    }

    public String next(int length) {
        char[] chars=new char[length];
        for (int i=0;i<length;i++){
            int pos=random.nextInt(charValueRange.length);
            chars[i]=charValueRange[pos];
        }
        return String.valueOf(chars);
    }
}
