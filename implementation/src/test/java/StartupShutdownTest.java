import komplexaufgabe.CLI;
import komplexaufgabe.core.SpeedCamera;
import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.interfaces.stoppingtools.TrafficSpikes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class StartupShutdownTest {




    @BeforeEach
    public void setup() {

    }

    @Order(1)
    @Test
    public void parking_space_initialisation() {
        List<Car> carList = TestUtil.get100CarsFromParkingSpace();
        Assertions.assertEquals(100, carList.size());

        carList.forEach(Assertions::assertNotNull);
    }

    @Order(2)
    @Test
    public void starting_speed_camera() {
        SpeedCamera speedCamera=TestUtil.initSpeedCamera();

        if (!speedCamera.isShutDown())
            speedCamera.deactivate();

        speedCamera.activate();

        Assertions.assertFalse(speedCamera.isShutDown());
    }

    @Order(3)
    @Test
    public void shutdown_speed_camera() {
        SpeedCamera speedCamera=TestUtil.initSpeedCamera();

        if (speedCamera.isShutDown())
            speedCamera.activate();

        speedCamera.deactivate();

        Assertions.assertTrue(speedCamera.isShutDown());
    }
}
