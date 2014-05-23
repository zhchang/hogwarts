package hogwarts.example.common;

import hogwarts.school.study.Question;

public abstract class MathQuestion extends Question {
	
	public static final String ID = "MathQuestion";
	
	public int op1,op2;
	public int answer;

	protected MathQuestion(int a, int b) {
		super(MathSubject.instance,AddTopic.instance);
		op1 = a;
		op2 = b;
	}
	
	public abstract void onCalculated();
	
	public String getId(){
		return ID;
	}

}
