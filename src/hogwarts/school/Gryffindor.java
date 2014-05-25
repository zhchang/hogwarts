package hogwarts.school;

import hogwarts.school.staff.MacGonagall;

public class Gryffindor extends House{
	public void onCreate(){
		this.appointHead(new MacGonagall());
	}


}
