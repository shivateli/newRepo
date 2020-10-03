package com.sg.automation.reports;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sg.automation.driver.DriverScript;


public class ReportUtils extends DriverScript{

	/*****************************************
	 * Method Name	: startReport
	 * Purpose		:
	 * Arguments	:
	 * Return Type	:
	 * Author		:
	 ****************************************/
	public ExtentReports startReport(String fileName, String testCaseID, String buildNum)
	{
		String strPath = null;
		File resLocation = null;
		File resScreenshot = null;
		try {
			strPath = System.getProperty("user.dir")+"\\Results\\"+buildNum;
			
			if(!new File(strPath).exists()) {
				new File(strPath).mkdir();
			}
			
			resLocation = new File(strPath + "\\" +testCaseID);
			if(!resLocation.exists()) {
				resLocation.mkdir();
			}
			
			strScreenShotPath = resLocation +"\\screenshot";
			resScreenshot = new File(strScreenShotPath);
			if(!resScreenshot.exists()) {
				resScreenshot.mkdir();
			}
			
			extent = new ExtentReports(resLocation +"\\"+ fileName +appInd.getDateTime("ddMMyyyy_hhmmss")+".html", true);
			extent.addSystemInfo("Host Name", System.getProperty("os.name"));
			extent.addSystemInfo("User Name", System.getProperty("user.name"));
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
			return extent;
		}catch(Exception e)
		{
			System.out.println("Exception in startTest() method. "+e.getMessage());
			return null;
		}
		finally {
			strPath = null;
			resLocation = null;
			resScreenshot = null;
		}
	}
	
	
	
	/*****************************************
	 * Method Name	: endTest
	 * Purpose		:
	 * Arguments	:
	 * Return Type	:
	 * Author		:
	 ****************************************/
	public void endTest(ExtentTest test) {
		try {
			extent.endTest(test);
			extent.flush();
		}catch(Exception e)
		{
			System.out.println("Exception in endTest() method. "+e.getMessage());
		}
	}
	
	
	
	
	/*****************************************
	 * Method Name	: endTest
	 * Purpose		:
	 * Arguments	:
	 * Return Type	:
	 * Author		:
	 ****************************************/
	public String captureScreenshot(WebDriver oDriver)
	{
		File srcFile = null;
		String destPath = null;
		try {
			TakesScreenshot screenShot = (TakesScreenshot) oDriver;
			srcFile = screenShot.getScreenshotAs(OutputType.FILE);
			destPath = strScreenShotPath +"\\ScreenShot_" + appInd.getDateTime("ddMMYYYY_hhmmss")+".png";
			FileUtils.copyFile(srcFile, new File(destPath));
			return destPath;
		}catch(Exception e)
		{
			System.out.println("Exception in captureScreenshot() method. "+e.getMessage());
			return destPath;
		}
		finally {
			srcFile = null;
		}
	}
	
	
	
	/*****************************************
	 * Method Name	: writeResult
	 * Purpose		:
	 * Arguments	:
	 * Return Type	:
	 * Author		:
	 ****************************************/
	public void writeResult(WebDriver oDriver, String status, String description, 
									ExtentTest test, boolean screenReqd)
	{
		try {
			if(screenReqd) {
				switch(status.toLowerCase()) {
					case "pass":
						test.log(LogStatus.PASS, description +":"+ test.addScreenCapture(
								reports.captureScreenshot(oDriver)));
						break;
					case "fail":
						test.log(LogStatus.FAIL, description +":"+ test.addScreenCapture(
								reports.captureScreenshot(oDriver)));
						break;
					case "warning":
						test.log(LogStatus.WARNING, description +":"+ test.addScreenCapture(
								reports.captureScreenshot(oDriver)));
						break;
					case "info":
						test.log(LogStatus.INFO, description +":"+ test.addScreenCapture(
								reports.captureScreenshot(oDriver)));
						break;
					case "exception":
						test.log(LogStatus.FATAL, description +":"+ test.addScreenCapture(
								reports.captureScreenshot(oDriver)));
						break;
					default:
						System.out.println("Invalid result status '"+status+"' was specified. please enter the valid status for the results to write");
				}
			}else {
				switch(status.toLowerCase()) {
				case "pass":
					test.log(LogStatus.PASS, description);
					break;
				case "fail":
					test.log(LogStatus.FAIL, description);
					break;
				case "warning":
					test.log(LogStatus.WARNING, description);
					break;
				case "info":
					test.log(LogStatus.INFO, description);
					break;
				case "exception":
					test.log(LogStatus.FATAL, description);
					break;
				default:
					System.out.println("Invalid result status '"+status+"' was specified. please enter the valid status for the results to write");
			}
			}
		}catch(Exception e)
		{
			System.out.println("Exception in writeResult() method. "+e.getMessage());
		}
	}
}
