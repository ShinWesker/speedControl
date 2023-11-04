import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.*;
import komplexaufgabe.core.entities.CameraData;
import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.entities.Owner;
import komplexaufgabe.core.entities.Police;
import komplexaufgabe.core.interfaces.components.IPolice;
import komplexaufgabe.core.interfaces.components.IVehicleRegistrationAuthority;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.core.interfaces.policy.GermanPolicy;
import komplexaufgabe.core.interfaces.policy.IPolicy;
import komplexaufgabe.core.interfaces.stoppingtools.TrafficSpikes;
import komplexaufgabe.simulate.ParkingSpace;
import komplexaufgabe.simulate.Simulation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimulationTest {

    @Order(1)
    @Test
    public void car_speed_between_45_and_130() {
        Simulation simulation = TestUtil.initSimulation();

        for (int i = 1; i < 1000; i++) {
            assertTrue(45 <= simulation.generateSpeed() && simulation.generateSpeed() < 130);
        }
    }

    @TestFactory
    @Order(2)
    public Stream<DynamicTest> aiengine_face_and_licenseplate_extraction() {
        Camera camera = new Camera();
        AIEngine aiEngine = new AIEngine();
        List<Car> carList = TestUtil.getCarsFromFile(new VehicleRegistrationAuthority(), new Police());

        return carList.stream().map(car -> DynamicTest.dynamicTest("Extracting Face and licensePlate from car: " + car.toString(), () -> {
            String[] expectedData = new String[]{car.getDriver().getFace(), car.getLicensePlate().getLicensePlateID()};

            CameraData cameraData = camera.takePhoto(car);
            String[] extractedData = aiEngine.extractData(cameraData);
            assertArrayEquals(expectedData, extractedData);
        }));
    }

    @Order(4)
    @Test
    @DisplayName("Tests the AES encryption and decryption with a simple word")
    public void aes_encryption() {
        AES aes = new AES();
        String text = "Hello";

        String encrypted = aes.encrypt(text);

        String decrypted = aes.decrypt(encrypted);

        assertNotEquals(text, encrypted);
        assertEquals(text, decrypted);
    }

    @Order(5)
    @Test
    @DisplayName("MobileNetworkModule request to arrest owner and check if he becomes wanted")
    public void police_check_owner_wanted() {
        IPolice police = new Police();
        IVehicleRegistrationAuthority vra = new VehicleRegistrationAuthority();
        MobileNetworkModule mnm = new MobileNetworkModule(police, vra);

        Owner owner = TestUtil.createOwner();
        police.addWanted(owner);

        assertTrue(mnm.sendRequestToPolice(owner.getFace()));
        mnm.requestArrest(owner.getFace());
        assertFalse(mnm.sendRequestToPolice(owner.getFace()));
    }

    @Order(6)
    @Test
    public void vra_car_request() {
        IPolice police = new Police();
        IVehicleRegistrationAuthority vra = new VehicleRegistrationAuthority();
        MobileNetworkModule mnm = new MobileNetworkModule(police, vra);

        Owner owner = TestUtil.createOwner();
        String licensePlateID = owner.getCar().getLicensePlate().getLicensePlateID();

        vra.registerCar(licensePlateID, owner);
        Car car = mnm.vraGetCar(licensePlateID);

        assertEquals(owner.getCar(), car);
    }

    @Order(7)
    @Test
    public void vra_owner_request() {
        IPolice police = new Police();
        IVehicleRegistrationAuthority vra = new VehicleRegistrationAuthority();
        MobileNetworkModule mnm = new MobileNetworkModule(police, vra);

        Owner owner = TestUtil.createOwner();
        String[] ownerInfos = new String[]{owner.getName(), String.valueOf(owner.getBirthDate().getTime()), String.valueOf(owner.getSmartPhone().getPhoneNumber())};
        String licensePlateID = owner.getCar().getLicensePlate().getLicensePlateID();

        vra.registerCar(licensePlateID, owner);
        String[] vraOwnerInfos = mnm.sendRequestToVRA(licensePlateID);

        assertArrayEquals(ownerInfos, vraOwnerInfos);
    }

    @Order(8)
    @Test
    public void fine_engine_record_creation() {
        IVehicleRegistrationAuthority vra = new VehicleRegistrationAuthority();
        Stack<Object> componentsStack = new Stack<>();

        LED led = new LED();
        CentralUnit centralUnit = spy(new CentralUnit());
        LaserScanner laserScanner = new LaserScanner();

        // Push the objects into the stack
        componentsStack.push(led);
        componentsStack.push(laserScanner);
        componentsStack.push(centralUnit);

        SpeedCamera speedCamera =  new SpeedCamera.CameraBuilder(componentsStack, new TrafficSpikes(), new MobileNetworkModule(new Police(), vra)).build();

        Owner owner = TestUtil.createOwner();
        MobileCentralUnit.addOwner(owner.getSmartPhone().getPhoneNumber(), owner.getSmartPhone());

        vra.registerCar(owner.getCar().getLicensePlate().getLicensePlateID(), owner);

        CameraData data = new Camera().takePhoto(owner.getCar());
        IPolicy policy = mock(GermanPolicy.class);
        int speed = 200;
        when(policy.getFine(200)).thenReturn(1000.0);

        speedCamera.getFineEngine().setPolicy(policy);
        speedCamera.getFineEngine().processCase(data, speed);

        verify(speedCamera.getCentralUnit()).addRecord(any());
    }

    @Order(9)
    @ParameterizedTest(name = "{index} => Fine {1} Euro for {0} km/h fined")
    @CsvSource({
            "130, 843.5",
            "82, 208.5",
            "99, 428.5",
            "75, 143.5",
            "112, 591.5",
            "54, 58.5",
            "88, 288.5"
    })
    public void fine_engine_access_wallet_and_fine_money(int speed, double fine) {
        IVehicleRegistrationAuthority vra = new VehicleRegistrationAuthority();
        SpeedCamera speedCamera = TestUtil.initSpeedCamera(vra, new Police());
        IPolicy policy = mock(GermanPolicy.class);
        when(policy.getFine(speed)).thenReturn(fine);

        Owner owner = TestUtil.createOwner();
        MobileCentralUnit.addOwner(owner.getSmartPhone().getPhoneNumber(), owner.getSmartPhone());
        vra.registerCar(owner.getCar().getLicensePlate().getLicensePlateID(), owner);

        CameraData data = new Camera().takePhoto(owner.getCar());
        speedCamera.getFineEngine().setPolicy(policy);
        speedCamera.getFineEngine().processCase(data, speed);

        verify(owner.getSmartPhone()).fineWallet(fine);
    }

    @Order(10)
    @ParameterizedTest(name = "{index} => {0} stopping car!")
    @CsvSource({
            "TrafficSpikes, Traffic spikes thrown!"
    })
    public void stopping_tools_stopping_car(String stoppingTool, String expectedOutput) {
        IVehicleRegistrationAuthority vra = new VehicleRegistrationAuthority();
        IPolice police = new Police();
        police.setParkingSpace(new ParkingSpace(TestUtil.getCarsFromFile(vra, police)));
        SpeedCamera speedCamera = TestUtil.initSpeedCamera(vra, police);

        Car car = TestUtil.createCar();
        Owner owner = TestUtil.createOwner();
        MobileCentralUnit.addOwner(owner.getSmartPhone().getPhoneNumber(), owner.getSmartPhone());
        car.setDriver(owner);
        car.setSpeed(500);
        police.addWanted(owner);

        vra.registerCar(car.getLicensePlate().getLicensePlateID(), owner);
        IPolicy policy = mock(GermanPolicy.class);
        when(policy.getFine(500)).thenReturn(1000.0);
        speedCamera.getFineEngine().setPolicy(policy);

        // Redirect System.out to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the method that produces the output
        speedCamera.controlCar(car);

        // Get the captured output
        String actualOutput = outputStream.toString().trim().split("\n")[1].trim();
        assertEquals(expectedOutput, actualOutput);
    }

    @Order(11)
    @Test
    public void owner_arrest() {
        Police police = new Police();
        Owner owner = TestUtil.createOwner();
        police.addWanted(owner);
        AES aes = new AES();
        String encrypted = aes.encrypt("CFFAEFEAAACCCCC");


        assertTrue(police.checkWanted(encrypted));
        police.arrestOwner(encrypted);
        assertFalse(police.checkWanted(encrypted));
    }

    @Order(12)
    @Test
    public void confiscate_car() {
        ParkingSpace parkingSpace = mock(ParkingSpace.class);
        IPolice police = new Police();
        police.setParkingSpace(parkingSpace);

        Car car = TestUtil.createCar();

        police.confiscateCar(car);

        verify(parkingSpace).removeCar(car);
    }
}
