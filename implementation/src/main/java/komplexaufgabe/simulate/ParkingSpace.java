package komplexaufgabe.simulate;

import komplexaufgabe.core.entities.Car;
import java.util.List;

public class ParkingSpace {
    private Car[][] parkingSlots = new Car[100][10];

    public ParkingSpace(List<Car> cars) {
        if (cars.size() > 1000) {
            throw new IllegalArgumentException("The list of cars exceeds the parking space capacity.");
        }

        // Filling the 2D array
        int count = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) {
                if (count < cars.size()) {
                    parkingSlots[i][j] = cars.get(count);
                    count++;
                } else {
                    break;
                }
            }
        }
    }

    public Car[] get100Cars() {
        Car[] result = new Car[100];
        int count = 0;
        for (int i = 0; i < parkingSlots.length && count < 100; i++) {
            for (int j = 0; j < parkingSlots[i].length && count < 100; j++) {
                result[count] = parkingSlots[i][j];
                count++;
            }
        }
        return result;
    }
}
