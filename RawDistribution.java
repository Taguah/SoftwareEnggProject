package project.excelSpike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RawDistribution extends Distribution{
	
	private Map<String, ArrayList<Course>> courseNameMap;
	
	public RawDistribution(String gradeSchemaFile) {
		Map<String, ArrayList<Course>> distributionMap = new HashMap<String, ArrayList<Course>>();
		GradeSchema gradeSchema = new GradeSchema(gradeSchemaFile);
	}
	
	public Map<String, Integer> getDistributionForCourse(String courseName) {
		Map<String, Integer> courseDistribution = this.createDistributionMap();
		ArrayList<Course> coursesOfName = courseNameMap.get(courseName);
		ArrayList<String> letterGrades = this.getLetterGrades(coursesOfName);
		return courseDistribution;
	}
}
