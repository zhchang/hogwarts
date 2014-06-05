package hogwarts.example;

import hogwarts.school.owl.Owlery;
import hogwarts.school.staff.NormalTeacher;
import hogwarts.school.study.QuestionWork;

import java.util.List;

import android.os.Bundle;

public class MathTeacher extends NormalTeacher {

	public MathTeacher() {
		teachSubject("math");
		this.setAnswer("add", new QuestionWork() {

			@Override
			protected void doJob() {
				if (null != question) {
					MathQuestion ipc = MathQuestion.Stub
							.asInterface(question.ipc);
					if (null != ipc) {
						try {
							List<MathData> things = ipc.getQuestions();
							for(MathData thing : things){
								thing.sum = thing.op1 + thing.op2;
							}
							ipc.answer(things);
							Bundle bundle = new Bundle();
							bundle.putString("testing", "testing");
							Owlery.getInstance().post("testActivity", bundle);
							Owlery.getInstance().publish("test.news", bundle);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}

			}
		});

	}
	
	public void help(String help, Object object){
	}

}
