package komplexaufgabe.simulate;


import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.entities.Car;
import komplexaufgabe.randomUtil.MersenneTwister;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Simulation {

    private final ParkingSpace parkingSpace;
    private SpeedCamera speedCamera;
    private final Queue<Car> simulationCars = new LinkedList<>();

    public Simulation(ParkingSpace parkingSpace, SpeedCamera speedCamera) {
        this.parkingSpace = parkingSpace;
        this.speedCamera = speedCamera;
    }


    public void start() {
        Car[] cars = parkingSpace.get100Cars();
        for (Car car : cars) {
            car.setSpeed(generateSpeed());

            // Magic (too fast for Twister)
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Collections.addAll(simulationCars, cars);

        while (!simulationCars.isEmpty()) {
            speedCamera.controlCar(simulationCars.poll());
        }


    }

    private static int generateSpeed() {
        MersenneTwister mersenneTwister = new MersenneTwister();
        // 90%
        if (mersenneTwister.nextInt(0, 100) > 10) {
            return mersenneTwister.nextInt(45, 53);
        }
        // 10%
        else {
            // 85%
            if (mersenneTwister.nextInt(0, 100) > 15) {
                return mersenneTwister.nextInt(54, 74);
            }
            // 15%
            else {
                return mersenneTwister.nextInt(75, 120);
            }

        }

    }
}