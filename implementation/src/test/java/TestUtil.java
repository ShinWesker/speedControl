import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.*;
import komplexaufgabe.core.entities.*;
import komplexaufgabe.core.interfaces.components.IPolice;
import komplexaufgabe.core.interfaces.components.IVehicleRegistrationAuthority;
import komplexaufgabe.core.interfaces.policy.GermanPolicy;
import komplexaufgabe.core.interfaces.stoppingtools.TrafficSpikes;
import komplexaufgabe.io.CSVParser;
import komplexaufgabe.io.IFileParser;
import komplexaufgabe.simulate.ParkingSpace;
import komplexaufgabe.simulate.Simulation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestUtil {

    public static SpeedCamera initSpeedCamera(IVehicleRegistrationAuthority vra, IPolice police) {
        Stack<Object> componentsStack = new Stack<>();

        LED led = new LED();
        CentralUnit centralUnit = new CentralUnit();
        LaserScanner laserScanner = new LaserScanner();

        // Push the objects into the stack
        componentsStack.push(led);
        componentsStack.push(laserScanner);
        componentsStack.push(centralUnit);

        return new SpeedCamera.CameraBuilder(
                componentsStack,
                new TrafficSpikes(),
                new MobileNetworkModule(police, vra)).build();
    }

    public static SpeedCamera initSpeedCamera() {
        return initSpeedCamera(new VehicleRegistrationAuthority(), new Police());
    }

    public static void runSetPolicySimulation(SpeedCamera speedCamera) {
        Simulation simulation = new Simulation(new ParkingSpace(getCarsFromFile()), speedCamera);
        speedCamera.activate();
        speedCamera.getFineEngine().setPolicy(new GermanPolicy("fine_catalogue.json"));
        simulation.start();
        speedCamera.deactivate();
    }

    public static List<Car> get100CarsFromParkingSpace(){
        List<Car> carList = getCarsFromFile();

        ParkingSpace parkingSpace = new ParkingSpace(carList);
        return List.of(parkingSpace.get100Cars());
    }

    public static List<Car> getCarsFromFile() {
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

            carList.add(car);
        }
        return carList;
    }

    public static Owner createOwner() {
        String name = "Name";
        Date brithDate = new Date();
        String face = "ACFFAEFEAAACCCCC";
        SmartPhone smartphone = new SmartPhone(123456789);
        Car car = createCar();

        return new Owner.OwnerBuilder(name, brithDate, face, smartphone, car).build();
    }

    public static Car createCar() {
        String manufacturer = "BMW";
        String registrationID = "test";
        LicensePlate licensePlate = new LicensePlate("licensePlateID");

        return new Car.CarBuilder(manufacturer, registrationID, licensePlate).build();
    }







}
