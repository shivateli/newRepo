package com.sg.automation.methods;

import org.openqa.selenium.WebDriver;
import com.sg.automation.driver.DriverScript;
import com.sg.automation.locators.ObjectLocators;

public class AppDependentMethods extends DriverScript implements ObjectLocators{
	/************************************************
	 * Method Name		: navigateURL()
	 * Purpose			: to navigate the URL
	 * Author			:
	 * Reviewer			:
	 * Arguments		: oDriverm strURL
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean navigateURL(WebDriver oDriver, String strURL)
	{
		try {
			oDriver.navigate().to(strURL);
			Thread.sleep(2000);
			
			if(appInd.compareValues(oDriver, oDriver.getTitle(), "actiTIME - Login"))
			{
				reports.writeResult(oDriver, "Pass", "URL Loaded", test, true);
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in navigateURL() method. "+e.getMessage(), test, true);
			return false;
		}
	}
	
	
	
	/************************************************
	 * Method Name		: loginToApp()
	 * Purpose			: to login to actiTime application
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, UserName, Password
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean loginToApp(WebDriver oDriver, String userName, String password)
	{
		String strStatus = null;
		try {
			test = extent.startTest("loginToApp");
			strStatus+= appInd.setObject(oDriver, obj_User_UserName_Edit, userName);
			strStatus+= appInd.setObject(oDriver, obj_Password_Edit, password);
			strStatus+= appInd.clickObject(oDriver, obj_Login_Btn);
			Thread.sleep(4000);
			
			//Handle the short cut window
			if(appInd.verifyOptionalElement(oDriver, obj_ExploreActiTime_Btn))
			{
				Thread.sleep(2000);
				strStatus+= appInd.clickObject(oDriver, obj_ExploreActiTime_Btn);
			}
			
			
			//Handle the short cut window
			if(appInd.verifyOptionalElement(oDriver, obj_Shortcut_Window))
			{
				strStatus+= appInd.clickObject(oDriver, obj_Shortcut_Close_Btn);
			}
			
			strStatus+= appInd.verifyText(oDriver, obj_TimeTrack_Page, "Text", "Enter Time-Track");
					
					
			if(strStatus.contains("false")){
				reports.writeResult(oDriver, "Fail", "Failed to login to actitime", test, true);
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "Login is successful", test, true);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Excecption", "Exception in loginToApp() method. "+e.getMessage(), test, true);
			return false;
		}
	}
	
	
	/************************************************
	 * Method Name		: logoutFromApp()()
	 * Purpose			: to logout from the actiTime application
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean logoutFromApp(WebDriver oDriver)
	{
		String strStatus = null;
		try {
			test = extent.startTest("logoutFromApp");
			strStatus+= appInd.clickObject(oDriver, obj_Logout_Btn);
			Thread.sleep(2000);
			
			strStatus+= appInd.verifyText(oDriver, obj_LoginHeader, "Text", "Please identify yourself");
			
			if(strStatus.contains("false"))
			{
				reports.writeResult(oDriver, "Fail", "FAiled to logout form the actiTime application", test, true);
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "Logout from the actiTime was successful", test, true);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in logoutFromApp() method. "+e.getMessage(), test, true);
			return false;
		}
	}
}
