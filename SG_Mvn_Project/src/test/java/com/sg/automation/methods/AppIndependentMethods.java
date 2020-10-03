package com.sg.automation.methods;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

import com.sg.automation.driver.DriverScript;

public class AppIndependentMethods extends DriverScript{
	/************************************************
	 * Method Name		: getDateTime()
	 * Purpose			: to create the time stamp in required format
	 * Author			:
	 * Reviewer			:
	 * Arguments		: strDateformat
	 * Return type		: String
	 * Date Created		:
	 * **********************************************
	 */
	public String getDateTime(String strDateformat)
	{
		Date dt = null;
		SimpleDateFormat sdf = null;
		try {
			dt = new Date();
			sdf = new SimpleDateFormat(strDateformat);
			return sdf.format(dt);
		}catch(Exception e)
		{
			System.out.println("Exception in getDateTime() method. "+e.getMessage());
			return null;
		}
		finally
		{
			dt = null;
			sdf = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: launchApp()
	 * Purpose			: to launch the required browsers
	 * Author			:
	 * Reviewer			:
	 * Arguments		: browserName
	 * Return type		: WebDriver
	 * Date Created		:
	 * **********************************************
	 */
	public WebDriver launchApp(String browserName)
	{
		WebDriver oDriver = null;
		try {
			test = extent.startTest("launchApp");
			switch(browserName.toLowerCase())
			{
				case "chrome":
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
							+"\\Library\\drivers\\chromedriver.exe");
					oDriver = new ChromeDriver();
					break;
				case "firefox":
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")
							+"\\Library\\drivers\\geckodriver.exe");
					oDriver = new FirefoxDriver();
					break;
				case "ie":
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")
							+"\\Library\\drivers\\IEDriverServer.exe");
					oDriver = new InternetExplorerDriver();
					break;
				default:
					System.out.println("Invalid browser name '"+browserName+"'");
			}
			
			if(oDriver!=null) {
				reports.writeResult(oDriver, "Pass", "The '"+browserName+"' browser was launched successful", test, false);
				oDriver.manage().window().maximize();
				return oDriver;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to launch the '"+browserName+"' browser", test, true);
				return null;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in launchApp() method. "+e.getMessage(), test, true);
			return null;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: closeBrowser()
	 * Purpose			: to close the browser
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver
	 * Return type		: void
	 * Date Created		:
	 * **********************************************
	 */
	public void closeBrowser(WebDriver oDriver)
	{
		try {
			oDriver.close();
			oDriver = null;
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in closeBrowser() method. "+e.getMessage(), test, false);
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: clickObject()
	 * Purpose			: to click the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By
	 * Return type		: booelan
	 * Date Created		:
	 * **********************************************
	 */
	public boolean clickObject(WebDriver oDriver, By objBy)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			
			if(oEles.size() > 0) {
				oEles.get(0).click();
				reports.writeResult(oDriver, "Pass", "The Element '"+String.valueOf(objBy)+"' was licked successful.", test, false);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to click as the Element '"+String.valueOf(objBy)+"' doesnot exist in DOM.", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in clickObject() method. "+e.getMessage(), test, true);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: clickObject()
	 * Purpose			: to click the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By
	 * Return type		: booelan
	 * Date Created		:
	 * **********************************************
	 */
	public boolean clickObject(WebDriver oDriver, String strObjectName)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			
			if(oEles.size() > 0) {
				oEles.get(0).click();
				reports.writeResult(oDriver, "Pass", "The Element '"+strObjectName+"' was licked successful.", test, false);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to click as the Element '"+strObjectName+"' doesnot exist in DOM.", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in clickObject() method. "+e.getMessage(), test, true);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: setObject()
	 * Purpose			: to enter the value in the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By, strData
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean setObject(WebDriver oDriver, By objBy, String strData)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			
			if(oEles.size() > 0) {
				oEles.get(0).sendKeys(strData);
				reports.writeResult(oDriver, "Pass", "The data '"+strData+"' was entered in the Element '"+String.valueOf(objBy)+"' successful", test, false);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to Enter the data '"+strData+"' as the Element '"+String.valueOf(objBy)+"' doesnot exist in DOM.", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in setObject() method. "+e.getMessage(), test, true);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: setObject()
	 * Purpose			: to enter the value in the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By, strData
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean setObject(WebDriver oDriver, String strObjectName, String strData)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			
			if(oEles.size() > 0) {
				oEles.get(0).sendKeys(strData);
				reports.writeResult(oDriver, "Pass", "The data '"+strData+"' was entered in the Element '"+strObjectName+"' successful", test, false);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to Enter the data '"+strData+"' as the Element '"+strObjectName+"' doesnot exist in DOM.", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in setObject() method. "+e.getMessage(), test, true);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: clearAndSetObject()
	 * Purpose			: to enter the value in the element by clearing the existing value
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By, strData
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean clearAndSetObject(WebDriver oDriver, By objBy, String strData)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			
			if(oEles.size() > 0) {
				oEles.get(0).clear();
				oEles.get(0).sendKeys(strData);
				reports.writeResult(oDriver, "Pass", "The data '"+strData+"' was entered in the Element '"+String.valueOf(objBy)+"' successful", test, false);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to Enter the data '"+strData+"' as the Element '"+String.valueOf(objBy)+"' doesnot exist in DOM.", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in clearAndSetObject() method. "+e.getMessage(), test, true);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: clearAndSetObject()
	 * Purpose			: to enter the value in the element by clearing the existing value
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By, strData
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean clearAndSetObject(WebDriver oDriver, String strObjectName, String strData)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			
			if(oEles.size() > 0) {
				oEles.get(0).clear();
				oEles.get(0).sendKeys(strData);
				reports.writeResult(oDriver, "Pass", "The data '"+strData+"' was entered in the Element '"+strObjectName+"' successful", test, false);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to Enter the data '"+strData+"' as the Element '"+strObjectName+"' doesnot exist in DOM.", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in clearAndSetObject() method. "+e.getMessage(), test, true);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: verifyText()
	 * Purpose			: to validate the element text values
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By, strData, objectType, expectedResult
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyText(WebDriver oDriver, By objBy, String objectType, String expected)
	{
		List<WebElement> oEles = null;
		Select oSel = null;
		String actual = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() > 0)
			{
				switch(objectType.toLowerCase())
				{
					case "text":
						actual = oEles.get(0).getText();
						break;
					case "value":
						actual = oEles.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSel = new Select(oEles.get(0));
						actual = oSel.getFirstSelectedOption().getText();
						break;
					default:
						reports.writeResult(oDriver, "Fail", "Invalid object type '"+objectType+"' was specified.", test, false);
				}
				
				if(appInd.compareValues(oDriver, actual, expected)) {
					return true;
				}else {
					return false;
				}
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to validate the text as the Element '"+String.valueOf(objBy)+"' doesnot exist in DOM.", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyText() method. "+e.getMessage(), test, true);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: verifyText()
	 * Purpose			: to validate the element text values
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By, strData, objectType, expectedResult
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyText(WebDriver oDriver, String strObjectName, String objectType, String expected)
	{
		List<WebElement> oEles = null;
		Select oSel = null;
		String actual = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			if(oEles.size() > 0)
			{
				switch(objectType.toLowerCase())
				{
					case "text":
						actual = oEles.get(0).getText();
						break;
					case "value":
						actual = oEles.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSel = new Select(oEles.get(0));
						actual = oSel.getFirstSelectedOption().getText();
						break;
					default:
						reports.writeResult(oDriver, "Fail", "Invalid object type '"+objectType+"' was specified.", test, false);
				}
				
				if(appInd.compareValues(oDriver, actual, expected)) {
					return true;
				}else {
					return false;
				}
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to validate the text as the Element '"+strObjectName+"' doesnot exist in DOM.", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyText() method. "+e.getMessage(), test, true);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: compareValues()
	 * Purpose			: to compare both actual and expected values
	 * Author			:
	 * Reviewer			:
	 * Arguments		: actual, expected
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean compareValues(WebDriver oDriver, String actual, String expected)
	{
		try {
			if(actual.equalsIgnoreCase(expected)) {
				reports.writeResult(oDriver, "Pass", "Both actual '"+actual+"' & expected '"+expected+"' values are matched", test, false);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "Mis-match in both actual '"+actual+"' & expected '"+expected+"' values", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in compareValues() method. "+e.getMessage(), test, true);
			return false;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: verifyElementExist()
	 * Purpose			: to check for the presence of the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyElementExist(WebDriver oDriver, By objBy)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() > 0) {
				reports.writeResult(oDriver, "Pass", "The element '"+String.valueOf(objBy)+"' was present in the DOM", test, false);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "The element '"+String.valueOf(objBy)+"' DOESNOT present in the DOM", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyElementExist() method. "+e.getMessage(), test, true);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: verifyElementExist()
	 * Purpose			: to check for the presence of the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyElementExist(WebDriver oDriver, String strObjectName)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			if(oEles.size() > 0) {
				reports.writeResult(oDriver, "Pass", "The element '"+strObjectName+"' was present in the DOM", test, false);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "The element '"+strObjectName+"' DOESNOT present in the DOM", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyElementExist() method. "+e.getMessage(), test, true);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: verifyElementNotExist()
	 * Purpose			: to check for the invisibility of the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyElementNotExist(WebDriver oDriver, By objBy)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() == 0) {
				reports.writeResult(oDriver, "Pass", "The element '"+String.valueOf(objBy)+"' Not present in the DOM", test, true);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "The element '"+String.valueOf(objBy)+"' present in the DOM", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyElementNotExist() method. "+e.getMessage(), test, true);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: verifyElementNotExist()
	 * Purpose			: to check for the invisibility of the element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyElementNotExist(WebDriver oDriver, String strObjectName)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			if(oEles.size() == 0) {
				reports.writeResult(oDriver, "Pass", "The element '"+strObjectName+"' Not present in the DOM", test, true);
				return true;
			}else {
				reports.writeResult(oDriver, "Fail", "The element '"+strObjectName+"' present in the DOM", test, true);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyElementNotExist() method. "+e.getMessage(), test, true);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	/************************************************
	 * Method Name		: verifyOptionalElement()
	 * Purpose			: to check for the optional element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyOptionalElement(WebDriver oDriver, By objBy)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() == 0) {
				return false;
			}else {
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyOptionalElement() method. "+e.getMessage(), test, false);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: verifyOptionalElement()
	 * Purpose			: to check for the optional element
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, By
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean verifyOptionalElement(WebDriver oDriver, String strObjectName)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(By.xpath(strObjectName));
			if(oEles.size() == 0) {
				return false;
			}else {
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in verifyOptionalElement() method. "+e.getMessage(), test, false);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: getPropData()
	 * Purpose			: to read the data from the prop file based on key name
	 * Author			:
	 * Reviewer			:
	 * Arguments		: keyName
	 * Return type		: String
	 * Date Created		: 
	 * **********************************************
	 */
	public String getPropData(String keyName)
	{
		FileInputStream fin = null;
		Properties prop = null;
		try {
			fin = new FileInputStream(System.getProperty("user.dir")+"\\Configuration\\Config.properties");
			prop = new Properties();
			prop.load(fin);
			
			return prop.getProperty(keyName);
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in getPropData() method. "+e.getMessage(), test, false);
			return null;
		}
		finally
		{
			try {
				
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exception in getPropData() method. "+e.getMessage(), test, false);
				return null;
			}
		}
	}
}
