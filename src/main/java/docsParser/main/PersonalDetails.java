package docsParser.main;

import java.io.IOException;
import java.util.ArrayList;



public class PersonalDetails extends CommonFunctions{
	PersonalDetails() throws IOException {
		super();
	}

	String Name;
	String dob;
	String Position;
	ArrayList<String> emails = new ArrayList<String>();
	ArrayList<String> phone = new ArrayList<String>();
	String description;
	ArrayList<String> links = new ArrayList<String>();
	String Place;
	ArrayList<String> SoftSkills = new ArrayList<String>();
	
	public void setPrams(ArrayList<String> details) throws IOException {
		String name="";
		String dob="";
		String email = "";
		String phone = "";
		String skills = "";
		String urls = "";
		
		String tmp = "";
		for(String s: details) {
			s = s.replaceAll("\\s+"," ");
			s = s.replaceAll("[()]","");
			s = s.trim();
			
			if(s.length() == 0) {
				continue;
			}
			
//			name
			tmp = this.findNames(s).strip();
			
			if(tmp.length() > 0 && !name.contains(tmp)) {
				if(tmp.contains(name.strip())) {
					name = tmp+" ";
				}
				else {
					name +=  tmp + " ";
				}
				
			}
			
			
//			dob
			if(dob.length() == 0) {
				dob = this.fetchDate(s);
			}
			
//			email
			email = this.fetchEmail(s);
			if(email.length() > 0) {
				for(String itr: email.split(" ")) {
					this.emails.add(itr);
				}				
			}
			
//			Phone
			phone = this.fetchPhone(s);
			if(phone.length() > 0) {
				for(String itr: phone.split("sep")) {
					if(itr.length() > 0) {
						this.phone.add(itr);
					}
				}				
			}
			
//			skills
			skills = this.fetchSoftSkills(s);
			if(skills.length() > 0) {
				for(String itr: skills.split("sep")) {
					if(itr.length() > 0 && !this.SoftSkills.contains(itr)) {
						this.SoftSkills.add(itr);
					}
				}				
			}
			
//			urls
			urls = this.fetchUrl(s);
			if(urls.length() > 0) {
				for(String itr: urls.split("\t")) {
					if(itr.length() > 0) {
						this.links.add(itr);
					}
				}				
			}
						
		}
		
		this.Name = name;
		this.dob = dob;
		this.description = ArrayListToString(details, "\n\t     ");
		
	}
	
	
	private String ArrayListToString(ArrayList<String> list, String sep) {
		String returnStr = "";
		for(String str: list) {
			str = str.replaceAll("\\s+"," ");
			str = str.replaceAll("[()]","");
			str = str.strip();
			
			if(str.length()>0) {
				returnStr += str+ sep;
			}
			
		}
		return returnStr;
	}
	
	@Override
	public String toString() {
		return "Personal Details"
				+ "\n----------------"
				+ "\nName: "+this.Name
				+ "\nDOB: "+this.dob
				+ "\nEmail: "+this.ArrayListToString(this.emails, ", ")
				+ "\nPhone: "+this.ArrayListToString(this.phone, ", ")
				+ "\nPosition: "+this.Position
				+ "\nplace: "+this.Place
				+ "\ndescription: "+this.description
				+ "\nSoft skills: "+this.ArrayListToString(this.SoftSkills, ", ")
				+ "\nlinks: "+this.ArrayListToString(this.links, ", ");
	}
	
}