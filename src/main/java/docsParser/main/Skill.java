package docsParser.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Skill extends CommonFunctions{
	
	Skill() throws IOException {
		super();
	}

	public HashSet<String> skills = new HashSet<String>();
	String desc = "";
	public void setPrams(ArrayList<String> skill) throws IOException {
		String skillsStr = "";
		String dir = System.getProperty("user.dir");
		String regex = getRegex(dir+"\\assets\\text\\skillRegex.txt");
		
		for(String str: skill) {
			str = str.replaceAll("\\s+"," ");
			str = str.replaceAll("[()]","");
//			System.out.println(str);
			desc += str + "\n\t     ";
			
			skillsStr = getMatchedString(regex, "[\t]", str);
			if(skillsStr.length() > 0) {
				for(String it: skillsStr.split("~")) {
					this.skills.add(it);
				}
			}
		}
	}
	
	private String hashsetToStr(HashSet<String> set) {
		String returnStr = "";
		for(String s: set) {
			returnStr += s+", ";
		}
		return returnStr;
	}
	
	@Override
	public String toString() {
		return "Technical Skills"
				+ "\n---------------\n"
				+ "skills: "+hashsetToStr(this.skills)
				+"\ndescription: "+this.desc;
	}

}
