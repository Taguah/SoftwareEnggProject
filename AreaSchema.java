import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/*
 * Class for the area schema.
 * Maps areas to a list of courses.
 * The area name is the key, the list is the value.
 */
public class AreaSchema {

	private static Map<String, ArrayList<String>> areaMap = new HashMap<String, ArrayList<String>>();
	
	/*
	 * Calls ExcelReader to read the relevant config file
	 * (NOTE: we may want to hardcode this file path).
	 * Clears the current map and creates a new one from these contents.
	 * Currently set so that redundant area names will be merged. This may not be necessary.
	 * @param String The filename for the area config
	 */
	public static void setAreasFromFile(String fileName) {
		areaMap.clear();
		ArrayList<String> fileContents = ExcelReader.readConfigFile(fileName);
		while (!fileContents.isEmpty()) {
			String areaName = fileContents.get(0);
			fileContents.remove(0);
			
			ArrayList<String> areaCourses = new ArrayList<String>();
			String areaContents = fileContents.get(0);
			fileContents.remove(0);
			String[] contentArray = areaContents.split(",");
			
			for (String course : contentArray) {
				course = course.trim();
				areaCourses.add(course);
			}
			
			addArea(areaName, areaCourses);
		}
	}
	
	/*
	 * reads the specified excel sheet by column, utilizing addArea()
	 * requires that columns be organized longest to shortest, left to right
	 */
	public static void readExcelArea() {
		areaMap.clear();
		try {
			FileInputStream excelIn = new FileInputStream(new File("./src/cfg+xlsx/exampleResults.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelIn);
			Sheet areas = workbook.getSheetAt(3);
			Iterator<Row> iterR = areas.iterator();
			Row currentRow = iterR.next();
			Iterator<Cell> iterC = currentRow.cellIterator();
			int areaCount = 0;
			
			//areaCount is always 16 for some reason
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
				addArea(areaName, areaCourses);
			}
			excelIn.close();
			workbook.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void addArea(String area, ArrayList<String> courses) {
		if (areaMap.containsKey(area)) {
			ArrayList<String> currentCourses = areaMap.get(area);
			currentCourses.addAll(courses);
		}
		else {
			areaMap.put(area, courses);
		}
	}
	
	public static List<String> getAllAreas(){
		ArrayList<String> areas = new ArrayList<String>(areaMap.keySet());
		return areas;
	}
	
	public static List<String> getAllCoursesInArea(String area){
		return areaMap.get(area);
	}
	
}