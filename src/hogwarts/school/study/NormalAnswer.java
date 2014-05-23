package hogwarts.school.study;

import hogwarts.school.resource.Facility;
import hogwarts.school.staff.NormalTeacher;

public abstract class NormalAnswer implements Runnable {

	protected Question question;
	protected NormalTeacher teacher;
	protected Facility facility;

	public NormalAnswer(NormalTeacher teacher) {
		this.teacher = teacher;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	protected abstract void answer();

	public void run() {
		try {
			answer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		cleanup();
	}

	private void cleanup() {
		if (null != teacher) {
			teacher.releaseFacility(facility);
		}
	}

}
