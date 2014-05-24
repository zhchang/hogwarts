package hogwarts.school.staff;

import hogwarts.school.House;
import hogwarts.school.study.Question;
import hogwarts.school.study.Subject;

import java.util.Set;

public interface Teacher {
	
	Set<Subject> getSubjects();
	
	void answer(Question question);
	
	void setHouse(House house);
	
}
