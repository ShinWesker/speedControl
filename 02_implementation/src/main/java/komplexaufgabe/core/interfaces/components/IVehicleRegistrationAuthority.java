package komplexaufgabe.core.interfaces.components;

import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.entities.Owner;

public interface IVehicleRegistrationAuthority {
    void registerCar(String licensePlate, Owner owner);

    String[] getOwnerDataByLicensePlate(String licensePlate);

    Car getCar(String licensePlate);
}
