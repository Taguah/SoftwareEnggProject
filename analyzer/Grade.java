package analyzer;

public class Grade {

	private String letterGrade;
	private double numberGrade;
	
	public Grade(String letterGrade) {
		this.letterGrade = letterGrade;
		if(letterGrade == null)
			letterGrade = "NoGrade";
		this.numberGrade = Grade.convertLetterToNumber(letterGrade);
	}
	
	public Grade(double numberGrade) {
		this.numberGrade = numberGrade;
		this.letterGrade = Grade.convertNumberToLetter(numberGrade);
	}
	
	public void setLetter(String letterGrade) {
		this.letterGrade = letterGrade;
	}
	
	public void setNumber(double numberGrade) {
		this.numberGrade = numberGrade;
	}

	public static double convertLetterToNumber(String letterGrade) {
		double numberGrade;
		switch(letterGrade) {
			case "A+":	numberGrade = 4.3;
						break;
			case "A":	numberGrade = 4.0;
						break;
			case "A-":	numberGrade = 3.7;
						break;
			case "B+":	numberGrade = 3.3;
						break;
			case "B":	numberGrade = 3.0;
						break;
			case "B-":	numberGrade = 2.7;
						break;
			case "C+":	numberGrade = 2.3;
						break;
			case "C":	numberGrade = 2.0;
						break;
			case "C-":	numberGrade = 1.7;
						break;
			case "D+":	numberGrade = 1.3;
						break;
			case "D":	numberGrade = 1.0;
						break;
			case "D-":	numberGrade = 0.7;
						break;
			default: 	numberGrade = 0;
		}
		return numberGrade;
	}
	
	public static String convertNumberToLetter(double number){
		if (number >= 4.3)
			return "A+";
		else if (number >= 4.0)
			return "A";
		else if (number >= 3.7)
			return "A-";
		else if (number >= 3.3)
			return "B+";
		else if (number >= 3.0)
			return "B";
		else if (number >= 2.7)
			return "B-";
		else if (number >= 2.3)
			return "C+";
		else if (number >= 2.0)
			return "C";
		else if (number >= 1.7)
			return "C-";
		else if (number >= 1.3)
			return "D+";
		else if (number >= 1.0)
			return "D";
		else if (number >= 0.7)
			return "D-";
		else {
			return "F";
		}
	}
	
	public double getNumberGrade() {
		return numberGrade;
	}
	
	public String getLetterGrade() {
		return letterGrade;
	}
	
}
