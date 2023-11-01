import komplexaufgabe.CLI;
import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.*;
import komplexaufgabe.core.entities.*;
import komplexaufgabe.core.interfaces.stoppingtools.TrafficSpikes;
import komplexaufgabe.io.CSVParser;
import komplexaufgabe.io.IFileParser;
import komplexaufgabe.randomUtil.MersenneTwister;
import komplexaufgabe.simulate.ParkingSpace;
import komplexaufgabe.simulate.Simulation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

public class Application {
    private static SpeedCamera speedCamera;
    private static Police police;

    public static void main(String... args) {

        Stack<Object> componentsStack = new Stack<>();

        LED led = new LED();
        CentralUnit centralUnit = new CentralUnit();
        LaserScanner laserScanner = new LaserScanner();

        // Push the objects into the stack
        componentsStack.push(led);
        componentsStack.push(laserScanner);
        componentsStack.push(centralUnit);


        police = new Police();
        speedCamera = new SpeedCamera.CameraBuilder(
                componentsStack,
                new TrafficSpikes(),
                new MobileNetworkModule(police)).build();

        ParkingSpace parkingSpace = new ParkingSpace(getCarsFromFile());
        Simulation simulation = new Simulation(parkingSpace);
        simulation.setSpeedCamera(speedCamera);

        police.setParkingSpace(simulation.getParkingSpace());

        CLI cli = new CLI(speedCamera, simulation);
        cli.start();
    }

    private static List<Car> getCarsFromFile() {
        IFileParser csvParser = new CSVParser();
        List<String[]> csvOut = csvParser.parse("/data.csv");
        List<Car> carList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
        for (int i = 1; i < csvOut.size(); i++) {

            Car car = new Car.CarBuilder(csvOut.get(i)[2], csvOut.get(i)[1], new LicensePlate(csvOut.get(i)[0])).build();
            Owner owner;
            SmartPhone smartPhone = new SmartPhone(Long.parseLong(csvOut.get(i)[6].replaceAll("\\s", "")));
            try {
                owner = new Owner.OwnerBuilder(csvOut.get(i)[3], formatter.parse(csvOut.get(i)[4]), csvOut.get(i)[5], smartPhone, car).build();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            car.setDriver(owner);

            if (csvOut.get(i)[7].equals("yes")) {
                police.addWanted(owner);
            }
            carList.add(car);
            speedCamera.getMobileNetworkModule().registerCar(car.getLicensePlate(), owner);
            MobileCentralUnit.addOwner(smartPhone.getPhoneNumber(), smartPhone);
        }
        return carList;
    }


}