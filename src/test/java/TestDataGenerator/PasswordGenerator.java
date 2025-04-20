package TestDataGenerator;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@$*_";

    private static final String ALL_CHARACTERS = LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARACTERS;

    // Method to generate a strong password with random length between 8 to 14 characters
    public static String generatePassword() {
        SecureRandom random = new SecureRandom();

        // Generate a random length between 8 and 14
        int length = random.nextInt(7) + 8; // Random number between 8 and 14
        StringBuilder password = new StringBuilder(length);

        // Ensure at least one lowercase, one special character, and one number
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // Fill the rest of the password with random characters from all available sets
        for (int i = 3; i < length; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        // Shuffle the characters to ensure randomness
        StringBuilder shuffledPassword = new StringBuilder(password.toString());
        for (int i = 0; i < shuffledPassword.length(); i++) {
            int j = random.nextInt(shuffledPassword.length());
            char temp = shuffledPassword.charAt(i);
            shuffledPassword.setCharAt(i, shuffledPassword.charAt(j));
            shuffledPassword.setCharAt(j, temp);
        }

        return shuffledPassword.toString();
    }

    public static void main(String[] args) {
        // Generate a password with a random length between 8 and 14
        String password = generatePassword();
        System.out.println("Generated Password: " + password);
    }
}
