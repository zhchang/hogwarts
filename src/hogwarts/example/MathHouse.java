package hogwarts.example;

import hogwarts.school.Gryffindor;

public class MathHouse extends Gryffindor {

	@Override
	protected void initHouse() {
		this.assign(new MathTeacher());
	}

}
