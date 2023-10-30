package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.Owner;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.core.interfaces.encryption.IEncryption;

import java.util.HashMap;

public class VehicleRegistrationAuthority {

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

        licensePlate = encryption.decrpyt(licensePlate);
        String[] ownerData = new String[3];

        ownerData[0] =  encryption.encrpyt(carOwners.get(licensePlate).getName());
        ownerData[1] =  encryption.encrpyt(String.valueOf(carOwners.get(licensePlate).getBirthDate().getTime()));
        ownerData[2] =  encryption.encrpyt(String.valueOf(carOwners.get(licensePlate).getSmartPhone().getPhoneNumber()));

        return ownerData;
    }
}
