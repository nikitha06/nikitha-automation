package qa.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import qa.base.TestBase;

public class TestUtil extends TestBase{
	public static long PAGE_LOAD_TIMEOUT = 40;
	public static long IMPLICIT_WAIT = 20;
	static FileInputStream fileinput;
	static Workbook book1;
	static Sheet sheet2;
	public static String timestamp;
	
	
	
	
	public static Object[][] getTestData(String sheetName) throws EncryptedDocumentException, IOException{
		
		fileinput = new FileInputStream(excelpath);
		book1 = WorkbookFactory.create(fileinput);
		sheet2 = book1.getSheet(sheetName);
		System.out.println(sheet2.getPhysicalNumberOfRows());
		System.out.println("Last row number: "+sheet2.getLastRowNum());
		System.out.println("Last cell number: "+sheet2.getRow(0).getLastCellNum());
		Object[][] data = new Object[sheet2.getLastRowNum()][sheet2.getRow(0).getLastCellNum()];
		for(int k=0; k<sheet2.getLastRowNum();k++) {
			for(int j=0;j<sheet2.getRow(0).getLastCellNum();j++) {
				
				data[k][j] = sheet2.getRow(k+1).getCell(j).toString();
				System.out.println(data[k][j]);
			}
		}
		
		return data;
	}
	
	public static String getTimestamp() {
		 timestamp = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
		return timestamp;
		
	}
	public static String takeScreenshot() throws IOException {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir");
		String screenshotpath = path+"\\screenshots\\"+getTimestamp()+".png";
		System.out.println(path);
		
		File destFile = new File(screenshotpath);
		FileUtils.copyFile(srcFile, destFile);
		return screenshotpath;
	}
	
	
	}
	