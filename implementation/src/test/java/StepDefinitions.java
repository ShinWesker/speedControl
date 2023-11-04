import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import komplexaufgabe.core.components.LaserScanner;
import komplexaufgabe.core.entities.Car;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {

    Car speedCar;
    Car slowCar;

    int speedSpeedCar;
    int speedSlowCar;


    @Given("\\{car} drives by the speedcamera")
    public void drives_by_the_speedcamera() {
        speedCar = TestUtil.createCar();
        slowCar = TestUtil.createCar();
        speedCar.setSpeed(60);
        slowCar.setSpeed(40);
    }

    @When("\\{car} drives with \\{speed} next to speedcamera")
    public void drives_with_next_to_speedcamera() {
        LaserScanner laserScanner = new LaserScanner();
        speedSpeedCar = laserScanner.detectSpeed(speedCar);
        speedSlowCar = laserScanner.detectSpeed(slowCar);
    }

    @Then("the speedcamera captures the \\{speed} of the car")
    public void the_speedcamera_captures_the_of_the_car() {
        assertTrue(speedSpeedCar > 53);
        assertTrue(speedSlowCar < 53);
    }
}
