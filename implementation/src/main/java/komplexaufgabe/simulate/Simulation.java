package komplexaufgabe.simulate;


import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.Camera;
import komplexaufgabe.core.components.MobileCentralUnit;
import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.entities.LicensePlate;
import komplexaufgabe.core.entities.Owner;
import komplexaufgabe.core.entities.SmartPhone;
import komplexaufgabe.io.CSVParser;
import komplexaufgabe.io.FileParser;
import komplexaufgabe.randomUtil.MersenneTwister;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Simulation {

    private final ParkingSpace parkingSpace;
    private final SpeedCamera speedCamera;
    private Queue<Car> simulationCars = new LinkedList<>();

    public Simulation(SpeedCamera speedCamera) {
        this.speedCamera = speedCamera;

        this.parkingSpace = new ParkingSpace(getCarsFromFile());
    }



    private List<Car> getCarsFromFile() {
        FileParser csvParser = new CSVParser();
        List<String[]> csvOut = csvParser.parse("./src/main/java/resources/data.csv");
        List<Car> carList = new ArrayList<Car>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
        for (int i = 1; i < csvOut.size(); i++) {

            Car car = new Car.CarBuilder(csvOut.get(i)[2], csvOut.get(i)[1], generateSpeed(), new LicensePlate(csvOut.get(i)[0])).build();
            Owner owner;
            SmartPhone smartPhone = new SmartPhone(Long.parseLong(csvOut.get(i)[6].replaceAll("\\s","")));
            try {
                owner = new Owner.OwnerBuilder(csvOut.get(i)[3], formatter.parse(csvOut.get(i)[4]), csvOut.get(i)[5], smartPhone, car).build();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            car.setDriver(owner);

            if (csvOut.get(i)[7].equals("yes")) {
                speedCamera.addWanted(owner);
            }
            carList.add(car);
            speedCamera.getMobileNetworkModule().registerCar(car.getLicensePlate(), owner);
        //TODO ADD Owner With Phone How to do ENUM
        }
        return carList;
    }

    private int generateSpeed() {
        MersenneTwister mersenneTwister = new MersenneTwister();
        // 90%
        if(mersenneTwister.nextInt(0, 100) >10) {
            return mersenneTwister.nextInt(45, 53);
        }
        // 10%
        else {
            // 85%
            if (mersenneTwister.nextInt(0,100) > 15) {
                return mersenneTwister.nextInt(54, 74);
            }
            // 15%
            else {
                return mersenneTwister.nextInt(75, 120);
            }

        }
    }

    public void start(){
        Car[] cars = parkingSpace.get100Cars();
        Collections.addAll(simulationCars, cars);

        while (!simulationCars.isEmpty()){
           speedCamera.controlCar(simulationCars.poll());
        }

    }

}