package TestDataGenerator;

public class EmailGenerator {
	public static String generateRandomEmail() {
		long currentMillis = System.currentTimeMillis(); // Get current time in milliseconds
		return "user" + currentMillis + "@example.com"; // Append the timestamp to the email
	}
}
