package hogwarts.school.staff;

import hogwarts.school.House;
import hogwarts.school.resource.Facility;
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

public class MacGonagall implements Head {

	Map<Subject, List<Teacher>> subjectMap = new HashMap<Subject, List<Teacher>>();
	Stack<Facility> freeFacilities = new Stack<Facility>();
	Stack<Facility> busyFacilities = new Stack<Facility>();
	private static final int MAX_FACILITIES = 5;

	@Override
	public void ask(Question question) {
		Pair<Subject, Topic> pair = question.getSubject();
		List<Teacher> teachers = subjectMap.get(pair.first);
		if (null != teachers) {
			for (Teacher teacher : teachers) {
				Set<Topic> topics = teacher.getTopics(pair.first);
				if (null == pair.second) {
					House.gryffindor.refer(question, teacher);
					return;
				} else if (null != topics && topics.contains(pair.second)) {
					House.gryffindor.refer(question, teacher);
					return;
				}
			}
		}
	}

	@Override
	public void introduce(Teacher teacher) {
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

	@Override
	public synchronized Facility bookFacility() {
		Facility facility = null;
		if (0 == freeFacilities.size()) {
			if (busyFacilities.size() < MAX_FACILITIES) {
				facility = new Facility();
			} else {
				facility = busyFacilities.peek();
			}
		} else {
			facility = freeFacilities.pop();
		}
		if (!busyFacilities.contains(facility)) {
			busyFacilities.push(facility);
		}
		return facility;
	}

	@Override
	public synchronized void releaseFacility(Facility facility) {
		if (null != facility) {
			if (!busyFacilities.contains(facility)) {
				busyFacilities.remove(facility);
			}
			if(!freeFacilities.contains(facility)){
				freeFacilities.push(facility);
			}
		}
	}

}
