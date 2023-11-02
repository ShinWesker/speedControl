package komplexaufgabe.core.interfaces.components;

import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.entities.Owner;
import komplexaufgabe.simulate.ParkingSpace;

public interface IPolice {
    void arrestOwner(String face);

    void confiscateCar(Car car);

    void addWanted(Owner owner);

    boolean checkWanted(String data);

    void setParkingSpace(ParkingSpace parkingSpace);
}
