package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import FuntionalUtilities.FuntionalUtilities;

public class OnboardOcnNode extends FuntionalUtilities {
	private WebDriver driver;

	public OnboardOcnNode(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	private By NodeIdField = By.xpath("//label[text()='Node ID']/following-sibling::div//input");
	private By PublicIpField = By.xpath("//label[text()='Public IP']/following-sibling::div//input");
	private By DropDownValues = By.xpath("//div[@id='menu-']//li");
	private By AddNodeButton = By.xpath("//button[text()='+ Add Node ']");
	private By AddedNodeItems = By.xpath("//div[@class='MuiDataGrid-cellContent']");
	private By NoOfAddedItems = By .xpath("//p[@class='MuiTablePagination-displayedRows css-1chpzqh']");
	
}
