import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import komplexaufgabe.core.components.LaserScanner;
import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.entities.LicensePlate;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.assertTrue;

public class StepDefinitions {

Car speedCar;
Car slowCar;

int speedSpeedCar;
int speedSlowCar;




    @Given("\\{Car} drives by the speedcamera")
    public void drives_by_the_speedcamera() {
        speedCar = new Car.CarBuilder("manufacteur","registrationId",80,new LicensePlate("licenseID")).build();
        slowCar = new Car.CarBuilder("manufacteur","registrationId",48,new LicensePlate("licenseID")).build();

    }
    @When("\\{car} drives with \\{speed} next to speedcamera")
    public void drives_with_next_to_speedcamera() {
        LaserScanner laserScanner = new LaserScanner();
        speedSpeedCar = laserScanner.detectSpeed(speedCar);
                speedSlowCar = laserScanner.detectSpeed(slowCar);

    }
    @Then("the speedcamera captures the \\{speed} of the car")
    public void the_speedcamera_captures_the_of_the_car() {
        Assertions.assertTrue(speedSpeedCar>53);
        Assertions.assertTrue(speedSlowCar<53);

    }




}
