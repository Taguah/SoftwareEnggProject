package project.excelSpike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Distribution {

	private Map<String, ArrayList<Course>> distributionMap;
	private GradeSchema gradeSchema;
	
/*
	public Distribution(String gradeSchemaFile) {
		distributionMap = new HashMap<String, ArrayList<Course>>();
		gradeSchema = new GradeSchema(gradeSchemaFile);
	}
*/
	
	public Map<String, Integer> createDistributionMap(){
		Map<String, Integer> distributionMap = new HashMap<String, Integer>();
		return distributionMap;
	}
	
	public ArrayList<String> getLetterGrades(ArrayList<Course> courseList){
		ArrayList<String> letterGrades = new ArrayList<String>();
		for (Course course : courseList) {
			String letterGrade = course.getGrade().getLetterGrade();
			letterGrades.add(letterGrade);
		}
		return letterGrades;
	}
	

}