package ma.shop.utils;

public class RandomCodeGenerator {

    public static String randomCode() {
        return String.valueOf((int)((Math.random() * ((9999 - 1000) + 1)) + 1000));
    }
}
