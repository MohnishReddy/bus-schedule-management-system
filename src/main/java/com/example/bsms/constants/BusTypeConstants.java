package com.example.bsms.constants;

public class BusTypeConstants {
    public final static String NORMAL = "Normal";

    public final static String DELUXE = "Deluxe";

    public static boolean isValidBusType(String type) {
        return type.equals(NORMAL) || type.equals(DELUXE);
    }

}
