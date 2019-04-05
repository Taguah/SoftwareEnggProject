package excelSpike;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	
	private static String[] aCategories = {"area", "other", "fails", "marginal", "meets", "exceeds"};
	private static String[] rCategories = {"course", "other", "fails", "marginal", "meets", "exceeds", "fails (E)", "marginal(E)", "meets(E)", "exceeds(E)"};
	private static String fileLocation = "./output/Data.xlsx";
	
	//At the moment, excel is required to be closed before running this method (Seemingly not on linux though)
	public static void write(TranscriptList tList) {
		try {
			Files.createDirectories(Paths.get("./output"));
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
			ExcelWriter.writeExcelArea(tList, workbook);
			ExcelWriter.writeExcelRaw(tList, workbook);
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
	
	public static void writeExcelArea(TranscriptList tList, Workbook workbook) {
		AreaDistribution aDist = new AreaDistribution(tList);
		aDist.setDistributionForAreas();
		
		try {
			FileOutputStream excelOut = new FileOutputStream(new File(fileLocation));
			Sheet areaSheet = workbook.createSheet("Area Distribution");
			Object[] areaNames = aDist.getAreaDistributionMap().keySet().toArray();
			int rowInd = 0;
			Row row = areaSheet.createRow(rowInd++);
			for (int colInd = 0; colInd < aCategories.length; colInd++) {
				Cell cell = row.createCell(colInd);
				cell.setCellValue((String) aCategories[colInd]);
			}
			for (Map<String, Integer> areaDist : aDist.getAreaDistributionMap().values()) {
				Object[] values = areaDist.values().toArray();
				row = areaSheet.createRow(rowInd++);
				for (int colInd = 0; colInd < values.length+1; colInd++) {
					if (colInd == 0) {
						Cell cell = row.createCell(colInd);
						cell.setCellValue((String) areaNames[rowInd - 2]);
					}
					else {
						Cell cell = row.createCell(colInd);
						cell.setCellValue((Integer) values[colInd - 1]);
					}
				}
			}
			workbook.write(excelOut);
			excelOut.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Please close the workbook before running this application.");
		}
		catch(IOException e) {
			System.out.println("Critical error: Distribution data excel file has been corrupted. Deleting output. Please try again.");
			File f = new File("./excel/Data.xlsx");
			f.delete();
		}
	}
	
	public static void writeExcelRaw(TranscriptList tList, Workbook workbook) {
		RawDistribution rDist = new RawDistribution(tList);
		rDist.setDistributionForCourses();
		rDist.setDistributionForRetakenCourses();
		ArrayList<Map<String, Integer>> courseDists = new ArrayList<>();
		ArrayList<Map<String, Integer>> retakenDists = new ArrayList<>();
		try {
			FileOutputStream excelOut = new FileOutputStream(new File(fileLocation));
			Sheet rawSheet = workbook.createSheet("Raw Distribution");
			Object[] courseNames = rDist.getRawDistributionMap().keySet().toArray();
			int rowInd = 0;
			Row row = rawSheet.createRow(rowInd++);
			for (int colInd = 0; colInd < rCategories.length; colInd++) {
				Cell cell = row.createCell(colInd);
				cell.setCellValue((String) rCategories[colInd]);
			}
			
			//compounds regular and retaken courses
			courseDists.addAll(rDist.getRawDistributionMap().values());
			retakenDists.addAll(rDist.getRetakenDistributionMap().values());
			for (String courseName : rDist.getRawDistributionMap().keySet()) {
				Map<String, Integer> courseDist = rDist.getCourseDistribution(courseName);
				Object[] courseValues = courseDist.values().toArray();
				//length-2 until "other" column is added
				Object[] allValues = Arrays.copyOf(courseValues, rCategories.length-2);
				for (Map<String, Integer> retakenDist : retakenDists) {
					if (rDist.getRetakenDistributionMap().containsKey(courseName)) {
						//concat retaken values to course values
						retakenDist = rDist.getRetakenCourseDistribution(courseName);
						Object[] retakenValues = retakenDist.values().toArray();
						System.arraycopy(courseValues,  0,  allValues,  0,  courseValues.length);
						System.arraycopy(retakenValues,  0,  allValues,  courseValues.length,  retakenValues.length);
						break;
					}
				}
				//length-2 until "other" column is added
				for (int i = 0; i < rCategories.length-2; i++) {
					if (allValues[i] == null) {
						allValues[i] = 0;
					}
				}
				row = rawSheet.createRow(rowInd++);
				for (int colInd = 0; colInd <= allValues.length; colInd++) {
					if (colInd == 0) {
						Cell cell = row.createCell(colInd);
						cell.setCellValue((String) courseNames[rowInd - 2]);
					}
					else {
						Cell cell = row.createCell(colInd);
						cell.setCellValue((Integer) allValues[colInd-1]);
						//System.out.println((Integer) allValues[colInd-1]);
					}
				}
			}
			workbook.write(excelOut);
			excelOut.close();
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
}
