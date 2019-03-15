package project.excelSpike;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.File;

public class ExcelReader{
	
	public static ArrayList<String> readConfigFile(String fileName){
		ArrayList<String> fileContents = new ArrayList<String>();
		File file = new File(fileName);
		try{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
			    //process the line
			    if (!line.contains("*")){
			    	fileContents.add(line);
			    }
			}
			br.close();
		}
		catch(Exception e) {
			fileContents.add("Incompatible file error");
		}
		return fileContents;
	}
}
	
//		String fileName = "/home1/ugrads/ecamp/inputSample.txt";
	