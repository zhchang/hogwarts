package hogwarts.school.study;

import hogwarts.school.resource.Office;

public abstract class Work implements Runnable {

	protected Question question;
	protected Office office;

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setOffice(Office office) {
		this.office = office;
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
