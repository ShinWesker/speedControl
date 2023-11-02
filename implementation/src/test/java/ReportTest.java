import komplexaufgabe.core.SpeedCamera;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {

    @Test
    //TODO: fix
    public void create_report() throws IOException, URISyntaxException {
        SpeedCamera speedCamera = TestUtil.initSpeedCamera();

        Path logPath = Paths.get(getClass().getClassLoader().getResource("export/report.log").toURI());
        Stream<String> stream = Files.lines(logPath);
        List<String> reportLines = stream.toList();
        long reportLogLengthBefore = reportLines.size();

        TestUtil.runSetPolicySimulation(speedCamera);

        speedCamera.getCentralUnit().createReportLog();

        stream = Files.lines(logPath);
        reportLines = stream.toList();
        long reportLogLengthAfter = reportLines.size();

        assertNotNull(reportLines);
        assertFalse(reportLines.isEmpty());
        assertNotEquals(reportLogLengthBefore, reportLogLengthAfter);
    }
}
