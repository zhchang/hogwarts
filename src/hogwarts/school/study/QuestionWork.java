package hogwarts.school.study;

import hogwarts.school.resource.Office;

public abstract class QuestionWork implements Runnable {

	protected Office office;
	protected Question question;


	public void setOffice(Office office) {
		this.office = office;
	}
	
	public void setQuestion(Question question){
		this.question = question;
	}

	protected abstract void doJob();

	public void run() {
		try {
			doJob();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null!=office){
			office.leave();
		}
	}

}
