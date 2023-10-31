import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.entities.LicensePlate;
import komplexaufgabe.core.entities.Owner;
import komplexaufgabe.core.entities.SmartPhone;
import komplexaufgabe.core.interfaces.encryptionhash.MD5;
import komplexaufgabe.core.interfaces.encryptionhash.SHA256;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber-report.html"}, features = {"src/test/resources"})
public class TestApplication {

    @BeforeEach
    public void setup() {

    }


    @Order(1)
    @ParameterizedTest(name = "{index} => Building Car with manufacturer: {0}, registrationID: {1}, speed: {2}, licensePlateID: {3}")
    @CsvSource({"test, test, 10, lp"})
    public void buildCar(String manufacturer, String registrationID, int speed, String licensePlateID) {
        LicensePlate lp = new LicensePlate(licensePlateID);
        Car car = new Car.CarBuilder(manufacturer, registrationID, speed, lp).build();

        assertEquals(manufacturer, car.getManufacturer());
        assertEquals(registrationID, car.getRegistrationID());
        assertEquals(speed, car.getSpeed(), 0);
        assertEquals(licensePlateID, car.getLicensePlate().getLicensePlateID());
    }

    @Order(2)
    @ParameterizedTest(name = "{index} => Building Car with name: {0}, birthDate: {1}, face: {2}, phonenumber: {3}")
    @CsvSource({"Jon Doe, 1667222847, face, 12421512"})
    public void buildOwner(String name, long birthDateTimestamp, String face, int phonenumber) {
        Date birthDate = new Date();
        birthDate.setTime(birthDateTimestamp);
        SmartPhone smartPhone = new SmartPhone(phonenumber);
        Owner owner = new Owner.OwnerBuilder(name, birthDate, face, smartPhone, null).build();

        assertEquals(name, owner.getName());
        assertEquals(birthDate, owner.getBirthDate());
        assertEquals(face, owner.getFace());
        assertEquals(phonenumber, owner.getSmartPhone().getPhoneNumber());
    }

    @Order(3)
    @ParameterizedTest(name = "{index} => MD5-Hash {0} to {1}")
    @CsvSource({"Hallo, d1bf93299de1b68e6d382c893bf1215f"})
    public void md5Test(String text, String hashedText) {
        MD5 md5 = new MD5();

        assertEquals(hashedText, md5.encrypt(text));
    }

    @Order(4)
    @ParameterizedTest(name = "{index} => SHA256-Hash {0} to {1}")
    @CsvSource({"Hallo, 533ea3ddaaede1572aa73579252794bcebb318b0de102b062572cb5f953871ca"})
    public void sha256Test(String text, String hashedText) {
        SHA256 sha256 = new SHA256();

        assertEquals(hashedText, sha256.encrypt(text));
    }
}