package hogwarts.example;

import hogwarts.school.staff.NormalTeacher;
import hogwarts.school.study.Work;

public class MathTeacher extends NormalTeacher {

	public MathTeacher() {
		teachSubject("math");
		this.setAnswer("add", new Work() {

			@Override
			protected void doJob() {
				if (null != question) {
					MathQuestion thing = MathQuestion.Stub
							.asInterface(question.ipc);
					if (null != thing) {
						int op1 = question.param.getInt("op1");
						int op2 = question.param.getInt("op2");
						question.param.putInt("sum", op1 + op2);
						try {
							thing.answer(question.param);
						} catch (Exception e) {

						}
					}

				}

			}
		});

	}

}
