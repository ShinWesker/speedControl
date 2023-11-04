import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.VehicleRegistrationAuthority;
import komplexaufgabe.core.entities.Police;
import komplexaufgabe.core.interfaces.components.IPolice;
import komplexaufgabe.core.interfaces.components.IVehicleRegistrationAuthority;
import komplexaufgabe.core.interfaces.policy.GermanPolicy;
import komplexaufgabe.io.TextFileWriter;
import komplexaufgabe.simulate.ParkingSpace;
import komplexaufgabe.simulate.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {

    @BeforeEach
    public void setup() {
        new TextFileWriter().writeFile("report.log", " ");
    }

    @Test
    public void create_report() throws IOException {
        IVehicleRegistrationAuthority vra = new VehicleRegistrationAuthority();
        IPolice police = new Police();
        SpeedCamera speedCamera = TestUtil.initSpeedCamera(vra, police);
        Simulation simulation = new Simulation(new ParkingSpace(TestUtil.getCarsFromFile(vra, police)), speedCamera);

        speedCamera.activate();
        speedCamera.getFineEngine().setPolicy(new GermanPolicy("fine_catalogue.json"));
        simulation.start();

        speedCamera.getCentralUnit().createReportLog();

        InputStream stream = null;
        try {
            stream = this.getClass().getClassLoader().getResourceAsStream("export/report.log");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertNotNull(stream);
        assertFalse(new String(stream.readAllBytes()).trim().isEmpty());
    }
}
