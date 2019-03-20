package project.excelSpike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Distribution {

	protected TranscriptList transcriptList;
	
/*
	static enum Level {
		Exceeds,
		Meets,
		Marginal,
		Fails,
		Other
        }
*/
	
	public Map<String, Integer> createDistributionMap(){
		Map<String, Integer> distributionMap = new HashMap<String, Integer>();
		Set<String> levels = GradeSchema.getLevels();
		for (String level : levels){
			distributionMap.put(level.toString(), 0);
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
