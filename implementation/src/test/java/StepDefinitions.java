import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import komplexaufgabe.core.components.LaserScanner;
import komplexaufgabe.core.entities.Car;
import komplexaufgabe.core.entities.LicensePlate;

import static org.junit.Assert.assertTrue;

public class StepDefinitions {




    @Given("Car drives by the speedcamera")
    public void car_drives_by_the_speedcamera() {

        Car speedCar = new Car.CarBuilder("manufacteur","registrationId",40,new LicensePlate("licenseID")).build();

    }
    @When("car drives next to speedcamera")
    public void car_drives_next_to_speedcamera(Car car) {
        LaserScanner laserScanner = new LaserScanner();
        laserScanner.detectSpeed(car);
    }
    @Then("the speedcamera captures the speed of the car")
    public void the_speedcamera_captures_the_speed_of_the_car() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }



}
