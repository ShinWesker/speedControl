package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.*;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.core.interfaces.encryption.IEncryption;

public class MobileNetworkModule {

    private final IEncryption encryption;
    private final Police police;

    private final VehicleRegistrationAuthority vra;


    public MobileNetworkModule(Police police) {
        this.police = police;
        encryption = new AES();
        vra = new VehicleRegistrationAuthority();

    }

    public boolean sendRequestToPolice(String data) {
        return police.checkWanted(encryption.encrypt(data));
    }

    public String[] sendRequestToVRA(String licensePlate) {
        String[] arr = vra.getOwnerDataByLicensePlate(encryption.encrypt(licensePlate));
        for (int i = 0; i < arr.length; i++) {
            arr[i] = encryption.decrypt(arr[i]);
        }

        return arr;
    }

    public void registerCar(LicensePlate licensePlate, Owner owner) {
        vra.registerCar(licensePlate.getLicensePlateID(), owner);
    }

    public void requestArrest(String face) {
        police.arrestOwner(encryption.encrypt(face));
    }

    public void requestCarConfiscation(Car car) {
        police.confiscateCar(car);
    }

    public Car vraGetCar(String licensePlate) {
        return vra.getCar(encryption.encrypt(licensePlate));
    }
}
