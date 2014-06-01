package hogwarts.example;

import hogwarts.school.staff.NormalTeacher;
import hogwarts.school.study.QuestionWork;

import java.util.List;

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
						MathData mathData = (MathData)question.param;
						mathData.sum = mathData.op1 + mathData.op2;
						try {
							List<MathData> things = ipc.getQuestions();
							for(MathData thing : things){
								thing.sum = thing.op1 + thing.op2;
							}
							ipc.answer(things);
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
