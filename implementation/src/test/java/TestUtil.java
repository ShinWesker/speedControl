import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.CentralUnit;
import komplexaufgabe.core.components.LED;
import komplexaufgabe.core.components.LaserScanner;
import komplexaufgabe.core.components.MobileCentralUnit;
import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.entities.LicensePlate;
import komplexaufgabe.core.entities.Owner;
import komplexaufgabe.core.entities.SmartPhone;
import komplexaufgabe.core.interfaces.policy.GermanPolicy;
import komplexaufgabe.core.interfaces.stoppingtools.TrafficSpikes;
import komplexaufgabe.io.CSVParser;
import komplexaufgabe.io.IFileParser;
import komplexaufgabe.simulate.ParkingSpace;
import komplexaufgabe.simulate.Simulation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

public class TestUtil {

    public static SpeedCamera initSpeedCamera() {
        Stack<Object> componentsStack = new Stack<>();

        LED led = new LED();
        CentralUnit centralUnit = new CentralUnit();
        LaserScanner laserScanner = new LaserScanner();

        // Push the objects into the stack
        componentsStack.push(led);
        componentsStack.push(laserScanner);
        componentsStack.push(centralUnit);


        return new SpeedCamera.CameraBuilder(componentsStack, new TrafficSpikes()).build();
    }

    public static void runSimulation(SpeedCamera speedCamera) {
        speedCamera.activate();
        speedCamera.getFineEngine().setPolicy(new GermanPolicy("./src/main/java/resources/fine_catalogue.json"));
        speedCamera.getSimulation().start();
        speedCamera.deactivate();
    }

    public static List<Car> get100CarsFromParkingSpace(){
        SpeedCamera speedCamera = TestUtil.initSpeedCamera();

        IFileParser csvParser = new CSVParser();
        List<String[]> csvOut = csvParser.parse("./src/main/java/resources/data.csv");
        List<Car> carList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
        for (int i = 1; i < csvOut.size(); i++) {

            Car car = new Car.CarBuilder(csvOut.get(i)[2], csvOut.get(i)[1], 50, new LicensePlate(csvOut.get(i)[0])).build();
            Owner owner;
            SmartPhone smartPhone = new SmartPhone(Long.parseLong(csvOut.get(i)[6].replaceAll("\\s", "")));
            try {
                owner = new Owner.OwnerBuilder(csvOut.get(i)[3], formatter.parse(csvOut.get(i)[4]), csvOut.get(i)[5], smartPhone, car).build();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            car.setDriver(owner);
            carList.add(car);
            speedCamera.getMobileNetworkModule().registerCar(car.getLicensePlate(), owner);
        }

        ParkingSpace parkingSpace = new ParkingSpace(carList);
        return List.of(parkingSpace.get100Cars());
    }
}
