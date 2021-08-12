package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class dataDriven {

	//Search for credentials from a Excel file and return it as ArrayList of strings
		public ArrayList<String> getData(String testCaseName) throws IOException {
			ArrayList<String> a = new ArrayList<String>();

			//Direction of the Excel file i use this path to be able to open it from another machine
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\datos.xlsx");
			
			XSSFWorkbook workbook = new XSSFWorkbook(fis);

			//Get the number of sheets from the Excel
			int sheets = workbook.getNumberOfSheets();

			//I iterate through the sheets
			for (int i = 0; i < sheets; i++) {
				
				//get the sheet name
				String sheetName = workbook.getSheetName(i);

				//If the sheet im looking for in this case is "data" i keep going on, otherwise i iterate again searching another sheet with that name
				if (sheetName.equalsIgnoreCase("data")) {
					//save the sheet
					XSSFSheet sheet = workbook.getSheetAt(i);

					// Identify testcases coloumn by scanning the entire 1st row
					Iterator<Row> rows = sheet.rowIterator();// sheet is collection of rows
					Row firstrow = rows.next();
					Iterator<Cell> ce = firstrow.cellIterator();// row is collection of cells

					int k = 0;
					int coloumn = 0;
					//I go through cells to find out which has the column "Testcases"
					while (ce.hasNext()) {
						Cell value = ce.next();
						if (value.getStringCellValue().equalsIgnoreCase("Testcases")) {
							coloumn = k;
						}
						k++;
					}
					//I find out which row has the "Desired Info." which i send as an argument to the method /(testCaseName)/
					while (rows.hasNext()) {
						Row r = rows.next();
						if (r.getCell(coloumn).getStringCellValue().equalsIgnoreCase(testCaseName)) {
							//Capture the data from the cells into an ArrayList
							Iterator<Cell> cv = r.cellIterator();
							while (cv.hasNext()) {
														
									a.add(cv.next().getStringCellValue());
																
							}

						}
					}
					//I close the workbook(Excel file) and i jump outside the for loop to stop searching if i already found the sheet i wanted
					workbook.close();
					break;
				}
			}
			return a;
		}
	
	
}
