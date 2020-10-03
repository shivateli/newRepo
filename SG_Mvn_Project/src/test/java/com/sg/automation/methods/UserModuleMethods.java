package com.sg.automation.methods;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.sg.automation.driver.DriverScript;
import com.sg.automation.locators.ObjectLocators;

public class UserModuleMethods extends DriverScript implements ObjectLocators{
	/************************************************
	 * Method Name		: createUser()
	 * Purpose			: to create the new user in actiTime application
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver
	 * Return type		: String
	 * Date Created		:
	 * **********************************************
	 */
	public String createUser(WebDriver oDriver, Map<String, String> objData)
	{
		String strStatus = null;
		try {
			test = extent.startTest("Create User");
			strStatus+= appInd.clickObject(oDriver, obj_Users_Menu);
			Thread.sleep(2000);
			
			strStatus+= appInd.clickObject(oDriver, obj_AddUser_Btn);
			Thread.sleep(2000);
			
			strStatus+= appInd.setObject(oDriver, obj_FirstName_Edit, objData.get("Fname"));
			strStatus+= appInd.setObject(oDriver, obj_LastName_Edit, objData.get("Lname"));
			strStatus+= appInd.setObject(oDriver, obj_Email_Edit, objData.get("Email"));
			strStatus+= appInd.setObject(oDriver, obj_User_UserName_Edit, objData.get("UserName"));
			strStatus+= appInd.setObject(oDriver, obj_User_Password_Edit, objData.get("Password"));
			strStatus+= appInd.setObject(oDriver, obj_RetypePassword_Edit, objData.get("ReTypePwd"));
			
			strStatus+= appInd.clickObject(oDriver, obj_CreateUser_Btn);
			Thread.sleep(2000);

			strStatus+= appInd.verifyElementExist(oDriver, 
					By.xpath("//div[@class='name']/span[text()="
							+"'"+objData.get("Lname")+", "+objData.get("Fname")+"'"+"]"));
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to create the user", test, true);
				return null;
			}else {
				reports.writeResult(oDriver, "Pass", "The user was created successful", test, true);
				return objData.get("Lname")+", "+objData.get("Fname");
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in createUser() method. "+e.getMessage(), test, true);
			return null;
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: deleteUser()
	 * Purpose			: to delete the required user in actiTime application
	 * Author			:
	 * Reviewer			:
	 * Arguments		: WebDriver, userNameToDelete
	 * Return type		: boolean
	 * Date Created		:
	 * **********************************************
	 */
	public boolean deleteUser(WebDriver oDriver, String userName)
	{
		String strStatus = null;
		try {
			test = extent.startTest("Delete User");
			strStatus+= appInd.clickObject(oDriver, 
					By.xpath("//div[@class='name']/span[text()="+"'"+userName+"'"+"]"));
			Thread.sleep(2000);
			strStatus+= appInd.clickObject(oDriver, obj_DeleteUser_Btn);
			Thread.sleep(2000);
			
			oDriver.switchTo().alert().accept();
			Thread.sleep(2000);
			
			strStatus+= appInd.verifyElementNotExist(oDriver, 
					By.xpath("//div[@class='name']/span[text()="+"'"+userName+"'"+"]"));
			
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to delete the user", test, true);
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "The user was deleted successful", test, true);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in deleteUser() method. "+e.getMessage(), test, true);
			return false;
		}
	}
}
