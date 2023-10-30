package komplexaufgabe.core.entities;

import io.cucumber.java.bs.A;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.core.interfaces.encryption.IEncryption;

import java.util.ArrayList;
import java.util.List;

public class Police {
    private List<Owner> wantedOwners;
    private List<Owner> arrestedOwners;
    private List<Car> confiscatedCars;

    private final IEncryption encryption;

    public Police() {
        this.wantedOwners = new ArrayList<>();
        this.arrestedOwners = new ArrayList<>();
        this.confiscatedCars = new ArrayList<>();
        this.encryption = new AES();
    }

    public void arrestOwner(Owner owner) {
        if (wantedOwners.contains(owner)) {
            wantedOwners.remove(owner);
            arrestedOwners.add(owner);
        } else {
            System.out.println("This owner is not on the wanted list.");
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
            if (wantedOwner.getFace().equals(encryption.decrpyt(data))) {
                return true;
            }
        }
        return false;
    }
}