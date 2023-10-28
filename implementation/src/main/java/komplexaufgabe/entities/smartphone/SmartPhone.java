package komplexaufgabe.entities.smartphone;

public class SmartPhone {
    private final int phoneNumber;

    private final Wallet wallet;

    public SmartPhone(int pPhoneNumber) {
        phoneNumber = pPhoneNumber;
        wallet = new Wallet();
    }
    public int getPhoneNumber() {
        return phoneNumber;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
