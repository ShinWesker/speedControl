package komplexaufgabe.core.components.mobile;

import komplexaufgabe.entities.human.Owner;
import java.util.HashMap;

public class VehicleRegistrationAuthority {

    private final HashMap<String, Owner> carOwners;

    public VehicleRegistrationAuthority() {
        this.carOwners = new HashMap<>();
    }

    public void registerCar(String licensePlate, Owner owner) {
        carOwners.put(licensePlate, owner);
    }

    public String[] getOwnerDataByLicensePlate(String licensePlate) {

        String[] ownerData = new String[3];

        ownerData[0] =  carOwners.get(licensePlate).getName();
        ownerData[1] =  carOwners.get(licensePlate).getBirthDate().toString();
        ownerData[2] =  String.valueOf(carOwners.get(licensePlate).getSmartPhone().getPhoneNumber());

        return ownerData;
    }
}
