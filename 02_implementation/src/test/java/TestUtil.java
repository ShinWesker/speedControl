import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.*;
import komplexaufgabe.core.entities.*;
import komplexaufgabe.core.interfaces.components.IPolice;
import komplexaufgabe.core.interfaces.components.IVehicleRegistrationAuthority;
import komplexaufgabe.core.interfaces.stoppingtools.TrafficSpikes;
import komplexaufgabe.io.CSVParser;
import komplexaufgabe.io.IFileParser;
import komplexaufgabe.simulate.ParkingSpace;
import komplexaufgabe.simulate.Simulation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.spy;

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

        return new SpeedCamera.CameraBuilder(componentsStack, new TrafficSpikes(), new MobileNetworkModule(police, vra)).build();
    }

    public static SpeedCamera initSpeedCamera() {
        return initSpeedCamera(new VehicleRegistrationAuthority(), new Police());
    }

    public static ParkingSpace initParkingSpace() {
        List<Car> carList = getCarsFromFile(new VehicleRegistrationAuthority(), new Police());
        return new ParkingSpace(carList);
    }

    public static Simulation initSimulation() {
        return new Simulation(initParkingSpace(), initSpeedCamera());
    }

    public static List<Car> getCarsFromFile(IVehicleRegistrationAuthority vra, IPolice police) {
        IFileParser csvParser = new CSVParser();
        List<String[]> csvOut = csvParser.parse("data.csv");
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
            vra.registerCar(car.getLicensePlate().getLicensePlateID(), owner);
            MobileCentralUnit.addOwner(smartPhone.getPhoneNumber(), smartPhone);
        }
        return carList;
    }

    public static Owner createOwner() {
        String name = "Name";
        Date brithDate = new Date();
        String face = "CFFAEFEAAACCCCC";
        SmartPhone smartphone = spy(new SmartPhone(123456789));
        Car car = createCar();
        Owner owner = new Owner.OwnerBuilder(name, brithDate, face, smartphone, car).build();
        car.setDriver(owner);

        return owner;
    }

    public static Car createCar() {
        String manufacturer = "BMW";
        String registrationID = "6afwib6x3t";
        LicensePlate licensePlate = new LicensePlate("RNH731");

        return new Car.CarBuilder(manufacturer, registrationID, licensePlate).build();
    }
}
