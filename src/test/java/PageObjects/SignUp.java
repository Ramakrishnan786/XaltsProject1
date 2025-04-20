package PageObjects;


import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import FuntionalUtilities.FuntionalUtilities;
import Utils.JsonReader;
import junit.framework.Assert;

public class SignUp extends FuntionalUtilities {
	private WebDriver driver;

	// Safest if "Get Started" is unique and static
	private By getStartedBtn = By.xpath("//button[normalize-space()='Get Started']");
	private By emailField = By.xpath("//label[text()='E-Mail']/following-sibling::div/input");
	private By passwordField = By.xpath("//label[text()='Password']/following-sibling::div/input");
	private By confirmPasswordField = By.xpath("//label[text()='Confirm Password']/following-sibling::div/input");
	private By SignUpButton = By.xpath("//button[text()='Sign Up']");
	private By SignOutButton = By.xpath("//button[text()='Sign Out']");
	private By NavigateToSignIn= By.xpath("//button[text()='Already have an account? Click here to sign in.']");
	private By emailBorderColor = By.xpath("//label[text()='E-Mail']/following-sibling::div");
	private By errorMessage = By.xpath("//p[@id='outlined-basic-helper-text']");
	
	public SignUp(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public Boolean clickGetStarted() {
		safeClick(getStartedBtn);
		return verifyPageLoaded("/signin", emailField);
	}

	public Boolean EnterDetails(String email, String password) {
		boolean isEmailEntered = sendKeysAndValidate(emailField, email);
	    boolean isPasswordEntered = sendKeysAndValidate(passwordField, password);
	    boolean isConfirmPasswordEntered = sendKeysAndValidate(confirmPasswordField,password);

	   
	    if (isEmailEntered && isPasswordEntered && isConfirmPasswordEntered) {
	        System.out.println("✅ Email and Password entered successfully.");
	        return true;
	    } else {
	        System.out.println("❌ Failed to enter credentials. Email: " + isEmailEntered + ", Password: " + isPasswordEntered);
	        return false;
	    }
	}
	
	public void ClickSignUpButton() {
		 safeClick(SignUpButton);
//		 return verifyPageLoaded(getStartedBtn);
	}
	
	public Boolean ValidateSignIn() {
		return verifyPageLoaded(SignOutButton);
	}
	
	public void ClicktoNavigatetoSignIn() {
		safeClick(NavigateToSignIn);
	}
	
	public Boolean Bordercolorvalidation() {
		WebElement EmailField = getElement(emailBorderColor);
		String classes = EmailField.getAttribute("class");
		Assert.assertTrue(classes.contains("Mui-error"));
		return true;
	}
	
	public Boolean isSignUpButtonEnabled() {
		return getElement(SignUpButton).isEnabled();
	}
	
	public void AcceptAlert() {
		waitForAlertAndAccept();
	}
	
	public void validatePasswordFieldError() {
		WebElement PasswordErrorMess = getElement(errorMessage);
		Map<String, String> ErrorMessagges = JsonReader.getJsonDataFromJsonFile("SignUp","ErrorMessages");
		Assert.assertEquals(ErrorMessagges.get("PasswordErrorMessage").trim(),PasswordErrorMess.getText().trim() );
		
	}
	
	public void validateAlertPopforReRegister() {
		
		String alertText = waitForAlertAndAccept();
		Map<String, String> ErrorMessagges = JsonReader.getJsonDataFromJsonFile("SignUp","ErrorMessages");
		Assert.assertEquals(ErrorMessagges.get("ReregisterAlertMessaage").trim(),alertText.trim() );
		
	}
	
	
}
