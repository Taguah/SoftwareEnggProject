package project.excelSpike;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class AreaDistribution extends Distribution{

	String area;
	Map<String, Integer> areaDistributionMap;
	
	public AreaDistribution(String area, ArrayList<Course> courseList) {
		areaDistributionMap = this.createDistributionMap();
		
/*
		for(Course course : courseList) {
			String letterGrade = course.getGrade().getLetterGrade();
			for(String level : areaDistributionMap) {
			}
		}
*/

	}
}
