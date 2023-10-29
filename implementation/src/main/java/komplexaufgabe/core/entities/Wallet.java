package komplexaufgabe.core.entities;

public class Wallet {
    private double deposit = 5000;
    public double getDeposit() {
        return deposit;
    }
    public void decreaseDeposit(double fine) {
        deposit = deposit - fine;
    }
}
