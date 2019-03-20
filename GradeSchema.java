package project.excelSpike;

import java.util.HashMap;
import java.util.Set;
import java.util.LinkedHashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GradeSchema {

/*
    static enum Level {
        Exceeds,
        Meets,
        Marginal,
        Fails,
        Other
    }
*/

/*
	private static ArrayList<String> levels = new ArrayList<String>() {
		{
			add("Exceeds");
			add("Meets");
			add("Marginal");
			add("Fails");
			add("Other");
		}
	};
*/

    private static HashMap<String, Set<String>> gradeMap = new HashMap<>();

    /* Creating an object of GradeSchema requires the filepath to the grade schema text file,
    and the constructor will read the file line by line to construct a map of levels to their
    respective grades.
    */
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


}