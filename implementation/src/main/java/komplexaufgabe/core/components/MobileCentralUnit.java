package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.SmartPhone;
import komplexaufgabe.core.entities.Wallet;

import java.util.HashMap;

public enum MobileCentralUnit {
    INSTANCE;
    private static final HashMap<Long, SmartPhone> smartPhoneMap = new HashMap<>();

    public static void addOwner(Long phoneNumber, SmartPhone smartPhone) {
        smartPhoneMap.put(phoneNumber, smartPhone);
    }

    public static void fineSmartphoneWallet(Long phoneNumber, double fine) {
         smartPhoneMap.get(phoneNumber).fineWallet(fine);
    }
}
