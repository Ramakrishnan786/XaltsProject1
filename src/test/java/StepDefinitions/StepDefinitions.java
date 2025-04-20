package StepDefinitions;

import org.openqa.selenium.WebDriver;

import DriverManager.DriverManager;
import PageObjects.HomePage;
import PageObjects.SignIn;
import PageObjects.SignUp;
import TestDataGenerator.EmailGenerator;
import TestDataGenerator.PasswordGenerator;
import TestDataGeneratorClasses.UserCredentials;
import Utils.ConfigReader;
import Utils.ExcelReaderWriter;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {
	WebDriver driver = DriverManager.getDriver();
	HomePage homePage = new HomePage(driver);
	SignUp signUp = new SignUp(driver);
	SignIn signIn = new SignIn(driver);
	Boolean stepResult;

	@Given("Navigation to the Xalts SignUp Page")
	public void navigation_to_the_xalts_sign_up_page() {
		try {
			String url = ConfigReader.getProperty("HomePageUrl");
			driver.get(url);
			StepContext.setResult(true);
		} catch (Exception e) {

		}
	}

	@Given("Click on Get Started Button")
	public void click_on_get_started_button() {
		stepResult = signUp.clickGetStarted();
		StepContext.setResult(stepResult);
	}

	@Given("Navigate to the SignIn page")
	public void Navigate_to_the_SignIn_page() {
		homePage.clickSignInButton();
	}

	@When("Enter the {string} and {string} for {string}")
	public void enter_the_credentials(String email, String password, String LoginType) {
		// You can now use email and password
		System.out.println("Email: " + email);
		System.out.println("Password: " + password);
		if (email.contains("Random")) {
			email = EmailGenerator.generateRandomEmail();
			password = PasswordGenerator.generatePassword();
			UserCredentials credentials = new UserCredentials(email, password);
			ExcelReaderWriter.writeUserCredentialsToExcel(credentials);

		}
		if (LoginType.contains("Signup")) {
			stepResult = signUp.EnterDetails(email, password);
		} else {
			stepResult = signIn.EnterDetails(email, password);
		}
		StepContext.setResult(stepResult);

	}

	@When("Click on SignUP button")
	public void click_on_sign_up_button() {
		signUp.ClickSignUpButton();
		StepContext.setResult(true);
	}

	@When("Click on Navigate to SignIn page Button")
	public void Click_on_Navigate_to_SignIn_page_Button() {
		signUp.ClicktoNavigatetoSignIn();
		StepContext.setResult(true);
	}

	@When("Click on SignIn button")
	public void click_on_sign_In_button() {
		signIn.clickSignInButton();
	}

	@Then("User should be logged in successfully")
	public void user_should_be_logged_in_successfully() {
		stepResult = signUp.ValidateSignIn();
		StepContext.setResult(stepResult);
	}

	@Then("Email field with red border color for {string}")
	public void Email_field_with_red_border_color_for(String LoginType) {
		if (LoginType.contains("SignUp")) {
			if (!signUp.isSignUpButtonEnabled()) {
				stepResult = signUp.Bordercolorvalidation();
				StepContext.setResult(stepResult);
			}
		} else {
			if (!signIn.isSignInButtonEnabled()) {
				stepResult = signIn.Bordercolorvalidation();
				StepContext.setResult(stepResult);
			}
		}

	}

	@Then("Sign up button sould be disabled")
	public void Sign_up_button_sould_be_disabled() {
		if (signUp.isSignUpButtonEnabled()) {
			signUp.ClickSignUpButton();
			signUp.AcceptAlert();
		}
		StepContext.setResult(true);
	}

	@Then("Sign In button sould be disabled")
	public void Sign_In_button_sould_be_disabled() {
		if (signIn.isSignInButtonEnabled()) {
			signIn.clickSignInButton();
			signIn.AcceptAlert();
		}
		StepContext.setResult(true);
	}

	@Then("Validate the Error Message for the Password field")
	public void Validate_the_Error_Message_for_the_Password_field() {
		signUp.validatePasswordFieldError();
		StepContext.setResult(true);
	}

	@Then("User should not be registered and get Alert pop up")
	public void User_should_not_be_registered_and_get_Alert_pop_up() {
		signUp.validateAlertPopforReRegister();
		StepContext.setResult(true);
	}

	@Then("User should not be logged in and get the Alert Popup")
	public void User_should_not_be_logged_in_and_get_the_Alert_Popup() {
		signIn.validateUnRegisteredLoginMessage();
		;
		StepContext.setResult(true);
	}
	
	@When("Click on SignOut Button")
	public void Click_on_SignOut_Button(){
		signIn.ClickOnSignOutButton();
		StepContext.setResult(true);
	}
	
	@Then("Validate User Signed OUt")
	public void Validate_User_Signed_OUt(){
		signIn.ValidateUserSignOutSuccessfully();
		StepContext.setResult(true);
	}

}
