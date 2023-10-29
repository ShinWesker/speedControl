package komplexaufgabe.core.entities;

import java.util.ArrayList;
import java.util.List;

public class Police {
    private List<Owner> wantedOwners;
    private List<Owner> arrestedOwners;
    private List<Car> confiscatedCars;

    public Police() {
        this.wantedOwners = new ArrayList<>();
        this.arrestedOwners = new ArrayList<>();
        this.confiscatedCars = new ArrayList<>();
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

}