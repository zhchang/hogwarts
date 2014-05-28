package hogwarts.school.resource;
import hogwarts.school.study.QuestionWork;

public class Office extends Room{

	
	public synchronized void enter(QuestionWork work) {
		work.setOffice(this);
		addJob(work);
		useCount++;
	}

	

	

}
