package project.excelSpike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RawDistribution extends Distribution{

	// This is the map that matches levels like "Exceeds" to a number like 2
	private Map<String, ArrayList<Course>> courseNameMap;

	// This is all the courses that will show up in our output file
	private ArrayList<String> courses;
	
	
	public RawDistribution(String gradeSchemaFile) {
		Map<String, ArrayList<Course>> courseNameMap = new HashMap<String, ArrayList<Course>>();
		this.mapNamesToCourseList();
		GradeSchema gradeSchema = new GradeSchema(gradeSchemaFile);
	}

	private void mapNamesToCourseList(){
		//	
	}
	
	public Map<Level, Integer> getDistributionForCourse(String courseName) {
		Map<String, Integer> courseDistribution = this.createDistributionMap();
		ArrayList<Course> coursesOfName = courseNameMap.get(courseName);
		ArrayList<String> letterGrades = this.getLetterGrades(coursesOfName);
		for (String letterGrade : letterGrades){
			Set<Level> levels = gradeSchema.getLevels();
			for (Level level : levels){
				Set<String> gradesInLevel = gradeSchema.getGradesForLevel();
				if (gradesInLevel.contains(letterGrade){
					courseDistribution.get(level.toString())++;
				}
			}
		}
		return courseDistribution;
	}
}
