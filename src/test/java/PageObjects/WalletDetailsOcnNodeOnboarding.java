package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import FuntionalUtilities.FuntionalUtilities;

public class WalletDetailsOcnNodeOnboarding extends FuntionalUtilities{
	private WebDriver driver;
	
	public WalletDetailsOcnNodeOnboarding(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	private By WalletAddressField = By.xpath("//label[text()='Wallet Address']/following-sibling::div//input");
	private By DropDownValues = By.xpath("//div[@id='menu-']//li");
	private By AddWalletButton = By.xpath("//button[text()=' + Add Wallet ']");
	private By BackButton= By.xpath("//div[@class='node-onboarding-input button-container']//button[position()=1]");
	private By NextButton = By.xpath("//div[@class='node-onboarding-input button-container']//button[position()=2]");
	private By AddedNodeItems = By.xpath("//div[@class='MuiDataGrid-cellContent']");
	private By NoOfAddedItems = By .xpath("//p[@class='MuiTablePagination-displayedRows css-1chpzqh']");
}
