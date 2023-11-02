import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.VehicleRegistrationAuthority;
import komplexaufgabe.core.entities.Police;
import komplexaufgabe.io.CSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExportTest {

    @Test
    //TODO: fix
    public void create_export() {
        SpeedCamera speedCamera = TestUtil.initSpeedCamera();

        TestUtil.runSetPolicySimulation(speedCamera);

        speedCamera.getCentralUnit().export();

        CSVParser csvParser = new CSVParser();
        List<String[]> exportLines = csvParser.parse("export/export.csv");

        assertNotNull(exportLines);
        assertFalse(exportLines.isEmpty());

        exportLines.forEach(Assertions::assertNotNull);
    }
}
