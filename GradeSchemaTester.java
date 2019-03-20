package project.excelSpike;

public class GradeSchemaTester {

    public static void main(String[] args) {
        GradeSchema.SetGradeSchema("/mnt/c/Users/Jenny/Google Drive/Winter2019/CS2043/Project Info/level-schema.txt");

        for(String level : GradeSchema.getLevels()) {
            System.out.println(GradeSchema.getGradesForLevel(level));
        }
    }
}
