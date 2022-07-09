package docsParser.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class CommonFunctions {
	InputStream inputStreamTokenizer;
	TokenizerModel TokenModel;
	TokenizerME tokenizer;
	public static final String dir = System.getProperty("user.dir");
	CommonFunctions() throws IOException{
		this.inputStreamTokenizer = new FileInputStream(dir+"\\assets\\en-token.bin");		
		this.TokenModel = new TokenizerModel(inputStreamTokenizer);		
		this.tokenizer = new TokenizerME(TokenModel); 
	}
	
	
	
	public String getRegex(String fileName) throws IOException{
		BufferedReader NamesStream = new BufferedReader(new FileReader(fileName));
		String regex = NamesStream.readLine();
		NamesStream.close();
		return regex;
	}
	
	private String getNameRegex() throws IOException{
		BufferedReader NamesStream = new BufferedReader(new FileReader(dir+"\\assets\\text\\nameRegex.txt"));
		String regex = NamesStream.readLine();
		NamesStream.close();
		return regex;
	}
	
	public String getMatchedString(String regex, String splitstr, String str) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		String returnStr = "";
		Matcher m;
		for(String s: str.split(splitstr)) {
			s = s.strip();
			m = pattern.matcher(s);
			
			while(m.find()){
				if(m.group(1)!= null) {
					returnStr += m.group(1).strip()+"~";
				}
				
			}
		}
		return returnStr;
	}
	
	public String findNames(String str) throws IOException {
		System.out.println(str);
		String[] tokens = tokenizer.tokenize(str);
		String Name = "";
		Pattern pattern = Pattern.compile(getNameRegex(), Pattern.CASE_INSENSITIVE);
		Matcher matcher;
		for(String s: tokens) {
			matcher = pattern.matcher(s);
			while(matcher.find()) {
				if(!Name.contains(matcher.group(1).strip())) {
					Name += matcher.group(1).strip() + " ";
				}				
			}
		}
		return Name.strip();
	}
	
	public String fetchDate(String str) {
		
		Matcher m = Pattern.compile("((\\d{1,2})([ ./-]|th |nd |st )(\\d{1,2}|january|february|march|april|may|june|july|august|september|november|december|jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)([ .,/-]+)(\\d{4}))|((\\d{2,4})([ ./-]+)(\\d{1,2}|january|february|march|april|may|june|july|august|september|november|december|jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)(\\d{1,2})?)", Pattern.CASE_INSENSITIVE).matcher(str);
        while (m.find()) {
            if(m.group(1)!=null) {
            	return m.group(1);
            }
        	
        }
		return "";
	}
	
	public String fetchEmail(String str) {
		Pattern validEmailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		String emails = "";
		Matcher m;
		for(String s: str.split(" ")) {
			s = s.strip();
			m = validEmailRegex.matcher(s);
			if(m.find()){
				emails += s+" ";
			}
		}
		return emails;
	}
	
	public String fetchPhone(String str) {
		Pattern validPhoneRegex = Pattern.compile("((\\+\\d{1,3}[-\\s+])?[\\s+(]?\\d{10}[\\W]?|(?:\\d{3,4}[- ]){2}\\d{4}|\\(\\d{3}\\)\\d{3}[- ]?\\d{4})");
		String phones = "";
		Matcher m;
		for(String s: str.split("\t")) {
			s = s.strip();
			m = validPhoneRegex.matcher(s);
			if(m.find()){
				phones += m.group(1)+"sep";
			}
		}
		return phones;
	}
	
	public String fetchSoftSkills(String str) throws IOException {
		String regex = getRegex(dir+"\\assets\\text\\skillRegex.txt");
		Pattern SoftSkillsRegex = Pattern.compile("(Creativity|Interpersonal|Critical Thinking|Problem Solving|Public Speaking|Customer Service|Teamwork Skills|Collaboration|descision maker|problem solver|creative|innovative|focused"
				+ "Accounting|Active Listening|Adaptability|Negotiation|Conflict Resolution|Empathy|Customer Service|Decision Making|project Management|Leaderships|Organization|Language|Administrative|"+regex+")", Pattern.CASE_INSENSITIVE);
		String skills = "";
		Matcher m;
		for(String s: str.split("\t")) {
			s = s.strip();
			m = SoftSkillsRegex.matcher(s);
			while(m.find()){
				if(!skills.contains(m.group(1))) {
					skills += m.group(1)+"sep";
				}
				
			}
		}
		return skills;
	}
	
	public String fetchUrl(String str) {
		Pattern UrlRegex = Pattern.compile("((http|ftp|https):\\/\\/)?([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])", Pattern.CASE_INSENSITIVE);
		String urls = "";
		Matcher m;
		for(String s: str.split("[ ,]+")) {
			s = s.strip();
			m = UrlRegex.matcher(s);
			if(m.find()){
				urls += s+"\t";
			}
		}
		return urls;
	}
	
}
