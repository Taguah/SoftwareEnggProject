package analyzer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RawDistribution extends Distribution{
	
	private Map<String, Map<String, Integer>> rawDistributionMap;
	private Map<String, Map<String, Integer>> retakenDistributionMap;

	public RawDistribution(TranscriptList transcriptList) {
		this.transcriptList = transcriptList;
		rawDistributionMap = new HashMap<String, Map<String, Integer>>();
		retakenDistributionMap = new HashMap<String, Map<String, Integer>>();
		this.setDistributionForCourses();
		this.setDistributionForRetakenCourses();
	}

	public void setDistributionForCourses() {
		Map<String, List<String>> courseGradeMap = transcriptList.getTakenCourseGrades();
		for (String courseName : courseGradeMap.keySet()) {
			Map<String, Integer> courseDistribution = this.createDistributionMap();
			List<String> gradesForCourse = courseGradeMap.get(courseName);
			for(String letterGrade : gradesForCourse) {
				for(String level : courseDistribution.keySet()) {
					Set<String> gradesInLevel = GradeSchema.getGradesForLevel(level);
					if (gradesInLevel.contains(letterGrade)) {
						courseDistribution.put(level, courseDistribution.get(level)+1);
					}
				}
				rawDistributionMap.put(courseName, courseDistribution);
			}
		}
	}

	public void setDistributionForRetakenCourses() {
		Map<String, List<String>> courseGradeMap = transcriptList.getRetakenCourseGrades();
		for (String courseName : courseGradeMap.keySet()) {
			Map<String, Integer> courseDistribution = this.createDistributionMap();
			List<String> gradesForCourse = courseGradeMap.get(courseName);
			for(String letterGrade : gradesForCourse) {
				for(String level : courseDistribution.keySet()) {
					Set<String> gradesInLevel = GradeSchema.getGradesForLevel(level);
					if (gradesInLevel.contains(letterGrade)) {
						courseDistribution.put(level, courseDistribution.get(level)+1);
					}
				}
				retakenDistributionMap.put(courseName, courseDistribution);
			}
		}
	}

	public Map<String, Integer> getCourseDistribution(String courseName){
		return rawDistributionMap.get(courseName);
	}

	public Map<String, Integer> getRetakenCourseDistribution(String courseName){
		return retakenDistributionMap.get(courseName);
	}
	
	public Map<String, Map<String, Integer>> getRawDistributionMap(){
		return rawDistributionMap;
	}

	public Map<String, Map<String, Integer>> getRetakenDistributionMap(){
		return retakenDistributionMap;
	}
}
