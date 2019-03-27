package project.excelSpike;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class TranscriptList {

	ArrayList<Transcript> transcriptList;
	
    public TranscriptList(ArrayList<Transcript> transcripts) {
    	transcriptList = transcripts;
    }
    
    public Set<String> getAllCourseNames() {
    	HashSet<String> courseNames = new HashSet<String>();
    	for (Transcript transcript : transcriptList) {
    		ArrayList<Course> courseList = transcript.getCourses();
    		for (Course course : courseList) {
    			courseNames.add(course.getCourseID());
    		}
    	}
    	return courseNames;
    }

    public Map<String, List<String>> getAllAveragesByArea(){
    	List<String> areas = AreaSchema.getAllAreas();
    	Map<String, List<String>> areaMap = new HashMap<String, List<String>>();
    	for (String area : areas) {
    		List<String> averageGrades = new ArrayList<String>();
    		for (Transcript transcript : transcriptList) {
    			double averageNumberGrade = transcript.getAverageForArea(area);
    			if (averageNumberGrade >= 0) {
    				String averageLetterGrade = Grade.convertNumberToLetter(averageNumberGrade);
    				averageGrades.add(averageLetterGrade);
    			}
    		}
    		areaMap.put(area, averageGrades);
    	}
    	return areaMap;
    }
    
    public Map<String, List<Course>> getAllCoursesByEquivalentName(){
    	Map<String, List<Course>> courseNameMap = new HashMap<String, List<Course>>();
    	for (Transcript transcript : transcriptList) {
    		List<Course> courseList = transcript.getCourses();
    		for (Course course : courseList) {
    			String courseName = course.getCourseID();
    			courseName = EquivalencySchema.getEquivalent(courseName);
    			List<Course> currentCourseList;
    			if (courseNameMap.containsKey(courseName)) {
    				currentCourseList = courseNameMap.get(courseName);
    			}
    			else {
    				currentCourseList = new ArrayList<Course>();
    			}
    			currentCourseList.add(course);
    			courseNameMap.put(courseName, currentCourseList);
    		}
    	}
    	return courseNameMap;
    }

    public List<Course> getAllCoursesInArea(String area){
    	List<Course> courseList = new ArrayList<Course>();
    	for (Transcript transcript : transcriptList) {
			List<Course> transcriptCourses = transcript.getCoursesByArea(area);
			courseList.addAll(transcriptCourses);
		}
    	return courseList;
    }
    
    public RawDistribution createRawDistribution() {
    	RawDistribution rawDistribution = new RawDistribution(this);
    	return rawDistribution;
    }

    public Map<String, AreaDistribution> createAreaDistributionMap(){
    	Map<String, AreaDistribution> areaDistributionMap = new HashMap<String, AreaDistribution>();
    	List<String> areas = AreaSchema.getAllAreas();
    	for (String area : areas) {
    		AreaDistribution areaDistribution = createAreaDistribution(area);
    		areaDistributionMap.put(area, areaDistribution);
    	}
    	return areaDistributionMap;
    }

    public AreaDistribution createAreaDistribution(String area) {
    	AreaDistribution areaDistribution = new AreaDistribution(this);
    	return areaDistribution;
    }
    

}
