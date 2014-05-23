package hogwarts.school.staff;

import hogwarts.school.resource.Facility;
import hogwarts.school.study.Question;


public interface Head {
	
	void ask(Question question);
	
	void introduce(Teacher teacher);

	Facility bookFacility();
	
	void releaseFacility(Facility facility);


}
