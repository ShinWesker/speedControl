package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.*;
import komplexaufgabe.core.interfaces.components.IMobileNetworkModule;
import komplexaufgabe.core.interfaces.components.IPolice;
import komplexaufgabe.core.interfaces.components.IVehicleRegistrationAuthority;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.core.interfaces.encryption.IEncryption;

public class MobileNetworkModule implements IMobileNetworkModule {

    private final IEncryption encryption;
    private final IPolice police;

    private final IVehicleRegistrationAuthority vra;


    public MobileNetworkModule(IPolice police, IVehicleRegistrationAuthority vra) {
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
