package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import FuntionalUtilities.FuntionalUtilities;

public class PostLoginHomePage extends FuntionalUtilities {
	private WebDriver driver;
	
	public PostLoginHomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	private By OcnNodeOnboard = By.xpath("//h2[text()='Onboard OCN Node']");
	private By LaunchOcnChildNetwork = By.xpath("//h2[text()='Launch OCN Child Network']");
	
}
