package hogwarts.example;

import hogwarts.school.House;
import hogwarts.school.study.Question;
import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.widget.TextView;

public class TestActivity extends Activity {
	static {
		House.serviceClass = MathHouse.class;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView text = new TextView(this);
		text.setText("fuck");
		this.setContentView(text);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Bundle add = new Bundle();
		add.putInt("op1", 1);
		add.putInt("op2", 2);
		new Question(new MathQuestion.Stub() {

			@Override
			public void answer(Bundle mathData) throws RemoteException {

				System.out.println("sum is : " + mathData.getInt("sum"));

			}
		}, add, "math", "add").ask(this);

	}

}
