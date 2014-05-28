package hogwarts.school;

import hogwarts.school.staff.MacGonagall;

public abstract class Gryffindor extends House{
	public void onCreate(){
		this.appointHead(new MacGonagall());
	}


}
