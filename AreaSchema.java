package project.excelSpike;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

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
			
			if (areaMap.containsKey(areaName)) {
				ArrayList<String> currentCourses = areaMap.get(areaName);
				currentCourses.addAll(areaCourses);
			}
			else {
				areaMap.put(areaName, areaCourses);
			}
		}
	}
	
	public static ArrayList<String> getAllAreas(){
		ArrayList<String> areas = new ArrayList<String>(areaMap.keySet());
		return areas;
	}
	
	public static ArrayList<String> getAllCoursesInArea(String area){
		return areaMap.get(area);
	}
	
}
