package hogwarts.school;

import hogwarts.school.owl.Owlery;
import hogwarts.school.resource.Office;
import hogwarts.school.staff.Head;
import hogwarts.school.staff.Teacher;
import hogwarts.school.study.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public abstract class House extends Service {
	private Head head;
	public static Class serviceClass;

	Map<String, List<Teacher>> subjectMap = new HashMap<String, List<Teacher>>();
	List<Office> offices = new ArrayList<Office>();
	private static final int MAX_OFFICES = 5;
	private boolean inited = false;

	public House() {
		super();
	}

	abstract protected void initHouse(Context context);

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(!inited){
			initHouse(getApplicationContext());
			inited = true;
		}
		System.out.println("service: on start command");
		if ("question".equals(intent.getAction())) {
			Question question = intent.getParcelableExtra("question");
			ask(question);
		} else if ("owlpost".equals(intent.getAction())) {
			String name = intent.getStringExtra("name");
			Bundle bundle = intent.getBundleExtra("post");
			Owlery.getInstance().post(name, bundle);
		}
		return Service.START_NOT_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("service created");
	}

	public IBinder onBind(Intent intent) {
		return null;
	}

	public void appointHead(Head head) {
		this.head = head;
	}

	public Head getHead() {
		return this.head;
	}

	public Office getAnOffice() {
		Office chosen = null;
		int min = Integer.MAX_VALUE;
		synchronized (offices) {
			for (Office office : offices) {
				if (office.getUseCount() < min) {
					min = office.getUseCount();
					chosen = office;
				}
			}
			if (null == chosen || (min > 0 && offices.size() < MAX_OFFICES)) {
				try {
					chosen = new Office();
					offices.add(chosen);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return chosen;

	}

	public void ask(Question question) {
		String subject = question.subject;
		List<Teacher> teachers = subjectMap.get(subject);
		if (null != teachers) {
			for (Teacher teacher : teachers) {
				refer(question, teacher);
				return;
			}
		}
	}

	public void assign(Teacher teacher) {
		teacher.setHouse(this);
		Set<String> subjects = teacher.getSubjects();
		if (null != subjects) {
			for (String subject : subjects) {
				List<Teacher> teachers = subjectMap.get(subject);
				if (null == teachers) {
					teachers = new ArrayList<Teacher>();
				}
				if (!teachers.contains(teacher)) {
					teachers.add(teacher);
				}
				subjectMap.put(subject, teachers);
			}
		}
	}

	public void refer(Question question, Teacher teacher) {
		teacher.answer(question);
	}

}
