package project.excelSpike;

import java.util.ArrayList;
import java.util.HashSet;
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
    
    public Map<String, ArrayList<Course>> getAllCoursesByArea(){
    	ArrayList<String> areas = AreaSchema.getAllAreas();
    	Map<String, ArrayList<Course>> areaMap = new HashMap<String, ArrayList<Course>>();
    	for (String area : areas) {
    		ArrayList<Course> areaCourses = this.getAllCoursesInArea(area);
    		areaMap.put(area, areaCourses);
    	}
    	return areaMap;
    }
    
    public Map<String, ArrayList<Course>> getAllCoursesByName(){
    	Map<String, ArrayList<Course>> courseNameMap = new HashMap<String, ArrayList<Course>>();
    	for (Transcript transcript : transcriptList) {
    		ArrayList<Course> courseList = transcript.getCourses();
    		for (Course course : courseList) {
    			String courseName = course.getCourseID();
    			ArrayList<Course> courselist = new ArrayList<Course>();;
    			if (!courseNameMap.containsKey(courseName)) {
    				courselist = courseNameMap.get(courseName);
    			}
    			courseList.add(course);
    			courseNameMap.put(courseName, courseList);
    		}
    	}
    	return courseNameMap;
    }
    
    public ArrayList<Course> getAllCoursesInArea(String area){
    	ArrayList<Course> courseList = new ArrayList<Course>();
    	for (Transcript transcript : transcriptList) {
			ArrayList<Course> transcriptCourses = transcript.getCoursesByArea(area);
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
    	ArrayList<String> areas = AreaSchema.getAllAreas();
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
