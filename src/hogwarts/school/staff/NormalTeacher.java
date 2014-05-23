package hogwarts.school.staff;

import hogwarts.example.common.AddTopic;
import hogwarts.example.common.MathSubject;
import hogwarts.school.House;
import hogwarts.school.resource.Facility;
import hogwarts.school.study.NormalAnswer;
import hogwarts.school.study.Question;
import hogwarts.school.study.Subject;
import hogwarts.school.study.Topic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class NormalTeacher implements Teacher {

	private Map<Subject, Set<Topic>> knowledge = new HashMap<Subject, Set<Topic>>();
	protected House house;
	private Map<String, NormalAnswer> answers  = new HashMap<String,NormalAnswer>();
	
	public void teachSubject(Subject subject,Topic topic){
		Set<Topic> topics = new HashSet<Topic>();
		topics.add(AddTopic.instance);
		knowledge.put(MathSubject.instance, topics);
	}

	public void teachSubject(Subject subject, Set<Topic> topics){
		knowledge.put(MathSubject.instance, topics);
	}
	
	
	public void teachSubjects(Map<Subject,Set<Topic>> subjects){ 
		knowledge.putAll(subjects);
	}

	@Override
	public Set<Subject> getSubjects() {
		return knowledge.keySet();
	}

	@Override
	public Set<Topic> getTopics(Subject subject) {
		return knowledge.get(subject);
	}
	
	@Override
	public void setHouse(House house){
		this.house = house;
	}
	
	@Override
	public void answer(final Question question) {
		final Facility facility = house.bookFacility();
		NormalAnswer answer = answers.get(question.getId());
		if(null!=answer){
			answer.setQuestion(question);
			facility.schedule(answer);
		}

	}
	
	public void releaseFacility(Facility facility){
		house.releaseFacility(facility);
	}
	
	protected void setAnswer(String questionId, NormalAnswer answer){
		answers.put(questionId, answer);
	}

}
