package docsParser.main;

import java.io.IOException;
import java.util.ArrayList;

class Item{
	String name;
	String duration;
	
	@Override
	public String toString() {
		return "\nname: "+ name
				+"\nduration: "+duration;
	}
}

public class Project extends CommonFunctions {
	ArrayList<Item> projects = new ArrayList<Item>();
	String desc="";
	
	Project() throws IOException {
		super();
	}

}
