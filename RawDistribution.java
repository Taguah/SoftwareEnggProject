package project.excelSpike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RawDistribution extends Distribution{
	
	private Map<String, ArrayList<Course>> courseNameMap;
	private Map<String, Map<String, Integer>> rawDistributionMap;
	
	public RawDistribution(TranscriptList transcriptList) {
		this.transcriptList = transcriptList;
		rawDistributionMap = new HashMap<String, Map<String, Integer>>();
		this.setDistributionForCourses();
	}

	public void setDistributionForCourses() {
		Map<String, List<Course>> allCourses = transcriptList.getAllCoursesByEquivalentName();
		for (String currentCourse : allCourses.keySet()) {
			Map<String, Integer> courseDistribution = this.createDistributionMap();
			List<Course> allCoursesOfName = allCourses.get(currentCourse);
			for (Course course : allCoursesOfName) {
				String letterGrade = course.getGrade().getLetterGrade();
				for (String level : courseDistribution.keySet()) {
					Set<String> gradesInLevel = GradeSchema.getGradesForLevel(level);
					if (gradesInLevel.contains(letterGrade)) {
						courseDistribution.put(level, courseDistribution.get(level)+1);
					}
				}
				rawDistributionMap.put(currentCourse, courseDistribution);
			}
		}
	}

	public Map<String, Integer> getCourseDistribution(String courseName){
		return rawDistributionMap.get(courseName);
	}

	public Map<String, Map<String, Integer>> getRawDistributionMap(){
		return rawDistributionMap;
	}

}