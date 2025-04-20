package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import TestDataGeneratorClasses.UserCredentials;

public class ExcelReaderWriter {
	private static final String EXCEL_FILE_PATH = "src/test/resources/ExcelFiles/UserCredentials.xlsx"; // Path to the Excel file

	// Method to write user credentials to the Excel file
	public static void writeUserCredentialsToExcel(UserCredentials user) {
		try (FileInputStream fis = new FileInputStream(new File(EXCEL_FILE_PATH))) {
			// Load the existing workbook
			Workbook workbook = new XSSFWorkbook(fis);

			// Get the sheet where you want to add the data (assuming sheet name is
			// "UserCredentials")
			Sheet sheet = workbook.getSheet("UserCredentials");

			// Find the next available row (i.e., the first empty row)
			int rowIndex = sheet.getPhysicalNumberOfRows(); // Get the number of rows already present

			// Create a new row to store the data
			Row row = sheet.createRow(rowIndex);
			row.createCell(0).setCellValue(user.getEmail());
			row.createCell(1).setCellValue(user.getPassword());

			// Write the changes to the file
			try (FileOutputStream fos = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
				workbook.write(fos);
			}

			System.out.println("User credentials written to the Excel file successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to read user credentials from the Excel file
	public static List<UserCredentials> readUserCredentialsFromExcel() {
		List<UserCredentials> credentialsList = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(new File(EXCEL_FILE_PATH))) {
			// Load the existing workbook
			Workbook workbook = new XSSFWorkbook(fis);

			// Get the sheet where the data is stored (assuming sheet name is
			// "UserCredentials")
			Sheet sheet = workbook.getSheet("UserCredentials");

			// Iterate through the rows (starting from the second row to skip header)
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);

				// Read email and password from each row
				String email = row.getCell(0).getStringCellValue();
				String password = row.getCell(1).getStringCellValue();

				// Create UserCredentials object and add it to the list
				UserCredentials user = new UserCredentials(email, password);
				credentialsList.add(user);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return credentialsList;
	}

}
