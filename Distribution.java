package project.excelSpike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Distribution {

	private Map<String, ArrayList<Course>> distributionMap;
	private GradeSchema gradeSchema;
	
	static enum Level {
		Exceeds,
		Meets,
		Marginal,
		Fails,
		Other
        }
	
	public Distribution(String gradeSchemaFile) {
		gradeSchema = new GradeSchema(gradeSchemaFile);
		distributionMap = new HashMap<String, ArrayList<Course>>();
	}
	
	public Map<String, Integer> createDistributionMap(){
		Map<String, Integer> distributionMap = new HashMap<String, Integer>();
		Set<Level> levels = gradeSchema.getLevels();
		for (Level level : levels){
			distributionMap.put(level, 0);
		}
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
