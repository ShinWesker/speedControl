package komplexaufgabe.core.entities;

import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.core.interfaces.encryption.IEncryption;

import java.util.ArrayList;
import java.util.List;

public class Police {
    private final List<Owner> wantedOwners;
    private final List<Owner> arrestedOwners;
    private final List<Car> confiscatedCars;

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
            }
        }


    }

    public void confiscateCar(Car car) {
        confiscatedCars.add(car);
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
}