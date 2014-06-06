package hogwarts.school.study;

import hogwarts.school.resource.Office;

public abstract class QuestionWork implements Runnable, Cloneable {

	protected Office office;
	protected Question question;

	@Override
	public Object clone() throws CloneNotSupportedException {
		QuestionWork thing = (QuestionWork) super.clone();
		thing.finish();
		return thing;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	protected abstract void doJob();

	private void finish() {
		office = null;
		question = null;
	}

	public boolean isInUse() {
		return !(office == null && question == null);
	}

	public void run() {
		try {
			doJob();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != office) {
			office.leave();
			finish();
		}
	}

}
