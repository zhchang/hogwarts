package hogwarts.school;

import hogwarts.school.resource.Facility;
import hogwarts.school.staff.Head;
import hogwarts.school.staff.MacGonagall;
import hogwarts.school.staff.Teacher;
import hogwarts.school.study.Question;


public class House {
	private Head head;
	
	public static final House gryffindor;
	public static final House hufflepuff;
	public static final House ravenclaw;
	public static final  House slytherin;
	
	static{
		gryffindor = new House(new MacGonagall());
		hufflepuff = new House(new MacGonagall());
		ravenclaw = new House(new MacGonagall());
		slytherin = new House(new MacGonagall());
	}
	
	private House(Head head){
		this.head = head;
	}
	
	public void appointHeadMaster(Head head){
		this.head = head;
	}
	
	public Facility bookFacility(){
		return head.bookFacility();
	}
	
	public void releaseFacility(Facility facility){
		head.releaseFacility(facility);
	}
	
	public void ask(Question question){
		this.head.ask(question);
	}
	
	public void refer(Question question, Teacher teacher){
		teacher.answer(question);
	}
	
	public void assign(Teacher teacher){
		teacher.setHouse(this);
		this.head.introduce(teacher);
	}

}
