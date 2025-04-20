package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "Features",
        glue = { "StepDefinitions", "Hooks" },
        tags = "@SignUP",
        plugin = {
            "pretty",
            "html:target/cucumber-reports.html",
            "json:target/cucumber.json"
        },
        monochrome = true,
        dryRun = false
)
public class ParallelTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true) // 🎯 Enable parallel execution
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
