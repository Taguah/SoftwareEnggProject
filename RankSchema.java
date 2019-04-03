/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rojan
 */
public class RankSchema {
    
    private static Map<String, Integer> NumberOfStudent = new HashMap<String, Integer>();;
    
    public static String checkRank(double totalCreditHour){
        
        String year = "";
        
        if(totalCreditHour < 40){
            year = "Fist year";
        }
        if(totalCreditHour < 80){
            year = "Second year";
        }
        if(totalCreditHour < 120){
            year = "Third year";
        }
        if(totalCreditHour >= 120){
            year = "Forth year";
        }
        NumberOfStudent.put(year, NumberOfStudent.get(year)+1);
        return year;
    }
    
    public Map<String, Integer> getNumberOfStudent(){
        return NumberOfStudent;
    }
}
