package project.excelSpike;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

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
	public static void setEquivalenciesFromFile(String fileName) {
		equivMap.clear();
		ArrayList<String> fileContents = ExcelReader.readConfigFile(fileName);
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
