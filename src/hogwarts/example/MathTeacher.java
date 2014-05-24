package hogwarts.example;

import java.util.Set;

import hogwarts.example.common.MathQuestion;
import hogwarts.example.common.MathSubject;
import hogwarts.school.staff.NormalTeacher;
import hogwarts.school.study.Subject;
import hogwarts.school.study.Topic;
import hogwarts.school.study.Work;

public class MathTeacher extends NormalTeacher {

	public MathTeacher() {
		teachSubject(MathSubject.instance);

		setAnswer(MathQuestion.ID, new Work() {
			@Override
			public void doJob() {
				if (question instanceof MathQuestion) {
					final MathQuestion mq = (MathQuestion) question;
					mq.answer = mq.op1 + mq.op2;
					mq.onCalculated();
				}
			}
		});

	}

}
