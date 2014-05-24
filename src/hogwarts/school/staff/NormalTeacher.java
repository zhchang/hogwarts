package hogwarts.school.staff;

import hogwarts.school.House;
import hogwarts.school.resource.Office;
import hogwarts.school.study.Question;
import hogwarts.school.study.Subject;
import hogwarts.school.study.Work;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class NormalTeacher implements Teacher {

	protected Set<Subject> subjects = new HashSet<Subject>();
	protected House house;
	private Map<String, Work> answers  = new HashMap<String,Work>();
	
	public void teachSubject(Subject subject){
        subjects.add(subject);
	}

	public void teachSubjects(Set<Subject> subjects){ 
		subjects.addAll(subjects);
	}

	@Override
	public Set<Subject> getSubjects() {
		return subjects;
	}

	@Override
	public void setHouse(House house){
		this.house = house;
	}
	
	@Override
	public void answer(final Question question) {
		final Office office = house.getAnOffice();
		Work work = answers.get(question.getId());
		if(null!=work){
			work.setQuestion(question);
			office.enter(work);
		}

	}
	
	protected void setAnswer(String questionId, Work answer){
		answers.put(questionId, answer);
	}

}
