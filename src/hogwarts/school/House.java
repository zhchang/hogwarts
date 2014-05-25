package hogwarts.school;

import hogwarts.school.resource.Office;
import hogwarts.school.staff.Head;
import hogwarts.school.staff.MacGonagall;
import hogwarts.school.staff.Teacher;
import hogwarts.school.study.Question;
import hogwarts.school.study.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class House extends Service {
	private Head head;

	Map<String, List<Teacher>> subjectMap = new HashMap<String, List<Teacher>>();
	List<Office> offices = new ArrayList<Office>();
	private static final int MAX_OFFICES = 5;
	

	public void onCreate(){
	}


	protected void onHandleIntent(Intent intent){
		Bundle question = intent.getExtras().getBundle("question");
		if(null != question){

		}
	}

	public IBinder onBind(Intent intent){
		return null;
	}


	public void appointHead(Head head) {
		this.head = head;
	}

	public Head getHead() {
		return this.head;
	}

	public Office getAnOffice() {
		Office chosen = null;
		int min = Integer.MAX_VALUE;
		synchronized (offices) {
			for(Office office : offices){
				if(office.getUseCount() < min){
					min = office.getUseCount();
					chosen  = office;
				}
			}
			if(null == chosen || (min >0 && offices.size() < MAX_OFFICES)){
				chosen = new Office();
				offices.add(chosen);
			}
		}
		return chosen;

	}

	public void ask(Bundle question) {
		String subject = question.getString("subject");
		List<Teacher> teachers = subjectMap.get(subject);
		if (null != teachers) {
			for (Teacher teacher : teachers) {
					refer(question, teacher);
					return;
			}
		}
	}

	public void assign(Teacher teacher) {
		teacher.setHouse(this);
		Set<String> subjects = teacher.getSubjects();
		if (null != subjects) {
			for (String subject : subjects) {
				List<Teacher> teachers = subjectMap.get(subject);
				if (null == teachers) {
					teachers = new ArrayList<Teacher>();
				}
				if (!teachers.contains(teacher)) {
					teachers.add(teacher);
				}
				subjectMap.put(subject.getClass().getName(), teachers);
			}
		}
	}

	public void refer(Bundle question, Teacher teacher) {
		teacher.answer(question);
	}

}
