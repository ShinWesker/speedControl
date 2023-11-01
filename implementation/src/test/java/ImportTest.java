import komplexaufgabe.CLI;
import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.interfaces.policy.GermanPolicy;
import komplexaufgabe.core.interfaces.policy.IPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

public class ImportTest {

    private SpeedCamera speedCamera;

    private final String fineCataloguePath = "./src/main/java/resources/fine_catalogue.json";

    @BeforeEach
    public void setup() {
        speedCamera = TestUtil.initSpeedCamera();
    }

    /*@Order(1)
    @Test
    public void can_import_after_startup() {
        CLI cli = spy(new CLI());
        cli.start();

        System.setIn(new ByteArrayInputStream("1".getBytes())); // startup speedcamera
        System.setIn(new ByteArrayInputStream("2".getBytes())); // import policy

        // answer policy questions
        System.setIn(new ByteArrayInputStream("1".getBytes())); // Officer ID 0 or 1
        System.setIn(new ByteArrayInputStream("234".getBytes())); // Officer 0 pwd: 123, officer 1 pwd: 234
        System.setIn(new ByteArrayInputStream("y".getBytes()));


        System.setIn(System.in);

        //verify(speedCamera)
    }*/

    @Order(2)
    @ParameterizedTest(name = "{index} => Validate Officer with id {0} and pwd: {1}")
    @CsvSource({
            "0, 123",
            "1, 234"
    })
    public void validate_officer_id_and_password(int id, int pwd) {
        assertTrue(speedCamera.getCentralUnit().validateOfficer(id, pwd));
    }

    @Order(3)
    @ParameterizedTest(name = "{index} => Fine for {0} km/h would be {1} Euro")
    @CsvSource({
            "145, 843.5",
            "82, 208.5",
            "99, 428.5",
            "75, 143.5",
            "112, 591.5",
            "54, 58.5",
            "88, 288.5"
    })
    public void import_fine_catalogue(int speed, double fine) {
        IPolicy policy = new GermanPolicy(fineCataloguePath);

        assertEquals(fine, policy.getFine(speed));
    }

    @Order(4)
    @Test
    public void set_fine_policy() {
        IPolicy policy = new GermanPolicy(fineCataloguePath);
        speedCamera.getFineEngine().setPolicy(policy);

        assertEquals(policy, speedCamera.getFineEngine().getPolicy());
    }
}
