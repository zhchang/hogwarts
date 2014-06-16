package hogwarts.example;

import hogwarts.school.owl.Owl;
import hogwarts.school.owl.OwlOwner;
import hogwarts.school.owl.Owlery;
import hogwarts.school.study.Question;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.widget.TextView;

public class TestActivity extends Activity implements OwlOwner {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView text = new TextView(this);
		text.setId(1000000);
		text.setText("test activity");
		this.setContentView(text);
	}

	@Override
	public void onStart() {
		super.onStart();
		Owl owl = Owlery.getInstance().registerOwl("testActivity", this);
		Owlery.getInstance().subscribe("test.news", owl);
	}

	@Override
	public void onStop() {
		super.onStop();
		Owl owl = Owlery.getInstance().registerOwl("testActivity", this);
		Owlery.getInstance().disposeOwl(owl);
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("UI: " + android.os.Process.myPid() + "|"
				+ android.os.Process.myTid());
		new Question(new MathQuestion.Stub() {

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
		}, null, "math", "add").ask(this);

	}

	@Override
	public void onPost(Bundle bundle) {
		System.out.println(bundle);
	}

	@Override
	public void onNews(String news, Bundle bundle) {
		System.out.println("news : " + news);
		System.out.println(bundle);
	}

}
