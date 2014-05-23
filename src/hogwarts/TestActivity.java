package hogwarts;

import hogwarts.example.MathTeacher;
import hogwarts.example.common.MathQuestion;
import hogwarts.school.House;
import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		House.gryffindor.assign(new MathTeacher());

	}
	
	@Override
	protected void onResume(){
		super.onResume();
		new MathQuestion(1, 2) {
			@Override
			public void onCalculated() {
				if(!this.isValid())return;
				//we can choose to run in ui thread here.
				System.out.println(this.answer);
				this.answered();
			}

		}.ask();
	}

}
