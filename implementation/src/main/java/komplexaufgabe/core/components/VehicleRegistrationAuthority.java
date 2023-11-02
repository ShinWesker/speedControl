package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.entities.Owner;
import komplexaufgabe.core.interfaces.components.IVehicleRegistrationAuthority;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.core.interfaces.encryption.IEncryption;

import java.util.HashMap;

public class VehicleRegistrationAuthority implements IVehicleRegistrationAuthority {

    private final HashMap<String, Owner> carOwners;
    private final IEncryption encryption;

    public VehicleRegistrationAuthority() {
        this.carOwners = new HashMap<>();
        this.encryption = new AES();
    }

    public void registerCar(String licensePlate, Owner owner) {
        carOwners.put(licensePlate, owner);
    }

    public String[] getOwnerDataByLicensePlate(String licensePlate) {

        licensePlate = encryption.decrypt(licensePlate);
        String[] ownerData = new String[3];

        ownerData[0] = encryption.encrypt(carOwners.get(licensePlate).getName());
        ownerData[1] = encryption.encrypt(String.valueOf(carOwners.get(licensePlate).getBirthDate().getTime()));
        ownerData[2] = encryption.encrypt(String.valueOf(carOwners.get(licensePlate).getSmartPhone().getPhoneNumber()));

        return ownerData;
    }

    public Car getCar(String licensePlate) {
        return carOwners.get(encryption.decrypt(licensePlate)).getCar();
    }
}
