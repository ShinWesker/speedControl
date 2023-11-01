import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.io.CSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExportTest {

    @Test
    public void create_export() {
        SpeedCamera speedCamera = TestUtil.initSpeedCamera();

        TestUtil.runSetPolicySimulation(speedCamera);

        speedCamera.export();

        CSVParser csvParser = new CSVParser();
        String exportPath = "./src/main/java/export/export.csv";
        List<String[]> exportLines = csvParser.parse(exportPath);

        assertNotNull(exportLines);
        assertFalse(exportLines.isEmpty());

        exportLines.forEach(Assertions::assertNotNull);
    }
}
