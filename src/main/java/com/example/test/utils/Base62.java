package com.example.test.utils;

import org.springframework.stereotype.Component;

@Component
public class Base62 {
    private static final String BASE62_CHARS =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String encode(long number) {
        if(number == 0) {
            return Character.toString(BASE62_CHARS.charAt(0));
        }

        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            int re = (int) (number % 62);
            sb.append(BASE62_CHARS.charAt(re));
            number /= 62;
        }
        return sb.reverse().toString();
    }

    public long decode(String str) {
        long result = 0;
        for (int i = 0; i < str.length(); i++) {
            result = result * 62 + BASE62_CHARS.indexOf(str.charAt(i));
        }
        return result;
    }

}
