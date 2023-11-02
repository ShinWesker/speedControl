package komplexaufgabe.core.entities;

import komplexaufgabe.core.interfaces.components.IPolice;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.core.interfaces.encryption.IEncryption;
import komplexaufgabe.simulate.ParkingSpace;

import java.util.ArrayList;
import java.util.List;

public class Police implements IPolice {
    private final List<Owner> wantedOwners;
    private  final List<Owner> arrestedOwners;
    private  final List<Car> confiscatedCars;
    private ParkingSpace parkingSpace;

    private final IEncryption encryption;

    public Police() {
        this.wantedOwners = new ArrayList<>();
        this.arrestedOwners = new ArrayList<>();
        this.confiscatedCars = new ArrayList<>();
        this.encryption = new AES();
    }

    public void arrestOwner(String face) {
        for (Owner wantedOwner : wantedOwners) {
            if (wantedOwner.getFace().equals(encryption.decrypt(face))) {
                wantedOwners.remove(wantedOwner);
                arrestedOwners.add(wantedOwner);
                break;
            }
        }

    }

    public void confiscateCar(Car car) {
        confiscatedCars.add(car);
        parkingSpace.removeCar(car);
    }

    public void addWanted(Owner owner) {
        this.wantedOwners.add(owner);
    }

    public boolean checkWanted(String data) {

        for (Owner wantedOwner : wantedOwners) {
            if (wantedOwner.getFace().equals(encryption.decrypt(data))) {
                return true;
            }
        }


        return false;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }
}