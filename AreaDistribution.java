package project.excelSpike;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;


public class AreaDistribution extends Distribution{

	private ArrayList<String> areas;
	private Map<String, Map<String, Integer>> areaDistributionMap;
	private TranscriptList transcriptList;
	
	public AreaDistribution(TranscriptList transcriptList) {
		this.transcriptList = transcriptList;
		
		areaDistributionMap = new HashMap<String, Map<String, Integer>>();
		areas = AreaSchema.getAllAreas();
		for (String area : areas){
			this.setDistributionForArea(area);	
		}
	}

	public void setDistributionForArea(String areaName) {
		//This method creates a list with "Exceeds", "Meets", etc. all set to 0
		Map<String, Integer> areaDistribution = this.createDistributionMap();
		//This gets a list of individual courses in the area
		ArrayList<Course> coursesInArea = transcriptList.getAllCoursesInArea(areaName);
		//This converts them to a list of letter grades
		ArrayList<String> letterGrades = this.getLetterGrades(coursesInArea);
		//Loop through the letter grades
		for (String letterGrade : letterGrades){
			Set<String> levels = GradeSchema.getLevels();
		//Loop through the levels	
			for (String level : levels){
				Set<String> gradesInLevel = GradeSchema.getGradesForLevel(level);
				if (gradesInLevel.contains(letterGrade)){
					Integer number = areaDistribution.get(level.toString());
					areaDistribution.put(level, number+1);
				}
			}
		}
		areaDistributionMap.put(areaName, areaDistribution);
	}
	
	public Map<String, Integer> getDistributionForArea(String areaName) {
		return areaDistributionMap.get(areaName);
	}
}
