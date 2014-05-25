package hogwarts.school.staff;

import hogwarts.school.House;

import java.util.Set;

import android.os.Bundle;

public interface Teacher {
	
	Set<String> getSubjects();
	
	void answer(Bundle question);
	
	void setHouse(House house);
}
