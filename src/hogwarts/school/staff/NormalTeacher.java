package hogwarts.school.staff;

import hogwarts.school.House;
import hogwarts.school.resource.Office;
import hogwarts.school.study.Question;
import hogwarts.school.study.Work;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class NormalTeacher implements Teacher {
	House house;

	protected Set<String> subjects = new HashSet<String>();
	private Map<String, Work> answers  = new HashMap<String,Work>();
	
	public void teachSubject(String subject){
        	subjects.add(subject);
	}

	public void teachSubjects(Set<String> subjects){ 
		subjects.addAll(subjects);
	}


	@Override
	public Set<String> getSubjects() {
		return subjects;
	}

	@Override
	public void setHouse(House house){
		this.house = house;
	}

	
	@Override
	public void answer(final Question question) {
		final Office office = house.getAnOffice();
		Work work = answers.get(question.id);
		if(null!=work){
			work.setQuestion(question);
			office.enter(work);
		}

	}
	
	protected void setAnswer(String questionId, Work work){
		answers.put(questionId, work);
	}

}
