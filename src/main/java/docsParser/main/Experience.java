package docsParser.main;

import java.io.IOException;
import java.util.ArrayList;

class Exp{
	public String CompanyName;
	public String duration;
	
	@Override
	public String toString() {
		return "\nCompany name: " + this.CompanyName
				+"\nDuration: "+this.duration;
	}
}

class Experience extends CommonFunctions {
	public ArrayList<Exp> experiences = new ArrayList<Exp>();
	public String description;
	
	Experience() throws IOException {
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
			company = getMatchedString("(\\b([A-Za-z&.,]?\\s*)*\\s*(.?inc.?|.?corp.?|.?inc.?|.?ltd.?|institute|services\b|valley|training center|industries|center,|financial))\\b|(((\\w\\s*)*){1,3},\\s*(\\w\\s*){1,2})", "\t", str);
			if(company.length()>0) {
				for(String itr: company.split("~")) {
					if(itr.length() > 0) {
						if(this.experiences.size() <= CompanyCount) {
							experiences.add(new Exp());
						}	
						this.experiences.get(CompanyCount++).CompanyName = itr;
						
					}
				}		
			}
			
			//duration
			duration = getMatchedString("((\\d{1,2}|january|february|march|april|may|june|july|august|september|november|december|jan|feb|mar|apr|jun|jul|aug|sep|oct|nov|dec)[ .-/–](\\d{4})((\\s*to\\s*|\\s*[-–] \\s*|\\s*)(present|current|till date|till|((\\d{1,2}|january|february|march|april|may|june|july|august|september|november|december|jan|feb|mar|apr|jun|jul|aug|sep|oct|nov|dec)[ .-/–](\\d{4}))))?)", "[\t]", str);
			if(duration.length() > 0) {
				for(String itr: duration.split("~")) {
					if(itr.length() > 0) {
						if(this.experiences.size() <= durationCount) {
							experiences.add(new Exp());
						}
						this.experiences.get(durationCount++).duration = itr;
						
					}
				}				
			}
		}
		this.description = desc;
	}
	
	
	private String ArrayListToString(ArrayList<Exp> list, String sep) {
		String returnStr = "";
		if(list.size() > 0) {
			for(Exp str: list) {
				returnStr += str.toString()+ sep;			
			}
		}
		return returnStr;
	}
	
	@Override
	public String toString() {
		String returnStr = this.ArrayListToString(this.experiences, "\n\n");
		
		if(returnStr.length() == 0) {
			return "";
		}
		return "Experience"
				+"\n----------"
				+ returnStr
				+"description: "+ this.description;	
	}
	
	
}