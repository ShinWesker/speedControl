package komplexaufgabe.simulate;

import komplexaufgabe.core.entities.Car;
import komplexaufgabe.randomUtil.MersenneTwister;

import java.util.Arrays;
import java.util.List;

public class ParkingSpace {
    private final Car[][] parkingSlots = new Car[50][20];

    public ParkingSpace(List<Car> cars) {
        if (cars.size() > 1000) {
            throw new IllegalArgumentException("The list of cars exceeds the parking space capacity.");
        }

        // Filling the 2D array
        int count = 0;
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 20; j++) {
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
        MersenneTwister mersenneTwister = new MersenneTwister();
        Car[] result = new Car[100];
        int count = 0;

        while (count < 100) {
            Car car = parkingSlots[mersenneTwister.nextInt(0, parkingSlots.length - 1)][mersenneTwister.nextInt(0, parkingSlots[0].length - 1)];
            if (car != null && !Arrays.stream(result).toList().contains(car)) {
                result[count] = car;
                count++;
            }
        }
        return result;
    }

    public void removeCar(Car car) {
        for (int i = 0; i < parkingSlots.length; i++) {
            for (int j = 0; j < parkingSlots[i].length; j++) {
                if (car == parkingSlots[i][j]) {
                    parkingSlots[i][j] = null;
                }
            }
        }
    }


}
