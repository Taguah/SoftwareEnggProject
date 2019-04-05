package analyzer;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * Class for the equivalency schema.
 * Maps courses to their latest equivalent.
 * Mapping is done with the equivalent as the value and the old course name as the key.
 */
public class EquivalencySchema {

	private static Map<String, String> equivMap = new HashMap<String, String>();

	/*
	 * Calls ExcelReader to read the relevant config file
	 * (NOTE: we may want to hardcode this file path)
	 * and uses it to generate a list of equivalents.
	 * Clears the current map and creates a new one from these contents.
	 * Currently set so that the last equivalency listed overwrites older ones.
	 * @param String The filename for the equivalency config
	 */
	/*
	public static void setEquivalenciesFromFile(String fileName) {
		equivMap.clear();
		ArrayList<String> fileContents = ExcelParser.readConfigFile(fileName);
		while (!fileContents.isEmpty()) {
			String mainCourseName = fileContents.get(0);
			fileContents.remove(0);
			
			String equivContents = fileContents.get(0);
			fileContents.remove(0);
			
			String[] equivCourseArray = equivContents.split(",");
			
			for (String course : equivCourseArray) {
				course = course.trim();
				equivMap.put(course, mainCourseName);
			}
		}
	}
	*/

	public static void setEquivalenciesFromFile(String fileName) {
		equivMap.clear();
		try {
			FileInputStream excelIn = new FileInputStream(new File(fileName));
			Workbook workbook = new XSSFWorkbook(excelIn);
			Sheet equivSheet = workbook.getSheet("Equivalents");

			int numCols = equivSheet.getRow(0).getLastCellNum();

			for(int col = 0; col < numCols; col++) {
				int row = 0;
				Cell current = equivSheet.getRow(row).getCell(col);
				String subject = current.getStringCellValue();
				row++;
				current = equivSheet.getRow(row).getCell(col);
				List<String> equivList = new ArrayList<>();
				while(current != null) {
					equivList.add(current.getStringCellValue());
					row++;
					if(equivSheet.getRow(row) == null) {
						break;
					}
					current = equivSheet.getRow(row).getCell(col);
				}
				setEquivalency(subject, equivList);
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
	
	public static void setEquivalency(String course, List<String> equivalents) {
		for (String equivCourse : equivalents) {
			equivMap.put(equivCourse, course);
		}
	}

	/*
	 * Checks if a course has a mapped equivalent. If so, return that course's name. Otherwise, returns the original name.
	 * @return The name of the course or, if available, its equivalent.
	 */
	public static String getEquivalent(String courseName){
		if (equivMap.containsKey(courseName)) {
			return (equivMap.get(courseName));
		}
		else {
			return courseName;
		}
	}
	
}
