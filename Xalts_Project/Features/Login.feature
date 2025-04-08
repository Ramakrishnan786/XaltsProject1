@E2ETesting
Feature: Xalts SignUP
  Registering the new user with valid detials

  @SignUP
  Scenario Outline: Registering the new user with valid detials
    Given Navigation to the Xalts SignUp Page
    And Click on Get Started Button
    When Enter the "<Email>" and "<Password>" for "Signup"
    And Click on SignUP button
    Then User should be logged in successfully
	
	Examples:
	|Email|Password|
	|RandomlyGenerated|RandomlyGenerated|

	@SignIn
	Scenario Outline: Verify that User able to Sign In with Valid credentials
		Given Navigation to the Xalts SignUp Page
		And Navigate to the SignIn page
		And Click on Navigate to SignIn page Button 
		When Enter the "<Email>" and "<Password>" for "Signin"
		And Click on SignIn button
		Then User should be logged in successfully
		
	Examples:
	|Email|Password|
	|testing20101999@gmail.com|Xalts@1234|
	
 @SignUPNegativeCases
  Scenario Outline: Registering the new user with Invalid Email
    Given Navigation to the Xalts SignUp Page
    And Click on Get Started Button
    When Enter the "<Email>" and "<Password>" for "Signup"
   	Then Sign up button sould be disabled
   	And Email field with red border color for "SignUp"
		
	Examples:
			|Email								|Password|
      | email               |Xalts@1234|
      | testexample.com     |Xalts@1234|
      | test@               |Xalts@1234|
      | @example.com        |Xalts@1234|
      | test@example        |Xalts@1234|
      | test@@example.com   |Xalts@1234|
      | test @example.com   |Xalts@1234|
      | test@domain..com    |Xalts@1234|
      | .test@domain.com    |Xalts@1234|
      | test.@domain.com    |Xalts@1234|
      | test@dom#ain.com    |Xalts@1234|

	@SignUPInvalidPW
	Scenario Outline: Registering the new User with Invalid Password
		Given Navigation to the Xalts SignUp Page
    And Click on Get Started Button
    When Enter the "<Email>" and "<Password>" for "Signup"
    Then Validate the Error Message for the Password field
    Examples:
  | Email                | Password              |
  | validuser@example.com | Ab1@                 |
  | validuser@example.com | ABC@1234             |
  | validuser@example.com | abc@1234             |
  | validuser@example.com | Abc@defg             |
  | validuser@example.com | Abc12345             |
  | validuser@example.com | Abcdefgh             |
  | validuser@example.com | 1234@#$%             |
  | validuser@example.com | Abc @1234            |
 	
 	@SignUpReRegister
 	Scenario Outline: Verify whether the user able register with the already registered User
 		Given Navigation to the Xalts SignUp Page
    And Click on Get Started Button
    When Enter the "<Email>" and "<Password>" for "Signup"
    And Click on SignUP button
    Then User should not be registered and get Alert pop up 
     Examples:
  	| Email                | Password    |
  	|testing20101999@gmail.com|Xalts@1234|
	
	@UnRegisteredSignin
	Scenario Outline: Verify that User not allowed to signin with unregsitered credentials
		Given Navigation to the Xalts SignUp Page
		And Navigate to the SignIn page
		And Click on Navigate to SignIn page Button 
		When Enter the "<Email>" and "<Password>" for "Signin"
		And Click on SignIn button
		Then User should not be logged in and get the Alert Popup
		 Examples:
  	| Email                | Password    |
  	|abc@gmail.com				 |Xalts@1234|
  	|testing20101999@gmail.com|Xalts@1235|
  	|testing20101998@gmail.com|Xalts@1234|
  	
  
   @SignInNegativeCases
  Scenario Outline: SignIn with Invalid Email
    Given Navigation to the Xalts SignUp Page
    And Click on Get Started Button
    And Click on Navigate to SignIn page Button 
    When Enter the "<Email>" and "<Password>" for "SignIn"
   	Then Sign In button sould be disabled
   	And Email field with red border color for "SignIn"
		
	Examples:
			|Email								|Password|
      | email               |Xalts@1234|
      | testexample.com     |Xalts@1234|
      | test@               |Xalts@1234|
      | @example.com        |Xalts@1234|
      | test@example        |Xalts@1234|
      | test@@example.com   |Xalts@1234|
      | test @example.com   |Xalts@1234|
      | test@domain..com    |Xalts@1234|
      | .test@domain.com    |Xalts@1234|
      | test.@domain.com    |Xalts@1234|
      | test@dom#ain.com    |Xalts@1234|
      
      
  @SignInNegativeCasesPW
  Scenario Outline: SignIn with Invalid Password
    Given Navigation to the Xalts SignUp Page
    And Click on Get Started Button
    And Click on Navigate to SignIn page Button 
    When Enter the "<Email>" and "<Password>" for "SignIn"
   	Then Validate the Error Message for the Password field
    Examples:
  | Email                | Password              |
  | validuser@example.com | Ab1@                 |
  | validuser@example.com | ABC@1234             |
  | validuser@example.com | abc@1234             |
  | validuser@example.com | Abc@defg             |
  | validuser@example.com | Abc12345             |
  | validuser@example.com | Abcdefgh             |
  | validuser@example.com | 1234@#$%             |
  | validuser@example.com | Abc @1234            |
	
		@SignOut
	Scenario Outline: Verify that User able to Signout after successful login
		Given Navigation to the Xalts SignUp Page
		And Navigate to the SignIn page
		And Click on Navigate to SignIn page Button 
		When Enter the "<Email>" and "<Password>" for "Signin"
		And Click on SignIn button
		Then User should be logged in successfully
		When Click on SignOut Button
		Then Validate User Signed OUt	
		Examples:
		|Email|Password|
		|testing20101999@gmail.com|Xalts@1234|