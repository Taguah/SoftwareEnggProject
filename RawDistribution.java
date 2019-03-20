package project.excelSpike;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class RawDistribution extends Distribution{
	
	private Map<String, ArrayList<Course>> courseNameMap;
	
	public RawDistribution(TranscriptList transcriptList) {
		this.transcriptList = transcriptList;
		
		courseNameMap = transcriptList.getAllCoursesByName();
		
		Set<String> courseNames = courseNameMap.keySet();
		for (String courseName : courseNames){
			this.setDistributionForCourse(courseName);	
		}
	}
	
	public Map<String, Integer> setDistributionForCourse(String courseName) {
		Map<String, Integer> courseDistribution = this.createDistributionMap();
		ArrayList<Course> coursesOfName = courseNameMap.get(courseName);
		ArrayList<String> letterGrades = this.getLetterGrades(coursesOfName);
		return courseDistribution;
	}
}
