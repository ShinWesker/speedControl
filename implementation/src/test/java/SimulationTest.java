import komplexaufgabe.core.components.*;
import komplexaufgabe.core.entities.*;
import komplexaufgabe.core.interfaces.encryption.AES;
import komplexaufgabe.io.CSVParser;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SimulationTest {

    private final String dataPath = "./implementation/src/main/java/resources/data.csv";

    @BeforeEach
    public void setup() {

    }

    @Order(1)
    @Test
    public void moving_cars_from_parking_space_to_waiting_queue() {

    }

    @Order(2)
    @Test
    public void car_speed_between_45_and_120() {

    }

    @Order(3)
    @Test
    public void car_speed_percentage_test() {

    }

    @Order(4)
    @Test
    public void laser_scanner_to_fine_engine_test___evtl_als_bdd() {

    }


    @TestFactory
    @Order(5)
    public Stream<DynamicTest> aiengine_face_and_licenseplate_extraction() {
        Camera camera = new Camera();
        AIEngine aiEngine = new AIEngine();
        List<String[]> csvOut = new CSVParser().parse(dataPath);

        List<String[]> expectedData = new ArrayList<>();
        List<String[]> aiExtractedData = new ArrayList<>();

        for (int i = 1; i <= 25; i++) {
            Car car = new Car.CarBuilder(csvOut.get(i)[2], csvOut.get(i)[1], 50, new LicensePlate(csvOut.get(i)[0])).build();
            expectedData.add(new String[]{csvOut.get(i)[5], csvOut.get(i)[0]});

            CameraData data = camera.takePhoto(car);
            aiExtractedData.add(aiEngine.extractData(data));
        }

        return expectedData.stream().map(data -> DynamicTest.dynamicTest("Resolving cameraData: " + Arrays.toString(data), () -> {
            assertEquals(expectedData.indexOf(data), aiExtractedData.indexOf(data));
        }));
    }

    @Order(6)
    @Test
    public void aes_encryption() {
        AES aes = new AES();
        String text = "Hello";

        String encrypted = aes.encrypt(text);
        String decrypted = aes.decrypt(encrypted);

        assertEquals(text, decrypted);
    }

    @Order(7)
    @Test
    public void request_to_police() {
        MobileNetworkModule mnm = new MobileNetworkModule();
        String face = "CFFAEFEAAACCCCC";

        mnm.requestArrest(face);
        boolean isWanted = mnm.sendRequestToPolice(face);

        assertTrue(isWanted);
    }

    @Order(8)
    @Test
    public void request_to_vra() {
        MobileNetworkModule mnm = new MobileNetworkModule();

        String licensePlateID = "licenseID";
        String name = "Name";
        Date birthDate = new Date();

        LicensePlate licensePlate = new LicensePlate(licensePlateID);
        Car car = new Car.CarBuilder("BMW", "RegID", 50, licensePlate).build();
        Owner owner = new Owner.OwnerBuilder(name, birthDate, "FACE", new SmartPhone(143141434), car).build();

        String[] expectedData = new String[]{name, String.valueOf(birthDate.getTime()), String.valueOf(owner.getSmartPhone().getPhoneNumber())};

        mnm.registerCar(licensePlate, owner);

        assertArrayEquals(expectedData, mnm.sendRequestToVRA(licensePlateID));
    }

    @Order(11)
    @Test
    public void fine_engine_record_creation() {

    }

    @Order(12)
    @Test
    public void fine_engine_access_wallet_and_fine_money() {
        long phoneNumber = 421412421;
        SmartPhone smartPhone = mock(SmartPhone.class);
        MobileCentralUnit.addOwner(phoneNumber, smartPhone);

        FineEngine fineEngine = new FineEngine(TestUtil.initSpeedCamera());

        fineEngine.processCase(new CameraData("dwadwa", 21421421L), 200);

        verify(smartPhone).fineWallet(50);
    }

    @Order(13)
    @Test
    public void traffic_spike_stop_car_after_fahndung() {

    }

    @Order(14)
    @Test
    public void owner_festname() {

    }

    @Order(15)
    @Test
    public void car_beschlagnahmung() {

    }

    @Order(16)
    @Test
    public void car_gets_removed_from_parking_space() {

    }
}
