package analyzer;

import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class ExcelParser {
	
	private static String[] aCategories = {"area", "other", "fails", "marginal", "meets", "exceeds", "fails (E)", "marginal(E)", "meets(E)", "exceeds(E)"};
	private static String[] rCategories = {"course", "other", "fails", "marginal", "meets", "exceeds", "fails (E)", "marginal(E)", "meets(E)", "exceeds(E)"};
	
	//At the moment, excel is required to be closed before running this method
	public static void parse(RawDistribution rawDist, AreaDistribution areaDist, String filepath) {
		try {
			FileInputStream excelIn = new FileInputStream(new File(filepath));
			Workbook workbook = new XSSFWorkbook(excelIn);
			for (int i = workbook.getNumberOfSheets() - 1; i >= 0; i--) {
				if ((workbook.getSheetName(i).equals("Raw Distribution")) || 
						(workbook.getSheetName(i).equals("Area Distribution"))) {
					workbook.removeSheetAt(i);
				}
			}
			ExcelParser.writeExcelRaw(rawDist, workbook, filepath);
			ExcelParser.writeExcelArea(areaDist, workbook, filepath);
			//ExcelParser.readExcelArea(workbook);
			excelIn.close();
			workbook.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Please create a workbook named 'Data.xlsx' and a sheet to read from named 'Areas' in the 'excel' directory of the project file");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//Columns must be organized longest to shortest
	public static void readExcelArea(String path) {
		AreaSchema.getAreaMap().clear();
		try {
			FileInputStream excelIn = new FileInputStream(new File(path));
			Workbook workbook = new XSSFWorkbook(excelIn);
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
			for (int i = 1; i <= areaCount; i++) {
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
	
			
			excelIn.close();
			workbook.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Please create a workbook named 'Data.xlsx' and a sheet to read from named 'Areas' in the 'excel' directory of the project file");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeExcelArea(AreaDistribution aDist, Workbook workbook, String path) {
		Sheet areaSheet = workbook.createSheet("Area Distribution");
		Object[] areaNames = aDist.getAreaDistributionMap().keySet().toArray();
		int rowInd = 0;
		Row row = areaSheet.createRow(rowInd++);
		for (int colInd = 0; colInd < aCategories.length-4; colInd++) {
			Cell cell = row.createCell(colInd);
			cell.setCellValue((String) aCategories[colInd]);
		}
		for(String key : aDist.getAreaDistributionMap().keySet()) {
			Map<String, Integer> courseDist = aDist.getAreaDistributionMap().get(key);
			int column = 1;
			row = areaSheet.createRow(rowInd++);
			row.createCell(0).setCellValue(key);

			for(String level : courseDist.keySet()) {
				int value = courseDist.get(level);

				row.createCell(column).setCellValue(value);
				
				column++;
			}
		}
		try {
			//Relative filepath from this project
			FileOutputStream excelOut = new FileOutputStream(new File(path));
			workbook.write(excelOut);
			excelOut.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeExcelRaw(RawDistribution rDist, Workbook workbook, String path) {
		Sheet rawSheet = workbook.createSheet("Raw Distribution");
		Object[] courseNames = rDist.getRawDistributionMap().keySet().toArray();
		int rowInd = 0;
		Row row = rawSheet.createRow(rowInd++);
		for (int colInd = 0; colInd < rCategories.length; colInd++) {
			Cell cell = row.createCell(colInd);
			cell.setCellValue((String) rCategories[colInd]);
		}

		for(String key : rDist.getRawDistributionMap().keySet()) {
			Map<String, Integer> courseDist = rDist.getRawDistributionMap().get(key);
			Map<String, Integer> courseDistRetaken = rDist.getRetakenDistributionMap().get(key);
			int column = 1;
			row = rawSheet.createRow(rowInd++);
			row.createCell(0).setCellValue(key);

			for(String level : courseDist.keySet()) {
				int value = courseDist.get(level);
				int valueRetaken = 0;
				if(courseDistRetaken != null) {
					valueRetaken = courseDistRetaken.get(level);
				}
				row.createCell(column).setCellValue(value);
				if(column != 1) {
					row.createCell(column+4).setCellValue(valueRetaken);
				}
				
				column++;
			}
		}
		
		try {
			//Relative filepath from this project
			FileOutputStream excelOut = new FileOutputStream(new File(path));
			workbook.write(excelOut);
			excelOut.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
