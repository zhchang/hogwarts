package hogwarts.school.staff;

import hogwarts.school.House;
import hogwarts.school.study.Question;
import hogwarts.school.study.Subject;
import hogwarts.school.study.Topic;

import java.util.Set;

public interface Teacher {
	
	Set<Subject> getSubjects();
	
	Set<Topic> getTopics(Subject subject);
	
	void answer(Question question);
	
	void setHouse(House house);
	
}
