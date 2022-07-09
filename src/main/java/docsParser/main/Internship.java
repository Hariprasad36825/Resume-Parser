package docsParser.main;

import java.io.IOException;
import java.util.ArrayList;

class Intern{
	public String CompanyName;
	public String duration;
	
	@Override
	public String toString() {
		return "\nCompany name: " + this.CompanyName
				+"\nDuration: "+this.duration;
	}
}

class Internship extends CommonFunctions{
	public String description;
	public ArrayList<Intern> internships = new ArrayList<Intern>();
	
	Internship() throws IOException {
		super();
	}
	
	public void setPrams(ArrayList<String> internship) {
		int CompanyCount = 0, durationCount = 0;
		
		String company = "", duration="", desc="";
		
		for(String str: internship) {
			str = str.replaceAll("\\s+"," ");
			str = str.replaceAll("[()]","");
			desc += str + "\n\t     ";
			
			//company
			company = getMatchedString("(\\b([A-Za-z&.,]?\\s*)*\\s*(.?inc.?|.?corp.?|.?inc.?|.?ltd.?|institute|services|valley|training center|industries))", "\t", str);
			if(company.length()>0) {
				for(String itr: company.split("~")) {
					if(itr.length() > 0) {
						if(this.internships.size() <= CompanyCount) {
							internships.add(new Intern());
						}
						this.internships.get(CompanyCount++).CompanyName = itr;
						
					}
				}		
			}
			
			//duration
			duration = getMatchedString("((\\d{1,2}|january|february|march|april|may|june|july|"
					+ "august|september|november|december|jan|feb|mar|apr|jun|jul|aug|sep|oct"
					+ "|nov|dec)[ .-/–](\\d{4})((\\s*to\\s*|\\s*[-–] \\s*|\\s*)(present|current|till date|"
					+ "till|((\\d{1,2}|january|february|march|april|may|june|july|august|september|november"
					+ "|december|jan|feb|mar|apr|jun|jul|aug|sep|oct|nov|dec)[ .-/–](\\d{4}))))?)", "[\t]", str);
			if(duration.length() > 0) {
				for(String itr: duration.split("~")) {
					if(itr.length() > 0) {
						if(this.internships.size() <= durationCount) {
							internships.add(new Intern());
						}
						this.internships.get(durationCount++).duration = itr;
						
					}
				}				
			}
		}
		this.description = desc;
	}
	
	private String ArrayListToString(ArrayList<Intern> list, String sep) {
		String returnStr = "";
		if(list.size() > 0) {
			for(Intern str: list) {
				returnStr += str.toString()+ sep;			
			}
		}
		return returnStr;
	}
	
	@Override
	public String toString() {
		String returnStr = this.ArrayListToString(this.internships, "\n\n");
		
		if(returnStr.length() == 0) {
			return "";
		}
		return "Internships"
				+"\n----------"
				+ returnStr
				+"description: "+ this.description;	
	}
}
