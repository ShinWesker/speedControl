package komplexaufgabe.core.entities;

public class SmartPhone {
    private final long phoneNumber;

    private final Wallet wallet;

    public SmartPhone(long pPhoneNumber) {
        phoneNumber = pPhoneNumber;
        wallet = new Wallet();
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
