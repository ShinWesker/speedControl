package komplexaufgabe.core.components;

import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.interfaces.components.ILaserScanner;

public class LaserScanner implements ILaserScanner {

    public int detectSpeed(Car car) {
        return car.getSpeed();
    }
}
