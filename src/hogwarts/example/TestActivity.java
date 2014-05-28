package hogwarts.example;

import hogwarts.school.House;
import hogwarts.school.owl.Owlery;
import hogwarts.school.study.Question;
import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

public class TestActivity extends Activity {
	static {
		House.serviceClass = MathHouse.class;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Owlery.getInstance().init(this);
		super.onCreate(savedInstanceState);
		TextView text = new TextView(this);
		text.setText("fuck this man\n fuck this");
		text.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Owlery.getInstance().post("fuck", new Bundle());
			}
		});
		this.setContentView(text);
	}

	@Override
	protected void onDestroy() {
		Owlery.getInstance().finish();
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
