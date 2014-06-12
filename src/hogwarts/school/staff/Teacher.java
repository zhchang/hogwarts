package hogwarts.school.staff;

import hogwarts.school.House;
import hogwarts.school.study.Question;

import java.util.Set;

public interface Teacher {
	
	Set<String> getSubjects();
	
	void answer(Question question);
	
	void setHouse(House house);
	
	void help(String help, Object object) throws Exception;
}
