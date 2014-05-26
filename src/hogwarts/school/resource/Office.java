package hogwarts.school.resource;
import hogwarts.school.study.Work;

public class Office extends Room{

	
	public synchronized void enter(Work work) {
		work.setOffice(this);
		addJob(work);
		useCount++;
	}

	

	

}
