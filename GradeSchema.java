package project.excelSpike;

import java.util.HashMap;
import java.util.Set;
import java.util.LinkedHashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GradeSchema {

    static enum Level {
        Exceeds,
        Meets,
        Marginal,
        Fails,
        Other
    }

    private HashMap<Level, Set<String>> gradeMap = new HashMap<>();

    /* Creating an object of GradeSchema requires the filepath to the grade schema text file,
    and the constructor will read the file line by line to construct a map of levels to their
    respective grades.
    */
    public GradeSchema(String filepath) {

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
                    addGradeToLevel(Level.valueOf(strArr[0]), grade);
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

    public void addGradeToLevel(Level level, String grade) {

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

    public Set<String> getGradesForLevel(Level level) {
        return gradeMap.get(level);
    }
    
    public Set<Level> getLevels(){
    	return gradeMap.keySet();
    }
    
/* To be deleted before submission
*/
    public static void main(String[] args) {
        GradeSchema gradeschema = new GradeSchema("/mnt/c/Users/Jenny/Google Drive/Winter2019/CS2043/Project Info/level-schema.txt");

        for(Level level : Level.values()) {
            System.out.println(gradeschema.getGradesForLevel(level));
        }
    }
}
