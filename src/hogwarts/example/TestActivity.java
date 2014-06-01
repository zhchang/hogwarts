package hogwarts.example;

import hogwarts.school.owl.Owlery;
import hogwarts.school.study.Question;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView text = new TextView(this);
		text.setId(1000000);
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
	protected void onResume() {
		super.onResume();
		System.out.println("UI: " + android.os.Process.myPid() + "|"
				+ android.os.Process.myTid());
		new Question<MathData>(new MathQuestion.Stub() {

			@Override
			public List<MathData> getQuestions() {
				System.out.println(System.currentTimeMillis());
				System.out.println("getQuestions: "
						+ android.os.Process.myPid() + "|"
						+ android.os.Process.myTid());
				List<MathData> things = new ArrayList<MathData>();
				things.add(new MathData(1, 2));
				things.add(new MathData(3, 4));
				return things;
			}

			@Override
			public void answer(List<MathData> mathDatas) throws RemoteException {
				System.out.println(System.currentTimeMillis());
				System.out.println("anwswer: " + android.os.Process.myPid()
						+ "|" + android.os.Process.myTid());
				for (MathData mathData : mathDatas) {
					System.out.println("sum is : " + mathData.sum);
				}
				TextView thingy = (TextView) TestActivity.this
						.findViewById(1000000);
				thingy.setText("hoorey");
			}
		}, new MathData(1, 2), "math", "add").ask(this);

	}

}
