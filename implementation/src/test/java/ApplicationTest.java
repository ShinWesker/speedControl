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

/**<p>
 * Zentrale Anwendungsfälle:
 * - Startup
 * - Import
 * - Execute Simulation     !!
 * - Report
 * - Export
 * - Shutdown
 * - Exit
 * </p><p>
 * <b>Authentifizierung des Officers:</b> Es sollte getestet werden, ob die Kombination aus ID und Passwort des Officers korrekt überprüft wird und ob die richtige Fehlermeldung ausgegeben wird, wenn die Authentifizierung fehlschlägt.
 * </p>
 * <p>
 * <b>Import der Datendatei:</b> Es sollte getestet werden, ob die Datendatei erfolgreich importiert wird, wenn die Authentifizierung erfolgreich ist, und ob die richtige Rückmeldung erfolgt, wenn der Import abgebrochen wird.
 * </p><p>
 * <b>Simulation der Geschwindigkeitsüberschreitung:</b> Es sollte getestet werden, ob die Simulation korrekt durchgeführt wird und ob die Geschwindigkeiten der Fahrzeuge richtig erfasst und überprüft werden.
 * </p><p>
 * <b>Auslösung der SpeedCamera:</b> Es sollte getestet werden, ob die SpeedCamera korrekt ausgelöst wird, wenn eine Geschwindigkeitsüberschreitung festgestellt wird, und ob die entsprechenden Informationen (Bild, Zeitstempel, Kennzeichen usw.) korrekt erfasst werden.
 * </p><p>
 * <b>Extraktion von Gesicht und Kennzeichen:</b> Es sollte getestet werden, ob die AIEngine korrekt arbeitet und ob das Gesicht und das Kennzeichen aus dem Bild extrahiert werden können.
 * </p><p>
 * <b>Kommunikation mit der Polizei und der VehicleRegistrationAuthority:</b> Es sollte getestet werden, ob die Kommunikation mit der Polizei und der VehicleRegistrationAuthority korrekt funktioniert und ob die richtigen Informationen abgerufen werden können.
 * </p><p>
 * <b>Erstellung von Records:</b> Es sollte getestet werden, ob die Records korrekt erstellt werden, einschließlich der Berechnung der Strafe basierend auf der gemessenen Geschwindigkeit und der importierten Tabelle der Bußgelder.
 * </p><p>
 * <b>Belastung des Wallets:</b> Es sollte getestet werden, ob das Wallet des Owners korrekt belastet wird, wenn eine Strafe verhängt wird.
 * </p><p>
 * <b>Stoppen und Festnahme des Owners:</b> Es sollte getestet werden, ob das Fahrzeug eines zur Fahndung ausgeschriebenen Owners korrekt gestoppt wird und ob die Festnahme des Owners und die Beschlagnahmung des Fahrzeugs korrekt durchgeführt werden.
 * </p><p>
 * <b>Erstellung des Reports:</b> Es sollte getestet werden, ob der Report korrekt erstellt wird, einschließlich der Gruppierung der Records nach Hersteller, der Sortierung nach Zeitstempel und gemessener Geschwindigkeit sowie der Berechnung von minimalen, maximalen und durchschnittlichen Geschwindigkeitsüberschreitungen.
 * </p><p>
 * <b>Export der Records:</b> Es sollte getestet werden, ob die Records korrekt in eine CSV-Datei exportiert werden.
 * </p><p>
 * <b>Herunterfahren der SpeedCamera:</b> Es sollte getestet werden, ob die SpeedCamera korrekt heruntergefahren wird.
 * </p>
 */

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber-report.html"}, features = {"src/test/resources"})
public class ApplicationTest {

    @BeforeEach
    public void setup() {

    }

    @Order(1)
    @ParameterizedTest(name = "{index} => MD5-Hash {0} to {1}")
    @CsvSource({"Hallo, d1bf93299de1b68e6d382c893bf1215f"})
    public void md5Test(String text, String hashedText) {
        MD5 md5 = new MD5();

        assertEquals(hashedText, md5.encrypt(text));
    }

    @Order(2)
    @ParameterizedTest(name = "{index} => SHA256-Hash {0} to {1}")
    @CsvSource({"Hallo, 533ea3ddaaede1572aa73579252794bcebb318b0de102b062572cb5f953871ca"})
    public void sha256Test(String text, String hashedText) {
        SHA256 sha256 = new SHA256();

        assertEquals(hashedText, sha256.encrypt(text));
    }
}