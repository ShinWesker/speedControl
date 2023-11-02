package komplexaufgabe.core.entities;

public class Car {
    private final String manufacturer;
    private final String registrationID;
    private final LicensePlate licensePlate;
    private int speed;
    private Human driver;

    private Car(CarBuilder carBuilder) {
        manufacturer = carBuilder.bManufacturer;
        registrationID = carBuilder.bRegistrationID;
        licensePlate = carBuilder.bLicensePlate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getSpeed() {
        return speed;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public Human getDriver() {
        return driver;
    }

    public void setDriver(Human driver) {
        this.driver = driver;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // Builder Class
    public static class CarBuilder {
        private final String bManufacturer;
        private final String bRegistrationID;
        private final LicensePlate bLicensePlate;

        public CarBuilder(String pManufacturer, String pRegistrationID, LicensePlate pLicensePlate) {
            bManufacturer = pManufacturer;
            bRegistrationID = pRegistrationID;
            bLicensePlate = pLicensePlate;
        }

        public Car build() {
            return new Car(this);
        }

    }

}
