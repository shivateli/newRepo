package com.sg.automation.locators;

public interface ObjectLocators {
	String obj_UserName_Edit = "//input[@id='username']";
	String obj_Password_Edit = "//input[@name='pwd']";
	String obj_Login_Btn = "//a[@id='loginButton']";
	String obj_Logout_Btn = "//a[@id='logoutLink']";
	String obj_ExploreActiTime_Btn = "//div[@class='startExploringLink']/span[text()='Start exploring actiTIME']";
	String obj_Shortcut_Window = "//div[@style='display: block;']";
	String obj_Shortcut_Close_Btn = "//div[@id='gettingStartedShortcutsMenuCloseId']";
	String obj_TimeTrack_Page = "//td[@class='pagetitle']";
	String obj_Users_Menu = "//div[text()='USERS']";
	String obj_AddUser_Btn = "//div[text()='Add User']";
	String obj_FirstName_Edit = "//input[@name='firstName']";
	String obj_LastName_Edit = "//input[@name='lastName']";
	String obj_Email_Edit = "//input[@name='email']";
	String obj_User_UserName_Edit = "//input[@name='username']";
	String obj_User_Password_Edit = "//input[@name='password']";
	String obj_RetypePassword_Edit = "//input[@name='passwordCopy']";
	String obj_CreateUser_Btn = "//span[text()='Create User']";
	String obj_DeleteUser_Btn = "//button[contains(text(), 'Delete User')]";
	String obj_LoginHeader = "//td[@id='headerContainer']";
}
