import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.FineEngine;
import komplexaufgabe.core.components.VehicleRegistrationAuthority;
import komplexaufgabe.core.entities.Police;
import komplexaufgabe.core.interfaces.components.IFineEngine;
import komplexaufgabe.core.interfaces.policy.GermanPolicy;
import komplexaufgabe.core.interfaces.policy.IPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ImportTest {

    private SpeedCamera speedCamera;

    private final String fineCataloguePath = "fine_catalogue.json";

    @BeforeEach
    public void setup() {
        speedCamera = TestUtil.initSpeedCamera(new VehicleRegistrationAuthority(), new Police());
    }

    @Order(2)
    @ParameterizedTest(name = "{index} => Validate Officer with id {0} and pwd: {1}")
    @CsvSource({
            "0, 1234",
            "1, 2345"
    })
    public void validate_officer_id_and_password(int id, int pwd) {
        assertTrue(speedCamera.getCentralUnit().validateOfficer(id, pwd));
    }

    @Order(3)
    @ParameterizedTest(name = "{index} => Fine for {0} km/h would be {1} Euro")
    @CsvSource({
            "130, 843.5",
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
        IFineEngine fineEngine = mock(FineEngine.class);

        speedCamera = spy(TestUtil.initSpeedCamera());
        when(speedCamera.getFineEngine()).thenReturn(fineEngine);

        speedCamera.getFineEngine().setPolicy(policy);

        verify(fineEngine).setPolicy(policy);
    }
}
