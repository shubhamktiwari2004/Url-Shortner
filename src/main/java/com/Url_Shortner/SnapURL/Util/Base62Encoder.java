package com.Url_Shortner.SnapURL.Util;


public class Base62Encoder {

    private static final String BASE62 =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encode(long number) {

        if (number == 0) return "0";

        StringBuilder sb = new StringBuilder();

        while (number > 0) {
            int rem = (int) (number % 62);
            sb.append(BASE62.charAt(rem));
            number /= 62;
        }

        return sb.reverse().toString();
    }
}
