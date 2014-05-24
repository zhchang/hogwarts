package hogwarts.school;

import hogwarts.school.resource.Office;
import hogwarts.school.staff.Head;
import hogwarts.school.staff.MacGonagall;
import hogwarts.school.staff.Teacher;
import hogwarts.school.study.Question;
import hogwarts.school.study.Subject;
import hogwarts.school.study.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import android.util.Pair;

public class House {
	private Head head;

	public static final House gryffindor;
	public static final House hufflepuff;
	public static final House ravenclaw;
	public static final House slytherin;

	static {
		gryffindor = new House(new MacGonagall());
		hufflepuff = new House(new MacGonagall());
		ravenclaw = new House(new MacGonagall());
		slytherin = new House(new MacGonagall());
	}

	Map<Subject, List<Teacher>> subjectMap = new HashMap<Subject, List<Teacher>>();
	List<Office> offices = new ArrayList<Office>();
	private static final int MAX_OFFICES = 5;

	private House(Head head) {
		this.head = head;
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

	public void ask(Question question) {
		Pair<Subject, Topic> pair = question.getSubject();
		List<Teacher> teachers = subjectMap.get(pair.first);
		if (null != teachers) {
			for (Teacher teacher : teachers) {
					refer(question, teacher);
					return;
			}
		}
	}

	public void assign(Teacher teacher) {
		Set<Subject> subjects = teacher.getSubjects();
		if (null != subjects) {
			for (Subject subject : subjects) {
				List<Teacher> teachers = subjectMap.get(subject);
				if (null == teachers) {
					teachers = new ArrayList<Teacher>();
				}
				if (!teachers.contains(teacher)) {
					teachers.add(teacher);
				}
				subjectMap.put(subject, teachers);
			}
		}
	}

	public void refer(Question question, Teacher teacher) {
		teacher.answer(question);
	}

}
