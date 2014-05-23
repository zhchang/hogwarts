package hogwarts.example;

import hogwarts.example.common.AddTopic;
import hogwarts.example.common.MathQuestion;
import hogwarts.example.common.MathSubject;
import hogwarts.school.staff.NormalTeacher;
import hogwarts.school.study.NormalAnswer;

public class MathTeacher extends NormalTeacher {

	public MathTeacher() {
		teachSubject(MathSubject.instance, AddTopic.instance);

		setAnswer(MathQuestion.ID, new NormalAnswer(this) {
			@Override
			public void answer() {
				if (question instanceof MathQuestion) {
					final MathQuestion mq = (MathQuestion) question;
					mq.answer = mq.op1 + mq.op2;
					mq.onCalculated();
				}
			}
		});

	}

}
