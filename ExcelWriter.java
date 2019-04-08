package excelSpike;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	
	private static String[] aCategories = {"area", "meets", "marginal", "exceeds", "fails", "other"};
	private static String[] rCategories = {"course", "exceeds", "fails", "marginal", "meets", "other", "fails(E)", "marginal(E)", "meets(E)", "exceeds(E)"};
	
	
	//At the moment, excel is required to be closed before running this method (Seemingly not on linux though)
	public static void write(TranscriptList tList, String fileLocation) {
		try {
			fileLocation = fileLocation.substring(0, fileLocation.lastIndexOf('\\')) + "\\OutputData.xlsx";
		}
		catch (StringIndexOutOfBoundsException e) {
			System.out.println("No transcript folder specified");
		}
		try {
			File f = new File(fileLocation);
			if (f.isFile()) {
				FileInputStream excelIn = new FileInputStream(new File(fileLocation));
				Workbook workbook = new XSSFWorkbook(excelIn);
				for (int i = workbook.getNumberOfSheets() - 1; i >= 0; i--) {
					if ((workbook.getSheetName(i).equals("Raw Distribution")) || 
					(workbook.getSheetName(i).equals("Area Distribution"))) {
						workbook.removeSheetAt(i);
					}
				}
				excelIn.close();
				workbook.close();
			}
			Workbook workbook = new XSSFWorkbook();
			//System.out.println(tList.getTakenCourseGrades().toString());
			//System.out.println(tList.getRetakenCourseGrades().toString());
			ExcelWriter.writeExcelArea(tList, workbook, fileLocation);
			ExcelWriter.writeExcelRaw(tList, workbook, fileLocation);
			workbook.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Please close the workbook before running this application.");
		}
		catch(IOException e) {
			System.out.println("Critical error: Distribution data excel file has been corrupted. Deleting output. Please try again.");
			File f = new File(fileLocation);
			f.delete();
		}
	}
	
	public static void writeExcelArea(TranscriptList tList, Workbook workbook, String fileLocation) {
		Sheet areaSheet = workbook.createSheet("Area Distribution");
		AreaDistribution aDist = new AreaDistribution(tList);
		aDist.setDistributionForAreas();
		Map<String, Map<String, Integer>> areaMap = aDist.getAreaDistributionMap();
		
		int rowInd = 0;
		Row row = areaSheet.createRow(rowInd++);
		for (int colInd = 0; colInd < aCategories.length; colInd++) {
			Cell cell = row.createCell(colInd);
			cell.setCellValue((String) aCategories[colInd]);
		}

		Set<String> set = areaMap.keySet();
		List<String> list = new ArrayList<>(set);
		Collections.sort(list);

		for(String key : list) {
			Map<String, Integer> areaDist = areaMap.get(key);
			int column = 1;
			row = areaSheet.createRow(rowInd++);
			row.createCell(0).setCellValue(key);

			Set<String> levels = areaMap.get(key).keySet();
			List<String> levelList = new ArrayList<>(levels);
			Collections.sort(levelList);

			if (key != "") {
				for(String level : areaDist.keySet()) {
					int value = areaDist.get(level);
					row.createCell(column).setCellValue(value);
					column++;
				}
			}
		}
		try {
			//Relative filepath from this project
			FileOutputStream excelOut = new FileOutputStream(new File(fileLocation));
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
	
	public static void writeExcelRaw(TranscriptList tList, Workbook workbook, String fileLocation) {
		Sheet rawSheet = workbook.createSheet("Raw Distribution");
		RawDistribution rDist = new RawDistribution(tList);
		rDist.setDistributionForCourses();
		rDist.setDistributionForRetakenCourses();
		Map<String, Map<String, Integer>> rawMap = rDist.getRawDistributionMap();
		Map<String, Map<String, Integer>> retakenMap = rDist.getRetakenDistributionMap();
		
		int rowInd = 0;
		Row row = rawSheet.createRow(rowInd++);
		for (int colInd = 0; colInd < rCategories.length; colInd++) {
			Cell cell = row.createCell(colInd);
			cell.setCellValue((String) rCategories[colInd]);
		}
		
		Set<String> set = rawMap.keySet();
		List<String> list = new ArrayList<>(set);
		Collections.sort(list);
		
		for(String key : list) {
			Map<String, Integer> courseDist = rawMap.get(key);
			Map<String, Integer> courseDistRetaken = retakenMap.get(key);
			int column = 1;
			row = rawSheet.createRow(rowInd++);
			row.createCell(0).setCellValue(key);

			Set<String> levels = rawMap.get(key).keySet();
			List<String> levelList = new ArrayList<>(levels);
			Collections.sort(levelList);

			for(String level : levelList) {
				int value = courseDist.get(level);
				int valueRetaken = 0;
				if(!level.equals("Other")) {
					if(courseDistRetaken != null) {
						valueRetaken = courseDistRetaken.get(level);
					}
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
			FileOutputStream excelOut = new FileOutputStream(new File(fileLocation));
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
