import komplexaufgabe.core.SpeedCamera;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReportTest {

    @Test
    public void create_report() throws IOException {
        SpeedCamera speedCamera = TestUtil.initSpeedCamera();

        TestUtil.runSetPolicySimulation(speedCamera);

        speedCamera.createReportLog();

        String logPath = "./src/main/java/export/report.log";
        Stream<String> stream = Files.lines(Paths.get(logPath));
        List<String> reportLines = stream.toList();

        assertNotNull(reportLines);
        assertFalse(reportLines.isEmpty());
    }
}
