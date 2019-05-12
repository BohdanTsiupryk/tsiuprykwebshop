package ma.shop.utils;

import java.util.Random;

public class RandomGenerator {

    public static String randomCode() {
        return String.valueOf((int)((Math.random() * ((9999 - 1000) + 1)) + 1000));
    }

    public static String randomSalt() {
        String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        int size = 4;
        char[] text = new char[size];
        for (int i = 0; i < size; i++) {
            text[i] = source.charAt(new Random().nextInt(source.length()));
        }
        return new String(text);
    }
}
