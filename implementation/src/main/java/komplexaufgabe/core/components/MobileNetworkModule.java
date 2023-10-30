package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.*;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.core.interfaces.encryption.IEncryption;

public class MobileNetworkModule {

    private final IEncryption encryption;
    private final Police police;

    private final VehicleRegistrationAuthority vra;


    public MobileNetworkModule() {
        police = new Police();
        encryption = new AES();
        vra = new VehicleRegistrationAuthority();

    }

    public void addWanted(Owner owner) {
        police.addWanted(owner);
    }

    public boolean sendRequestToPolice(String data) {
        return police.checkWanted(encryption.encrpyt(data));
    }

    public String[] sendRequestToVRA(String licensePlate) {
        String[] arr = vra.getOwnerDataByLicensePlate(encryption.encrpyt(licensePlate));
        for (int i = 0; i < arr.length; i++) {
            arr[i] = encryption.decrpyt(arr[i]);
        }

        return arr;
    }

    public void registerCar(LicensePlate licensePlate, Owner owner) {
        vra.registerCar(licensePlate.getLicensePlateID(), owner);
    }

}
