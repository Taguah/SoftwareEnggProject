import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class AreaDistribution extends Distribution{

	private Map<String, Map<String, Integer>> areaDistributionMap;
	
	public AreaDistribution(TranscriptList transcriptList) {
		this.transcriptList = transcriptList;
		
		areaDistributionMap = new HashMap<String, Map<String, Integer>>();
		this.setDistributionForAreas();	
	}

	public void setDistributionForAreas() {
		Map<String, List<String>> areaAverages = transcriptList.getAllAveragesByArea();
		for (String area : areaAverages.keySet()){
			List<String> averageGrades = areaAverages.get(area);
			Map<String, Integer> areaDistribution = this.createDistributionMap();
			Set<String> levels = GradeSchema.getLevels();
			for (String grade : averageGrades) {
				for (String level : levels) {
					Set<String> gradesInLevel = GradeSchema.getGradesForLevel(level);
					if (gradesInLevel.contains(grade)) {
						Integer number = areaDistribution.get(level);
						areaDistribution.put(level, number+1);
					}
				}
			}
			areaDistributionMap.put(area, areaDistribution);
		}
	}
	
	public void writeExcelArea() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet areaSheet = workbook.createSheet("Area Distribution");
		String[] categories = {"area", "other", "fails", "marginal", "meets", "exceeds"};
		Object[] areaNames = areaDistributionMap.keySet().toArray();
		
		int rowInd = 0;
		Row row = areaSheet.createRow(rowInd++);
		for (int colInd = 0; colInd < categories.length; colInd++) {
			Cell cell = row.createCell(colInd);
			cell.setCellValue((String) categories[colInd]);
		}
		for (Map<String, Integer> areaDist : areaDistributionMap.values()) {
			
			Object[] values = areaDist.values().toArray();
			row = areaSheet.createRow(rowInd++);
			for (int colInd = 0; colInd < values.length+1; colInd++) {
				if (colInd == 0) {
					Cell cell = row.createCell(colInd);
					cell.setCellValue((String) areaNames[rowInd - 2]);
				}
				else {
					Cell cell = row.createCell(colInd);
					cell.setCellValue((Integer) values[colInd - 1]);
				}
			}
		}
		try {
			//Relative filepath from this project
			FileOutputStream excelOut = new FileOutputStream(new File("./src/cfg+xlsx/Area.xlsx"));
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

	public Map<String, Integer> getDistributionForArea(String areaName) {
		return areaDistributionMap.get(areaName);
	}
}
