package hogwarts.example;

import hogwarts.school.Gryffindor;
import hogwarts.school.owl.Owlery;
import android.content.Context;

public class MathHouse extends Gryffindor {

	@Override
	protected void initHouse(Context context) {
		this.assign(new MathTeacher());
		Owlery.getInstance().init(context);
	}

}
