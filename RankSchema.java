package excelSpike;
/**
 *
 * @author rojan
 */
public class RankSchema {
    
    public static String checkRank(double totalCreditHour){
        
        String year = "";
        
        if(totalCreditHour < 40){
            year = "First year";
        }
        else if(totalCreditHour < 80){
            year = "Second year";
        }
        else if(totalCreditHour < 120){
            year = "Third year";
        }
        else if(totalCreditHour >= 120){
            year = "Fourth year";
        }
        return year;
    }
}