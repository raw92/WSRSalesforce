package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


//DE ESTA FORMA ES PARA USAR TESTNG
@CucumberOptions(
		features = "src/test/java/features",
		glue = "stepDefinitions")


//Extends AbstractTestNGCucumberTests needed
public class TestRunner extends AbstractTestNGCucumberTests{

}
