import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.components.VehicleRegistrationAuthority;
import komplexaufgabe.core.entities.Police;
import komplexaufgabe.simulate.ParkingSpace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class StartupShutdownTest {

    @Order(1)
    @Test
    public void parking_space_initialisation() {
        ParkingSpace parkingSpace = new ParkingSpace(TestUtil.getCarsFromFile(new VehicleRegistrationAuthority(), new Police()));

        Arrays.stream(parkingSpace.getParkingSlots()).toList().forEach(row -> Arrays.stream(row).toList().forEach(Assertions::assertNotNull));
    }

    @Order(2)
    @Test
    public void starting_speed_camera() {
        SpeedCamera speedCamera = TestUtil.initSpeedCamera();

        if (!speedCamera.isShutDown()) {
            speedCamera.deactivate();
        }

        speedCamera.activate();

        assertFalse(speedCamera.isShutDown());
    }

    @Order(3)
    @Test
    public void shutdown_speed_camera() {
        SpeedCamera speedCamera = TestUtil.initSpeedCamera();

        if (speedCamera.isShutDown()) {
            speedCamera.activate();
        }

        speedCamera.deactivate();

        assertTrue(speedCamera.isShutDown());
    }
}
