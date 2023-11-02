package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.*;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.core.interfaces.encryption.IEncryption;

public class MobileNetworkModule {

    private final IEncryption encryption;
    private final Police police;

    private final VehicleRegistrationAuthority vra;


    public MobileNetworkModule(Police police, VehicleRegistrationAuthority vra) {
        this.police = police;
        encryption = new AES();
        this.vra = vra;

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
