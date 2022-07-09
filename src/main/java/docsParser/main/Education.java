package docsParser.main;

import java.io.IOException;
import java.util.ArrayList;


class Edu{
	public String Institution;
	public String course;
	public String marks;
	public String completedYear;
	
	@Override
	public String toString() {
		return "\nInstitution: "+this.Institution
				+"\nCourse: "+this.course
				+"\nYear: "+this.completedYear
				+"\nmarks: "+this.marks;
	}
}

public class Education extends CommonFunctions{
	Education() throws IOException {
		super();
	}

	public ArrayList<Edu> educations = new ArrayList<Edu>();
	public String description;
	
	public void setPrams(ArrayList<String> education) {
		int insCount = 0, CourseCount = 0, marks_count = 0, yearCount=0;
		String institutions = "";
		String courses = "";
		String marks = "";
		String year = "";
		
		String desc = "";
		for(String str: education) {
			str = str.replaceAll("\\s+"," ");
			str = str.replaceAll("[()]","");
			desc += str + "\n\t     ";
			
			//institutions
			institutions = getMatchedString("(\\b([.,a-zA-Z]\\s*)*\\s*(technology|university|college|school|univ|scl))", "[.\t]", str);
			if(institutions.length() > 0) {
				for(String itr: institutions.split("~")) {
					if(itr.length() > 0) {
						if(this.educations.size() <= insCount) {
							educations.add(new Edu());
						}
						this.educations.get(insCount++).Institution = itr;
					}
				}				
			}
			
			//courses
			courses = getMatchedString("(\\b(BE\\.|B\\.E\\.|B\\.E|BS|B\\.S|ME\\.|M\\.E|M\\.E\\.|MS|M\\.S|BTECH|B\\.TECH|MTECH|SSC|HSC|CBSE|ICSE|X|XII|10th|12th|Bachelor|master|secondary education|higher secondary education|grade|Higher School Certificate)([A-Za-z,]?\\s*)*)", "\t", str);
			if(courses.length() > 0) {
				for(String itr: courses.split("~")) {
					if(itr.length() > 0) {
						if(this.educations.size() <= CourseCount) {
							educations.add(new Edu());
						}
						this.educations.get(CourseCount++).course = itr;
						
					}
				}				
			}
			
			//grade
			marks = getMatchedString("((\\d{1,2}\\.\\d{0,2})(/\\d{1,2}\\.\\d{0,2})?( \\w+|%\\D)?)", "\t", str);
			if(marks.length() > 0) {
				for(String itr: marks.split("~")) {
					if(itr.length() > 0) {
						if(this.educations.size() <= marks_count) {
							educations.add(new Edu());
						}
						this.educations.get(marks_count++).marks = itr;
					}
				}				
			}
			
			//year
			year = getMatchedString("\\b(((\\d{2}|january|february|march|april|may"
					+ "|june|july|august|september|november|december|jan|feb|mar|"
					+ "apr|jun|jul|aug|sep|oct|nov|dec)[\\s-/])?\\d{4}(\\s*[ -/–(to)]\\s*(\\d{2}|"
					+ "january|february|march|april|may|june|july|august|september"
					+ "|november|december|jan|feb|mar|apr|jun|jul|aug|sep|"
					+ "oct|nov|dec)?[ -/]?\\d{4})?)\\b", "\t", str);
			if(year.length() > 0) {
				for(String itr: year.split("~")) {
					if(itr.length() > 0) {
						if(this.educations.size() <= yearCount) {
							educations.add(new Edu());
						}
						this.educations.get(yearCount++).completedYear = itr;
						
					}
				}				
			}
			
		}
		this.description = desc;
	}
	
	private String ArrayListToString(ArrayList<Edu> list, String sep) {
		String returnStr = "";
		if(list.size() > 0) {
			for(Edu str: list) {
				returnStr += str.toString()+ sep;			
			}
		}
		return returnStr;
	}
	
	@Override
	public String toString() {
		
		String returnStr = this.ArrayListToString(this.educations, "\n\n");
				
		if(returnStr.length() == 0) {
			return "";
		}
		return "Education"
				+"\n---------"
				+ returnStr
				+"description: "+ this.description;				
	}
}
