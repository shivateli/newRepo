package com.sg.automation.methods;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.sg.automation.driver.DriverScript;

public class Datatable extends DriverScript{
	/************************************************
	 * Method Name		: getExcelData()
	 * Purpose			: to read the data from the excel based on logicalName
	 * Author			:
	 * Reviewer			:
	 * Arguments		: keyName
	 * Return type		: String
	 * Date Created		: 
	 * **********************************************
	 */
	public Map<String, String> getExcelData(String sheetName, String logicalName)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row1 = null;
		Row row2 = null;
		Cell cell1 = null;
		Cell cell2 = null;
		int rowNum = 0;
		String key = null;
		String value = null;
		String sDay = null;
		String sMonth = null;
		String sYears = null;
		Map<String, String> objData = null;
		try {
			objData = new HashMap<String, String>();
			fin = new FileInputStream(System.getProperty("user.dir")+"\\TestData\\"+moduleName+".xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			if(sh==null) {
				reports.writeResult(null, "Fail", "The sheet '"+sheetName+"' doesnot exist. Hence can't read the test data", test, false);
				return null;
			}
			
			//Find the row number of the given logical name
			int rows = sh.getPhysicalNumberOfRows();
			for(int r=0; r<rows; r++)
			{
				row1 = sh.getRow(r);
				cell1 = row1.getCell(0);
				if(cell1.getStringCellValue().equals(logicalName)) {
					rowNum = r;
					break;
				}
			}
			
			
			//If logical name is present then read the data from the row
			//and take the keys from the columns and read a MAP
			if(rowNum>0) {
				row1 = sh.getRow(0);
				row2 = sh.getRow(rowNum);
				for(int c=0; c<row1.getPhysicalNumberOfCells(); c++)
				{
					cell1 = row1.getCell(c);
					cell2 = row2.getCell(c);
					key = cell1.getStringCellValue();
					
					//Foramt and read the test data
					if(cell2==null || cell2.getCellType()==CellType.BLANK)
					{
						value = "";
					}else if(cell2.getCellType()==CellType.BOOLEAN) {
						value = String.valueOf(cell2.getBooleanCellValue());
					}else if(cell2.getCellType()==CellType.STRING)
					{
						value = cell2.getStringCellValue();
					}else if(cell2.getCellType()==CellType.NUMERIC)
					{
						//Validate the cell comtain date
						if(HSSFDateUtil.isCellDateFormatted(cell2))
						{
							double dt = cell2.getNumericCellValue();
							Calendar cal = Calendar.getInstance();
							cal.setTime(HSSFDateUtil.getJavaDate(dt));
							
							//If day is less than 10 then prefix zero
							if(cal.get(Calendar.DAY_OF_MONTH)<10) {
								sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
							}else {
								sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
							}
							
							
							//If month is less than 10 then prefix zero
							if(cal.get(Calendar.DAY_OF_MONTH)<10) {
								sMonth = "0" + (cal.get(Calendar.MONTH)+1);
							}else {
								sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
							}
							
							sYears = String.valueOf(cal.get(Calendar.YEAR));
							value = sDay +"-"+ sMonth +"-"+ sYears;
						}else {
							value = String.valueOf(cell2.getNumericCellValue());
						}
					}
					objData.put(key, value);
				}
				
				return objData;
			}else {
				reports.writeResult(null, "Fail", "Failed to find the logical name '"+logicalName+"' in the test data sheet", test, false);
				return null;
			}
			
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in getExcelData() method. "+e.getMessage(), test, false);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell1 = null;
				cell2 = null;
				row1 = null;
				row2 = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exception in getExcelData() method. "+e.getMessage(), test, false);
				return null;
			}
		}
	}
	
	
	
	
	/************************************************
	 * Method Name		: getRowNumber()
	 * Purpose			: to get the row number form the excel file
	 * Author			:
	 * Reviewer			:
	 * Arguments		: filePath, sheetName
	 * Return type		: int
	 * Date Created		: 
	 * **********************************************
	 */
	public int getRowNumber(String filePath, String sheetName)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		int rowCount = 0;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh==null) {
				reports.writeResult(null, "Fail", "The sheet '"+sheetName+"' doesnot exist", test, false);
				return -1;
			}
			
			rowCount = sh.getPhysicalNumberOfRows()-1;
			return rowCount;
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in getRowNumber() method. "+e.getMessage(), test, false);
			return -1;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exception in getRowNumber() method. "+e.getMessage(), test, false);
			}
		}
	}
	
	
	
	/************************************************
	 * Method Name		: getCellData()
	 * Purpose			: to read the cell data form the excel file
	 * Author			:
	 * Reviewer			:
	 * Arguments		: filePath, sheetName, colName, rowNum
	 * Return type		: String
	 * Date Created		: 
	 * **********************************************
	 */
	public String getCellData(String filePath, String sheetName, String colName, int rowNum)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		String strData = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh==null) {
				reports.writeResult(null, "Fail", "The sheet '"+sheetName+"' doesnot exist", test, false);
				return null;
			}
			
			//Find out the column number based on the column name
			row = sh.getRow(0);
			for(int c=0; c<row.getPhysicalNumberOfCells(); c++)
			{
				cell = row.getCell(c);
				if(cell.getStringCellValue().equalsIgnoreCase(colName))
				{
					colNum = c;
					break;
				}
			}
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			
			if(cell==null || cell.getCellType()==CellType.BLANK)
			{
				strData = "";
			}else if(cell.getCellType()==CellType.BOOLEAN) {
				strData = String.valueOf(cell.getBooleanCellValue());
			}else if(cell.getCellType()==CellType.STRING)
			{
				strData = cell.getStringCellValue();
			}else if(cell.getCellType()==CellType.NUMERIC)
			{
				//Validate the cell comtain date
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					double dt = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(dt));
					
					//If day is less than 10 then prefix zero
					if(cal.get(Calendar.DAY_OF_MONTH)<10) {
						sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
					}else {
						sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
					}
					
					
					//If month is less than 10 then prefix zero
					if(cal.get(Calendar.DAY_OF_MONTH)<10) {
						sMonth = "0" + (cal.get(Calendar.MONTH)+1);
					}else {
						sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
					}
					
					sYear = String.valueOf(cal.get(Calendar.YEAR));
					strData = sDay +"-"+ sMonth +"-"+ sYear;
				}else {
					strData = String.valueOf(cell.getNumericCellValue());
				}
			}
			return strData;
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in getCellData() method. "+e.getMessage(), test, false);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exception in getCellData() method. "+e.getMessage(), test, false);
				return null;
			}
		}
	}
	
	
	
	
	
	
	/************************************************
	 * Method Name		: setCellData()
	 * Purpose			: write to the excel cell
	 * Author			:
	 * Reviewer			:
	 * Arguments		: filePath, sheetName, colName, rowNum, strData
	 * Return type		: String
	 * Date Created		: 
	 * **********************************************
	 */
	public void setCellData(String filePath, String sheetName, String colName, int rowNum, String strData)
	{
		FileInputStream fin = null;
		FileOutputStream fout = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		CellStyle style = null;
		Font font = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh==null) {
				reports.writeResult(null, "Fail", "The sheet '"+sheetName+"' doesnot exist", test, false);
				return;
			}
			
			//Find out the column number based on the column name
			row = sh.getRow(0);
			for(int c=0; c<row.getPhysicalNumberOfCells(); c++)
			{
				cell = row.getCell(c);
				if(cell.getStringCellValue().equalsIgnoreCase(colName))
				{
					colNum = c;
					break;
				}
			}
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			
			style = wb.createCellStyle();
			font = wb.createFont();
			if(strData.equalsIgnoreCase("Passed")) {
				style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
				style.setFillPattern(FillPatternType.LEAST_DOTS);
				font.setBold(true);
				style.setFont(font);
			}else if(strData.equalsIgnoreCase("Failed")) {
				style.setFillBackgroundColor(IndexedColors.RED.getIndex());
				style.setFillPattern(FillPatternType.LEAST_DOTS);
				font.setBold(true);
				style.setFont(font);
			}else if(strData.equalsIgnoreCase("Skipped")) {
				style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
				style.setFillPattern(FillPatternType.LEAST_DOTS);
				font.setBold(true);
				style.setFont(font);
			}
			
			
			if(row.getCell(colNum)==null) {
				cell = row.createCell(colNum);
			}
			
			cell.setCellValue(strData);
			cell.setCellStyle(style);
			fout = new FileOutputStream(filePath);
			wb.write(fout);
			
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in setCellData() method. "+e.getMessage(), test, false);
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exception in setCellData() method. "+e.getMessage(), test, false);
			}
		}
	}
}
