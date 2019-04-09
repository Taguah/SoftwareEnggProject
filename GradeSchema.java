package analyzer;
import java.util.HashMap;
import java.util.Set;
import java.util.LinkedHashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GradeSchema {

    private static HashMap<String, Set<String>> gradeMap = new HashMap<>();

    public static void SetGradeSchema(String filepath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if(line.isEmpty()) {
                    continue;
                }
                String[] strArr = line.split("=");
                String[] gradeArr = strArr[1].split(",");

                for(String grade : gradeArr) {
                	addGradeToLevel(strArr[0], grade);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(br != null) {
                try {
                    br.close();
                }
                catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void addGradeToLevel(String level, String grade) {

        if(gradeMap.containsKey(level)) {
            Set<String> grades = gradeMap.get(level);
            grades.add(grade);
        }
        else {
            Set<String> grades = new LinkedHashSet<>();
            grades.add(grade);
            gradeMap.put(level, grades);
        }
    }

    public static Set<String> getGradesForLevel(String level) {
        return gradeMap.get(level);
    }
    
    public static Set<String> getLevels(){
    	return gradeMap.keySet();
    }
    
    public static String checkLevel(String letterGrade) {
    	for(String level : gradeMap.keySet()) {
    		Set<String> letterGrades = gradeMap.get(level);
    		if(letterGrades.contains(letterGrade)) {
    			return level;
    		}
    	}
    	return "Other";
    }
    
    public static Boolean checkIfFailed(Course course) {
		String letterGrade = course.getGrade().getLetterGrade();
		if (GradeSchema.checkLevel(letterGrade).contains("Fail") || GradeSchema.checkLevel(letterGrade).contains("fail")) {
			return true;
		}
		else {
			return false;
		}
	}

}
