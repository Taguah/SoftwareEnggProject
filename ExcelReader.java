package excelSpike;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	public static void read(String directory) {
		try {
			FileInputStream excelIn = new FileInputStream(new File(directory));
			Workbook workbook = new XSSFWorkbook(excelIn);
			ExcelReader.readExcelArea(workbook);
			ExcelReader.readExcelEquivalencies(workbook);
			excelIn.close();
			workbook.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Please create a workbook and two sheets to read from named 'Areas' and 'Equivalents' in the specified directory.");
		}
		catch(IOException e) {
			System.out.println("Please close the workbook before running this application.");
		}
	}
	
	//Columns must be organized longest to shortest
	public static void readExcelArea(Workbook workbook) {
		AreaSchema.getAreaMap().clear();
		Sheet areas = workbook.getSheet("Areas");
		
		int numCols = areas.getRow(0).getLastCellNum();
		
		for (int col = 0; col < numCols; col++) {
			int row = 0;
			Cell current = areas.getRow(row).getCell(col);
			String area = current.getStringCellValue().trim();
			row++;
			current = areas.getRow(row).getCell(col);
			ArrayList<String> courses = new ArrayList<>();
			while (current != null) {
				courses.add(current.getStringCellValue().trim());
				row++;
				if (areas.getRow(row) == null) {
					break;
				}
				current = areas.getRow(row).getCell(col);
			}
			AreaSchema.addArea(area, courses);
		}
	}
	
	public static void readExcelEquivalencies(Workbook workbook) {
		EquivalencySchema.clearEquivalencies();
		Sheet equivSheet = workbook.getSheet("Equivalents");

		int numCols = equivSheet.getRow(0).getLastCellNum();

		for(int col = 0; col < numCols; col++) {
			int row = 0;
			Cell current = equivSheet.getRow(row).getCell(col);
			String subject = current.getStringCellValue().trim();
			row++;
			current = equivSheet.getRow(row).getCell(col);
			List<String> equivList = new ArrayList<>();
			while(current != null) {
				equivList.add(current.getStringCellValue().trim());
				row++;
				if(equivSheet.getRow(row) == null) {
					break;
				}
				current = equivSheet.getRow(row).getCell(col);
			}
			EquivalencySchema.setEquivalency(subject, equivList);
		}
	}
}
