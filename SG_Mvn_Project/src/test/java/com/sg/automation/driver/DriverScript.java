package com.sg.automation.driver;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.sg.automation.methods.AppDependentMethods;
import com.sg.automation.methods.AppIndependentMethods;
import com.sg.automation.methods.Datatable;
import com.sg.automation.methods.TaskModuleMethods;
import com.sg.automation.methods.UserModuleMethods;
import com.sg.automation.reports.ReportUtils;

public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static Datatable datatable = null;
	public static UserModuleMethods userMethods = null;
	public static TaskModuleMethods taskMethods = null;
	public static ReportUtils reports = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static String controller = null;
	public static String strScreenShotPath = null;
	public static String moduleName = null;
	public static String testCaseID = null;
	
	@BeforeSuite
	public void loadClasses() {
		appInd = new AppIndependentMethods();
		appDep = new AppDependentMethods();
		datatable = new Datatable();
		userMethods = new UserModuleMethods();
		taskMethods = new TaskModuleMethods();
		reports = new ReportUtils();
		controller = System.getProperty("user.dir")+"\\ExecutionController\\Controller.xlsx";
	}
	
	
	@Test
	public void executeTests() {
		String executionStatus = null;
		int pRows = 0;
		int mRows = 0;
		int tcRows = 0;
		Class cls = null;
		Object obj = null;
		Method meth = null;
		try {
			pRows = datatable.getRowNumber(controller, "Projects");
			for(int i=1; i<=pRows; i++)
			{
				executionStatus = datatable.getCellData(controller, "Projects", "ExecuteProject", i);
				if(executionStatus.equalsIgnoreCase("Yes")) {
					String projectName = datatable.getCellData(controller, "Projects", "ProjectName", i);
					
					mRows = datatable.getRowNumber(controller, projectName);
					for(int j=1; j<=mRows; j++)
					{
						executionStatus = datatable.getCellData(controller, projectName, "ExecuteModule", j);
						if(executionStatus.equalsIgnoreCase("Yes")) {
							moduleName = datatable.getCellData(controller, projectName, "ModuleNames", j);
							
							tcRows = datatable.getRowNumber(controller, moduleName);
							for(int k=1; k<=tcRows; k++)
							{
								executionStatus = datatable.getCellData(controller, moduleName, "ExecuteTest", k);
								if(executionStatus.equalsIgnoreCase("Yes"))
								{
									String className = datatable.getCellData(controller, moduleName, "ClassName", k);
									String scriptName = datatable.getCellData(controller, moduleName, "TestScriptName", k);
									testCaseID = datatable.getCellData(controller, moduleName, "TestCaseID", k);
									cls = Class.forName(className);
									obj = cls.newInstance();
									meth = obj.getClass().getMethod(scriptName);
									if(String.valueOf(meth.invoke(obj)).equalsIgnoreCase("True")){
										datatable.setCellData(controller, moduleName, "Status", k, "PASSED");
									}else {
										datatable.setCellData(controller, moduleName, "Status", k, "FAILED");
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e)
		{
			System.out.println("Exception in executeTests() method. "+e.getMessage());
		}
		finally {
			cls = null;
			obj = null;
			meth = null;
		}
	}
}
