package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "Features", // Path to .feature files
		glue = { "StepDefinitions", "Hooks" },
		tags ="@E2ETesting",// Packages where StepDefs and Hooks are kept
		plugin = { "pretty", "html:target/cucumber-reports.html","StepDefinitions.StepTracker"}, monochrome = true, dryRun = false)
public class TestRunner{
	
}
