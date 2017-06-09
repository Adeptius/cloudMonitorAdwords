package ua.adeptius.utils;



public class StringUtils {

    public static String generateRandomKey(){
        String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder(62);
        for (int i = 0; i < 20; i++) {
            int random = (int) (Math.random()*62);
            builder.append(str.charAt(random));
        }
        return builder.toString();
    }


}
