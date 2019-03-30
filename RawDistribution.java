import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

public class RawDistribution extends Distribution{
	
	private Map<String, Map<String, Integer>> rawDistributionMap;
	
	public RawDistribution(TranscriptList transcriptList) {
		this.transcriptList = transcriptList;
		rawDistributionMap = new HashMap<String, Map<String, Integer>>();
		this.setDistributionForCourses();
	}
	
	public void setDistributionForCourses() {
		Map<String, List<Course>> allCourses = transcriptList.getAllCoursesByName();
		for (String currentCourse : allCourses.keySet()) {
			Map<String, Integer> courseDistribution = this.createDistributionMap();
			List<Course> allCoursesOfName = allCourses.get(currentCourse);
			for (Course course : allCoursesOfName) {
				String letterGrade = course.getGrade().getLetterGrade();
				for (String level : courseDistribution.keySet()) {
					Set<String> gradesInLevel = GradeSchema.getGradesForLevel(level);
					if (gradesInLevel.contains(letterGrade)) {
						courseDistribution.put(level, courseDistribution.get(level)+1);
					}
				}
				rawDistributionMap.put(currentCourse, courseDistribution);
			}
		}
	}
	
	public void writeExcelRaw() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet rawSheet = workbook.createSheet("Raw Distribution");
		String[] categories = {"course", "other", "fails", "marginal", "meets", "exceeds"};
		Object[] courseNames = rawDistributionMap.keySet().toArray();
		
		int rowInd = 0;
		Row row = rawSheet.createRow(rowInd++);
		for (int colInd = 0; colInd < categories.length; colInd++) {
			Cell cell = row.createCell(colInd);
			cell.setCellValue((String) categories[colInd]);
		}
		for (Map<String, Integer> courseDist : rawDistributionMap.values()) {
			
			Object[] values = courseDist.values().toArray();
			row = rawSheet.createRow(rowInd++);
			for (int colInd = 0; colInd < values.length+1; colInd++) {
				if (colInd == 0) {
					Cell cell = row.createCell(colInd);
					cell.setCellValue((String) courseNames[rowInd - 2]);
				}
				else {
					Cell cell = row.createCell(colInd);
					cell.setCellValue((Integer) values[colInd - 1]);
				}
			}
		}
		try {
			//Relative filepath from this project
			FileOutputStream excelOut = new FileOutputStream(new File("./src/cfg+xlsx/Raw.xlsx"));
			workbook.write(excelOut);
			excelOut.close();
			workbook.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Integer> getCourseDistribution(String courseName){
		return rawDistributionMap.get(courseName);
	}
	
	public Map<String, Map<String, Integer>> getRawDistributionMap(){
		return rawDistributionMap;
	}
}