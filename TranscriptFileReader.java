package analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TranscriptFileReader {

    private static Set<String> gradeSet = new HashSet<>(Arrays.asList(new String[] {"A+", "A", "A-", "B+", "B", 
            "B-", "C+", "C", "C-", "D", "F",
            "AUD", "CTN", "CR", "NCR", 
            "DNW", "INC", "NR", "W"} ));

    /*
    Reads each transcript text file from a specified folder path and creates a 
    transcript object from each file. 
    Returns a list of all the transcripts within a folder.
    */
    public static TranscriptList readTranscriptsFromFolder(String folderPath) {

        ArrayList<Transcript> transcriptList = new ArrayList<>();

        try {
            File folder = new File(folderPath);

            for(File file : folder.listFiles()) {
                String filepath;

                if(file.isFile()) {
                    filepath = file.getAbsolutePath();
                    Transcript transcript = readTranscriptFromFile(filepath);
                    transcriptList.add(transcript);
                }
            }
            TranscriptList returnList = new TranscriptList(transcriptList);
            return returnList;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    Reads line by line from a transcript text file, and splits each line on
    the transcript into an array of strings, which represent the different
    attributes in a course. 
    Returns a transcript object which contatins a list of all the courses 
    listed in one transcript.
    */
    public static Transcript readTranscriptFromFile(String filePath) {

        ArrayList<Course> courses = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if(line.isEmpty()) {
                    continue;
                }
                String[] strArr = line.split("\\s+");
                int n = strArr.length;
                String grade = null;
                if(gradeSet.contains(strArr[n-3])) {
                    grade = strArr[n-3];
                }
                //lines 85-96 may be unnecessary depending on transcripts (# and BBA)
                String semester = strArr[n-1];
                double creditHours = 0;
                if (!strArr[n-1].contains("/")) {
                    semester = strArr[n-2];
                    creditHours = Double.parseDouble(strArr[n-3]);
                    if(gradeSet.contains(strArr[n-4])) {
                        grade = strArr[n-4];
                    }
                }
                else {
                    creditHours = Double.parseDouble(strArr[n-2]);
                }
                Course course = new Course(strArr[0], strArr[1], grade, creditHours, semester);
                courses.add(course);
            }
            Transcript transcript = new Transcript(courses);
            return transcript;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
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
}