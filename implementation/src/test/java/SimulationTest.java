import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.*;
import komplexaufgabe.core.entities.*;
import komplexaufgabe.core.interfaces.components.IPolice;
import komplexaufgabe.core.interfaces.components.IVehicleRegistrationAuthority;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.simulate.ParkingSpace;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimulationTest {

    @Order(1)
    @Test
    public void moving_cars_from_parking_space_to_waiting_queue() {

    }

    /*@Order(2)
    @Test
    public void car_speed_between_45_and_120() {
        List<Car> cars = TestUtil.getCarsFromFile();
        cars.forEach(car -> {
            Assertions.assertTrue(45 < car.getSpeed() && car.getSpeed() < 120);
        });
    }

    @Order(3)
    @Test
    public void car_speed_percentage_test() {
        int tolerance= 3;

        List<Car> cars = TestUtil.getCarsFromFile();
        int countf10t20 = 0;
        int countf21t70 = 0;

        for(Car car : cars){
            int speed = car.getSpeed();
            if (speed > 53) {
                if (speed > 70) {
                    countf21t70 ++;
                } else {
                    countf10t20+=1;
                }
            }
        }

        Assertions.assertTrue(
                cars.size()/countf10t20<10+tolerance
                    && cars.size()/countf10t20>10-tolerance);

        Assertions.assertTrue(
                cars.size()/countf21t70<15+tolerance
                        && cars.size()/countf21t70>15-tolerance);

    }
*/
//In File "StepDefinitions"
//    @Order(4)
//    @Test
//    public void laser_scanner_to_fine_engine_test___evtl_als_bdd() {
//
//    }


    @TestFactory
    @Order(5)
    public Stream<DynamicTest> aiengine_face_and_licenseplate_extraction() {
        Camera camera = new Camera();
        AIEngine aiEngine = new AIEngine();
        List<Car> carList = TestUtil.getCarsFromFile();

        return carList.stream().map(car -> DynamicTest.dynamicTest("Extracting Face and licensePlate from car: " + car.toString(), () -> {
            String[] expectedData = new String[]{car.getDriver().getFace(), car.getLicensePlate().getLicensePlateID()};

            CameraData cameraData = camera.takePhoto(car);
            String[] extractedData = aiEngine.extractData(cameraData);
            assertArrayEquals(expectedData, extractedData);
        }));
    }

    @Order(6)
    @Test
    @DisplayName("Tests the AES encryption and decryption with a simple word")
    public void aes_encryption() {
        AES aes = new AES();
        String text = "Hello";

        String encrypted = aes.encrypt(text);
        String decrypted = aes.decrypt(encrypted);

        assertEquals(text, decrypted);
    }

    @Order(7)
    @Test
    @DisplayName("MobileNetworkModule request to arrest owner and check if he becomes wanted")
    public void police_check_owner_wanted() {
        IPolice police = new Police();
        IVehicleRegistrationAuthority vra = new VehicleRegistrationAuthority();
        MobileNetworkModule mnm = new MobileNetworkModule(police, vra);

        String face = "CFFAEFEAAACCCCC";

        mnm.requestArrest(face);

        boolean isWanted = mnm.sendRequestToPolice(face);

        assertTrue(isWanted);
    }

    @Order(8)
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

    @Order(9)
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

    @Order(11)
    @Test
    public void fine_engine_record_creation() {

    }

    @Order(12)
    @Test
    public void fine_engine_access_wallet_and_fine_money() {
        IVehicleRegistrationAuthority vra = new VehicleRegistrationAuthority();
        SpeedCamera speedCamera = TestUtil.initSpeedCamera(vra, new Police());

        Owner owner = spy(TestUtil.createOwner());
        MobileCentralUnit.addOwner(owner.getSmartPhone().getPhoneNumber(), owner.getSmartPhone());

        vra.registerCar(owner.getCar().getLicensePlate().getLicensePlateID(), owner);

        CameraData data = new Camera().takePhoto(owner.getCar());
        speedCamera.getFineEngine().processCase(data, 200);

        verify(owner.getSmartPhone()).fineWallet(50);
    }

    @Order(13)
    @Test
    public void traffic_spike_stop_car_after_fahndung() {
        SpeedCamera speedCamera = TestUtil.initSpeedCamera();
        Car car = TestUtil.createCar();
        car.setDriver(TestUtil.createOwner());
        car.setSpeed(500);

        // Redirect System.out to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the method that produces the output
        speedCamera.controlCar(car);

        // Get the captured output
        String actualOutput = outputStream.toString().trim();
        String expectedOutput = "Traffic spikes thrown!";
        assertEquals(expectedOutput, actualOutput);
    }

    @Order(14)
    @Test
    public void owner_festname() {
        Police police = new Police();
        Owner owner = TestUtil.createOwner();
        police.addWanted(owner);
        AES aes = new AES();
        Assertions.assertTrue(police.checkWanted(aes.encrypt("ACFFAEFEAAACCCCC")));
        police.arrestOwner(aes.encrypt("ACFFAEFEAAACCCCC"));
        Assertions.assertFalse(police.checkWanted(aes.encrypt("ACFFAEFEAAACCCCC")));
    }

    @Order(15)
    @Test
    public void confiscate_car() {
        ParkingSpace parkingSpace = mock(ParkingSpace.class);
        IPolice police = new Police();
        police.setParkingSpace(parkingSpace);

        Car car = TestUtil.createCar();

        police.confiscateCar(car);

        verify(parkingSpace).removeCar(car);
    }

    @Order(16)
    @Test
    public void test100cars(){
        ParkingSpace parkingSpace = new ParkingSpace(TestUtil.getCarsFromFile());
//         cars = parkingSpace.get100Cars();

    }
}
