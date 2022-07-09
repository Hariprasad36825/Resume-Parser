package docsParser.main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.aspose.words.*;

public class ResumeParser {
	
	public static String[] sections = new String[] {
			"experience", "education", "skill|languages", "internship", "achievement", "certif", "project", "(details|english|profile|summary|hobb|interests)"
	};
	
	private static String getFile() throws Exception {
		
		JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Docx/pdf", "docx");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        String FileName = "";	
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	FileName = chooser.getSelectedFile().getAbsolutePath();
        }
        
        else {
        	System.out.println("select proper files");
        }
		Document doc = new Document(FileName);
		doc.save(FileName+".txt");
		return FileName+".txt";
		
	}
	
	public static void main(String[] args) throws Exception {
		String file = getFile();
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		
		ArrayList<String> details = new ArrayList<>();
		ArrayList<String> education = new ArrayList<>();
		ArrayList<String> internship = new ArrayList<>();
		ArrayList<String> experience = new ArrayList<>();
		ArrayList<String> achievement = new ArrayList<>();
		ArrayList<String> certification = new ArrayList<>();
		ArrayList<String> project = new ArrayList<>();
		String curSection = sections[7];
		
		String line = br.readLine();
		line = br.readLine();
		line = br.readLine();
		while(line!=null) {
			
			if(line.contains("aspose.com")) {
				line = br.readLine();
				continue;
			}
			line = line.toLowerCase();
			
			for(String section: sections) {
				if(Pattern.compile(section).matcher(line).find()) {
					if(line.contains("project") && line.length() > 20) {
						continue;
					}
					curSection = section;
					break;
				}
			}
			
			switch(curSection) {
			case "(details|english|profile|summary|hobb|interests)":
				details.add(line);
				break;
			case "experience":
				experience.add(line);
				break;
			case "education":
				education.add(line);
				break;
			case "skill|languages":
				details.add(line);
				break;
			case "internship":
				internship.add(line);
				break;
			case "achievement":
				achievement.add(line);
				break;
			case "certif":
				certification.add(line);
				break;
			case "project":
				project.add(line);
				break;
			}
			line = br.readLine();
			
				
		}
		br.close();
		
		PersonalDetails personalDetail = new PersonalDetails();
		personalDetail.setPrams(details);
		
		Education educations = new Education();
		educations.setPrams(education);
		
		Internship internships = new Internship();
		internships.setPrams(internship);
		
		Experience experiences = new Experience();
		experiences.setPrams(experience);
		
		
		System.out.println(personalDetail+"\n\n\n");
		System.out.println(educations+"\n\n\n");
		System.out.println(internships+"\n\n\n");
		System.out.println(experiences+"\n\n\n");
		
		if(achievement.size()>0) {
			System.out.println("Achievement");
			System.out.println("-----------");
		}
		for(String itr: achievement) {
			itr = itr.replaceAll("\\s+"," ");
			itr = itr.replaceAll("[()]|(achievements)","");
			if(itr.length()>0) {
				System.out.println(itr);
			}
		}System.out.println();
		
		if(certification.size()>0) {
			System.out.println("certification");
			System.out.println("-------------");
		}
		for(String itr: certification) {
			itr = itr.replaceAll("\\s+"," ");
			itr = itr.replaceAll("[()]|(certification|certificates|certificate)","");
			if(itr.length()>0) {
				System.out.println(itr);
			}
		}System.out.println();
//		
		if(project.size()>0) {
			System.out.println("Project");
			System.out.println("-------");
		}
		for(String itr: project) {
			itr = itr.replaceAll("(projects|project|mini)","");
			for(String subitr: itr.split("\t")) {
				subitr = subitr.trim();
				if(subitr.length()>0) {
					System.out.println(subitr);
				}
			}
			
		}
	}
	
}
