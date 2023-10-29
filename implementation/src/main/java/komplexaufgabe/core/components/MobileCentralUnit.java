package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.SmartPhone;
import komplexaufgabe.core.entities.Wallet;

import java.util.HashMap;

public enum MobileCentralUnit {
    INSTANCE;
    private static final HashMap<Integer, SmartPhone> smartPhoneMap = new HashMap<>();

    public static void addOwner(Integer id, SmartPhone smartPhone) {
        smartPhoneMap.put(id, smartPhone);
    }

    public static Wallet getSmartPhoneWallet(Integer id) {
        return smartPhoneMap.get(id).getWallet();
    }
}
