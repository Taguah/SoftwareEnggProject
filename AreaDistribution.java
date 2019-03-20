package project.excelSpike;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class AreaDistribution extends Distribution{

	ArrayList<String> areas;
	Map<String, Map<Level, Integer>> areaDistributionMap;
	
	public AreaDistribution(String gradeSchemaFile) {
		Map<String, Map<Level, Integer>> areaDistributionMap = new HashMap<String, Map<Level, Integer>>();
		areas = AreaSchema.getAllAreas();
		GradeSchema gradeSchema = new GradeSchema(gradeSchemaFile);
		for (String area : areas){
			this.setDistributionForArea(area);	
		}
	}

	public void setDistributionForArea(String areaName) {
		Map<String, Integer> areaDistribution = this.createDistributionMap();
		ArrayList<Course> coursesOfName = courseNameMap.get(courseName);
		ArrayList<String> letterGrades = this.getLetterGrades(coursesOfName);
		for (String letterGrade : letterGrades){
			Set<Level> levels = gradeSchema.getLevels();
			for (Level level : levels){
				Set<String> gradesInLevel = gradeSchema.getGradesForLevel();
				if (gradesInLevel.contains(letterGrade){
					Integer number = courseDistribution.get(level.toString())
					courseDistribution.set(level.toString(), number++);
				}
			}
		}
		areaDistributionMap.put(areaName, areaDistribution);
	}
}
