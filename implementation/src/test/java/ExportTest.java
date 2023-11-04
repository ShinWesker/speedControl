import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.VehicleRegistrationAuthority;
import komplexaufgabe.core.entities.Police;
import komplexaufgabe.core.interfaces.components.IPolice;
import komplexaufgabe.core.interfaces.components.IVehicleRegistrationAuthority;
import komplexaufgabe.core.interfaces.policy.GermanPolicy;
import komplexaufgabe.io.CSVParser;
import komplexaufgabe.simulate.ParkingSpace;
import komplexaufgabe.simulate.Simulation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

public class ExportTest {

    String exportPath = "export/export.csv";

    @Test
    public void create_export() {
        IVehicleRegistrationAuthority vra = new VehicleRegistrationAuthority();
        IPolice police = new Police();
        SpeedCamera speedCamera = TestUtil.initSpeedCamera(vra, police);
        Simulation simulation = new Simulation(new ParkingSpace(TestUtil.getCarsFromFile(vra, police)), speedCamera);

        speedCamera.activate();
        speedCamera.getFineEngine().setPolicy(new GermanPolicy("fine_catalogue.json"));
        simulation.start();

        try {
            Path path = Paths.get(this.getClass().getClassLoader().getResource(exportPath).toURI());
            Files.deleteIfExists(path);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        speedCamera.getCentralUnit().export();

        CSVParser csvParser = new CSVParser();
        List<String[]> exportLines = csvParser.parse(exportPath);

        assertNotNull(exportLines);
        assertFalse(exportLines.isEmpty());
    }
}
