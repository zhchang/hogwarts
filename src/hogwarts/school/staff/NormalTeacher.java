package hogwarts.school.staff;

import hogwarts.school.House;
import hogwarts.school.resource.Office;
import hogwarts.school.study.Question;
import hogwarts.school.study.QuestionWork;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class NormalTeacher implements Teacher {
	protected House house;

	protected Set<String> subjects = new HashSet<String>();
	private Map<String, List<QuestionWork>> answers = new ConcurrentHashMap<String, List<QuestionWork>>();

	public void teachSubject(String subject) {
		subjects.add(subject);
	}

	public void teachSubjects(Set<String> subjects) {
		subjects.addAll(subjects);
	}

	@Override
	public Set<String> getSubjects() {
		return subjects;
	}

	@Override
	public void setHouse(House house) {
		this.house = house;
	}

	protected QuestionWork getWork(String id) {
		List<QuestionWork> pool = answers.get(id);
		QuestionWork result = null;
		if (null != pool) {
			for (QuestionWork work : pool) {
				if (!work.isInUse()) {
					result = work;
					break;
				}
			}
			if (null == result) {
				try {
					result = (QuestionWork) pool.get(0).clone();
					pool.add(result);
					System.out.println("CLONE questionwork for id["+id+"]");
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public synchronized void answer(final Question question) {
		final Office office = house.getAnOffice();
		QuestionWork work = getWork(question.id);
		if (null != work) {
			work.setQuestion(question);
			office.enter(work);
		}
	}

	protected void setAnswer(String questionId, QuestionWork work) {

		List<QuestionWork> pool = answers.get(questionId);
		if (pool == null) {
			pool = new ArrayList<QuestionWork>();
			answers.put(questionId, pool);
		}
		pool.add(work);
	}

}
