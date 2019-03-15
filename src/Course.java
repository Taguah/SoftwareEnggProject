public class Course {
    private String courseID;
    private String section;
    private String grade;
    private double creditHours;
    private String term;

    public Course(String courseID, String section, String grade, double creditHours, String term) {
        this.courseID = courseID;
        this.section = section;
        this.grade = grade;
        this.creditHours = creditHours;
        this.term = term;
    }

    public String getCourseID() {
        return courseID;
    }

    public String toString() {
        return courseID + " " + section + " " +  grade + " " + creditHours + " " + term;
    }
}