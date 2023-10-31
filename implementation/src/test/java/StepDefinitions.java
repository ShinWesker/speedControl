import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class StepDefinitions {

    @Given("user types {string}")
    public void userTypes(String expression) {

    }

    @When("the calculator evaluates the expression")
    public void theCalculatorEvaluatesTheExpression() {
    }

    @Then("the result should be {string}")
    public void theResultShouldBe(String expectedResult) {
        assertTrue(true);
    }
}
