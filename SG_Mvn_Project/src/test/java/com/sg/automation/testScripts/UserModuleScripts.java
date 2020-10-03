package com.sg.automation.testScripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.sg.automation.driver.DriverScript;

public class UserModuleScripts extends DriverScript{
	/****************************************************
	 * Script Name	: TS_LoginLogout()
	 * Test Case ID	: SRC_101
	 * 
	 * 
	 * 
	 * 
	 * *************************************************
	 */
	@Test
	public boolean TS_LoginLogout()
	{
		WebDriver oBrowser = null;
		String strStatus = null;
		Map<String, String> objData = null;
		try {
			extent = reports.startReport("LoginLogout", testCaseID, appInd.getPropData("BuildNum"));
			
			objData = datatable.getExcelData("userModule", "TS_Login");
			oBrowser = appInd.launchApp(objData.get("BrowserName"));
			strStatus+= appDep.navigateURL(oBrowser, appInd.getPropData("URL"));
			strStatus+= appDep.loginToApp(oBrowser, objData.get("UN"), objData.get("PWD"));
			strStatus+= appDep.logoutFromApp(oBrowser);
			appInd.closeBrowser(oBrowser);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oBrowser, "Fail", "The script TS_LoginLogout() failed.", test, false);
				return false;
			}else {
				reports.writeResult(oBrowser, "Pass", "The script TS_LoginLogout() passed.", test, false);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in TS_LoginLogout() test script. "+e.getMessage(), test, false);
			return false;
		}
		finally {
			reports.endTest(test);
			oBrowser = null;
		}
	}
	
	
	
	/****************************************************
	 * Script Name	: TS_LoginLogout()
	 * Test Case ID	: SRC_102
	 * 
	 * 
	 * 
	 * 
	 * *************************************************
	 */
	@Test
	public boolean TS_CreateAndDeleteUser()
	{
		WebDriver oBrowser = null;
		String strStatus = null;
		Map<String, String> objData = null;
		try {
			extent = reports.startReport("createDelete", testCaseID, appInd.getPropData("BuildNum"));
			objData = datatable.getExcelData("userModule", "TS_CreateDelete");
			oBrowser = appInd.launchApp(objData.get("BrowserName"));
			strStatus+= appDep.navigateURL(oBrowser, appInd.getPropData("URL"));
			strStatus+= appDep.loginToApp(oBrowser, objData.get("UN"), objData.get("PWD"));
			String userName = userMethods.createUser(oBrowser, objData);
			strStatus+= userMethods.deleteUser(oBrowser, userName);
			strStatus+= appDep.logoutFromApp(oBrowser);
			appInd.closeBrowser(oBrowser);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oBrowser, "Fail", "The script TS_CreateAndDeleteUser() failed.", test, false);
				return false;
			}else {
				reports.writeResult(oBrowser, "Pass", "The script TS_CreateAndDeleteUser() passed.", test, false);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in TS_CreateAndDeleteUser() test script. "+e.getMessage(), test, false);
			return false;
		}
		finally {
			reports.endTest(test);
			oBrowser = null;
		}
	}
	
	
	
	/****************************************************
	 * Script Name	: TS_LoginWithNewUser_DeleteUser()
	 * Test Case ID	: SRC_103
	 * 
	 * 
	 * 
	 * 
	 * *************************************************
	 */
	@Test
	public boolean TS_LoginWithNewUser_DeleteUser()
	{
		WebDriver oBrowser1 = null;
		WebDriver oBrowser2 = null;
		String strStatus = null;
		Map<String, String> objData = null;
		try {
			extent = reports.startReport("LoginWithNewUser", testCaseID, appInd.getPropData("BuildNum"));
			objData = datatable.getExcelData("userModule", "TS_NewUserLogin1");
			oBrowser1 = appInd.launchApp(objData.get("BrowserName"));
			strStatus+= appDep.navigateURL(oBrowser1, appInd.getPropData("URL"));
			strStatus+= appDep.loginToApp(oBrowser1, objData.get("UN"), objData.get("PWD"));
			String userName = userMethods.createUser(oBrowser1,objData);
			
			//Login with newly created user
			objData = datatable.getExcelData("userModule", "TS_NewUserLogin2");
			oBrowser2 = appInd.launchApp(objData.get("BrowserName")); 
			strStatus+= appDep.navigateURL(oBrowser2, appInd.getPropData("URL"));
			boolean blnRes = appDep.loginToApp(oBrowser2, objData.get("UN"), objData.get("PWD"));
			if(blnRes) {
				reports.writeResult(oBrowser2, "Pass", "Login with new user was successful", test, false);
				oBrowser2.close();
				strStatus+= true;
			}else {
				reports.writeResult(oBrowser2, "Fail", "Failed to Login with newly created user", test, true);
				oBrowser2.close();
				oBrowser1.close();
				return false;
			}
			
			strStatus+= userMethods.deleteUser(oBrowser1, userName);
			strStatus+= appDep.logoutFromApp(oBrowser1);
			appInd.closeBrowser(oBrowser1);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oBrowser1, "Fail", "The script TS_LoginWithNewUser_DeleteUser() failed.", test, false);
				return false;
			}else {
				reports.writeResult(oBrowser1, "Pass", "The script TS_LoginWithNewUser_DeleteUser() passed.", test, false);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser1, "Exception", "Exception in TS_LoginWithNewUser_DeleteUser() test script. "+e.getMessage(), test, false);
			return false;
		}
		finally {
			reports.endTest(test);
			oBrowser1 = null;
			oBrowser2 = null;
		}
	}
}
