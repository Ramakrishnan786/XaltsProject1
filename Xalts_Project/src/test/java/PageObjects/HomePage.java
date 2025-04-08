package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import FuntionalUtilities.FuntionalUtilities;

public class HomePage extends FuntionalUtilities{
	WebDriver driver;	
	
	public HomePage(WebDriver driver) {
		super(driver);
		this.driver= driver;
	}
	
	private By signInButton = By.xpath("//button[text()='Sign In']");
	
	public void clickSignInButton() {
		 safeClick(signInButton);
	}
}
