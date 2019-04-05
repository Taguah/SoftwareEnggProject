package excelSpike;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
		int sheetIndex = workbook.getSheetIndex("Areas");
		Sheet areas = workbook.getSheetAt(sheetIndex);
		Iterator<Row> iterR = areas.iterator();
		Row currentRow = iterR.next();
		Iterator<Cell> iterC = currentRow.cellIterator();
		int areaCount = 0;
		
		//areaCount sometimes counts additional empty columns
		while (iterC.hasNext()) {
			iterC.next();
			areaCount++;
		}
		//iterate rows by column
		for (int i = 1; i < areaCount; i++) {
			//resets the row iterator per column
			iterR = areas.iterator();
			currentRow = iterR.next();
			iterC = currentRow.cellIterator();
			//indexes column for area name
			for (int j = 1; j < i; j++) {
				iterC.next();
			}
			String areaName = iterC.next().getStringCellValue();
			
			ArrayList<String> areaCourses = new ArrayList<>();
			while (iterR.hasNext()) {
				currentRow = iterR.next();
				iterC = currentRow.cellIterator();
				if (currentRow.getPhysicalNumberOfCells() != 0) {
					areaCourses.add(iterC.next().getStringCellValue());
					iterC.remove();
					currentRow.shiftCellsLeft(1, areaCount, 1);
				}
			}
			AreaSchema.addArea(areaName, areaCourses);
		}
	}
		
	public static void readExcelEquivalencies(Workbook workbook) {
		EquivalencySchema.clearEquivalencyMap();
		int eqColCount = 0;
		String mainCourse = null;
		List<String> altCourses = new ArrayList<>();
		int sheetIndex = workbook.getSheetIndex("Equivalents");
		Sheet equivalents = workbook.getSheetAt(sheetIndex);
		Iterator<Row> iterR = equivalents.iterator();
		Row currentRow = iterR.next();
		Iterator<Cell> iterC = currentRow.cellIterator();
		while (iterC.hasNext()) {
			iterC.next();
			eqColCount++;
		}
		//iterate rows by column
		for (int i = 1; i < eqColCount+1; i++) {
			//resets the row iterator per column
			iterR = equivalents.iterator();
			currentRow = iterR.next();
			iterC = currentRow.cellIterator();
			for (int j = 1; j < i; j++) {
				iterC.next();
			}
			if (iterC.hasNext()) {
				mainCourse = iterC.next().getStringCellValue();
			}
			while (iterR.hasNext()) {
				currentRow = iterR.next();
				iterC = currentRow.cellIterator();
				if (currentRow.getPhysicalNumberOfCells() != 0) {
					altCourses.add(iterC.next().getStringCellValue());
					iterC.remove();
					currentRow.shiftCellsLeft(1, eqColCount, 1);
					EquivalencySchema.setEquivalency(mainCourse, altCourses);
				}
			}
		}
	}
}
