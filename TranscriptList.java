package project.excelSpike;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class TranscriptList {

	List<Transcript> transcriptList;
	Set<String> allCourseNames;
	
    public TranscriptList(List<Transcript> transcripts) {
    	transcriptList = transcripts;
    	setAllCourseNames();
    }

    public void setAllCourseNames() {
    	Set<String> courseNames = new HashSet<String>();
    	for (Transcript currentTranscript : transcriptList) {
    		Set<String> passedCourseNames = currentTranscript.getPassedCourseMap().keySet();
    		for(String courseName : passedCourseNames) {
    			courseNames.add(courseName);
    		}
    		Set<String> failedCourseNames = currentTranscript.getPassedCourseMap().keySet();
    		for(String courseName : failedCourseNames) {
    			courseNames.add(courseName);
    		}
    	}
    	allCourseNames = courseNames;
    }

    public Set<String> getAllCourseNames() {
    	return allCourseNames;
    }
    
    public Map<String, List<String>> getAllAveragesByArea(){
    	List<String> areas = AreaSchema.getAllAreas();
    	Map<String, List<String>> areaMap = new HashMap<String, List<String>>();
    	for (String area : areas) {
    		List<String> averagesForArea = new ArrayList<String>();
    		for (Transcript transcript : transcriptList) {
    			Map<String, String> averagesForTranscript = transcript.getAverageGradesByArea();
    			if(averagesForTranscript.containsKey(area)) {
    				averagesForArea.add(averagesForTranscript.get(area));
    			}
    		}
    		areaMap.put(area, averagesForArea);
    	}
    	return areaMap;
    }

    public Map<String, List<String>> getTakenCourseGrades(){
    	Map<String, List<String>> takenCourseGrades = new HashMap<String, List<String>>();
    	for (String courseName : allCourseNames) {
    		List<String> letterGrades = new ArrayList<String>();
    		for (Transcript transcript : transcriptList) {
    			Map<String, Course> passedCourses = transcript.getPassedCourseMap();
        		if(passedCourses.containsKey(courseName)) {
        			letterGrades.add(passedCourses.get(courseName).getGrade().getLetterGrade());
        		}
    			Map<String, Course> failedCourses = transcript.getFailedCourseMap();
    			if(failedCourses.containsKey(courseName)) {
        			letterGrades.add(failedCourses.get(courseName).getGrade().getLetterGrade());
        		}
    		}
    		takenCourseGrades.put(courseName, letterGrades);
    	}
    	return takenCourseGrades;
    }
    
    public Map<String, List<String>> getRetakenCourseGrades(){
    	Map<String, List<String>> retakenCourseGrades = new HashMap<String, List<String>>();
    	for (String courseName : allCourseNames) {
    		List<String> letterGrades = new ArrayList<String>();
    		for (Transcript transcript : transcriptList) {
    			Map<String, List<Course>> retakenCourses = transcript.getRetakenCourseMap();
        		if(retakenCourses.containsKey(courseName)) {
        			List<Course> retakenCourseList = retakenCourses.get(courseName);
        			for(Course course : retakenCourseList) {
        				letterGrades.add(course.getGrade().getLetterGrade());
        			}
        		}
    		}
    		retakenCourseGrades.put(courseName, letterGrades);
    	}
    	return retakenCourseGrades;
    	
    }
    
/*
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
*/
}