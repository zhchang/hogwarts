package hogwarts.school.staff;

import hogwarts.school.House;
import hogwarts.school.resource.Office;
import hogwarts.school.study.Question;
import hogwarts.school.study.QuestionWork;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class NormalTeacher implements Teacher {
	protected House house;

	protected Set<String> subjects = new HashSet<String>();
	private Map<String, QuestionWork> answers  = new HashMap<String,QuestionWork>();
	
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
		QuestionWork work = answers.get(question.id);
		if(null!=work){
			work.setQuestion(question);
			office.enter(work);
		}

	}
	
	protected void setAnswer(String questionId, QuestionWork work){
		answers.put(questionId, work);
	}

}
