package hogwarts.school.study;

import hogwarts.school.House;
import android.util.Pair;

public abstract class Question {
	
	boolean valid = true;
	boolean answered = false;
	Pair<Subject,Topic> pair = null;
	
	public abstract String getId();
	
	protected Question(Subject subject, Topic topic){
		pair = new Pair<Subject,Topic>(subject,topic);
	}
	
	public synchronized boolean isValid(){
		return valid && !answered;
	}
	
	public synchronized void neverMind(){
		valid = false;
	}

	public synchronized void answered(){
		answered = true;
	}
	
	public void ask(House house){
		valid = true;
		answered = false;
		house.ask(this);
	}
	
	public void ask(){
		ask(House.gryffindor);
	}
	
	public Pair<Subject,Topic> getSubject(){
		return pair;
	}

}
