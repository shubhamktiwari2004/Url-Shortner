package com.Url_Shortner.SnapURL.Util;
import java.util.Random;

public class ShortCodeGenerator {

    private static final String BASE62 =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final int CODE_LENGTH = 7;

    private static final Random RANDOM = new Random();

    public static String generateCode(Long id) {

        String randomPart = generateRandomString();

        String combined = randomPart + id;

        return encode(combined.hashCode());
    }

    private static String generateRandomString() {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {

            int index = RANDOM.nextInt(BASE62.length());

            result.append(BASE62.charAt(index));
        }

        return result.toString();
    }

    private static String encode(int number) {

        number = Math.abs(number);

        StringBuilder result = new StringBuilder();

        while (number > 0) {

            int remainder = number % 62;

            result.append(BASE62.charAt(remainder));

            number /= 62;
        }

        while (result.length() < CODE_LENGTH) {

            int index = RANDOM.nextInt(BASE62.length());

            result.append(BASE62.charAt(index));
        }

        return result.reverse().toString();
    }
}
