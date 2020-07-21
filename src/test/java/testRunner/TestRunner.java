package testRunner;

import io.cucumber.java.After;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import tests.BaseTest;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features"},
    glue = {"stepdefs", "stepdefs/"})
public class TestRunner {
}
