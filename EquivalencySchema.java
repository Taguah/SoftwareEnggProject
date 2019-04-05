package excelSpike;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

/*
 * Class for the equivalency schema.
 * Maps courses to their latest equivalent.
 * Mapping is done with the equivalent as the value and the old course name as the key.
 */
public class EquivalencySchema {

	private static Map<String, String> equivMap = new HashMap<String, String>();
	
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
	
	public static void clearEquivalencyMap() {
		equivMap.clear();
	}
}
