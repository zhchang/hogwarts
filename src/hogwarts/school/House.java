package hogwarts.school;

import hogwarts.school.owl.Owlery;
import hogwarts.school.resource.Office;
import hogwarts.school.staff.Head;
import hogwarts.school.staff.Teacher;
import hogwarts.school.study.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public abstract class House extends Service {
	private Head head;
	public static Class serviceClass;

	Map<String, Teacher> subjectMap = new ConcurrentHashMap<String, Teacher>();
	List<Office> offices = new ArrayList<Office>();
	private static final int MAX_OFFICES = 5;

	public House() {
		super();
	}

	abstract protected void initHouse(Context context);

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("service: on start command");
		if ("init".equals(intent.getAction())) {
			initHouse(getApplicationContext());
		} else if ("question".equals(intent.getAction())) {
			Question question = intent.getParcelableExtra("question");
			ask(question);
		} else if ("owlpost".equals(intent.getAction())) {
			String name = intent.getStringExtra("name");
			Bundle bundle = intent.getBundleExtra("post");
			Owlery.getInstance().post(name, bundle);
		}
		return Service.START_NOT_STICKY;
	}

	public static void startService(Context context, Class serviceClass) {
		House.serviceClass = serviceClass;
		Intent intent = new Intent(context, House.serviceClass);
		intent.setAction("init");
		context.startService(intent);
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
		synchronized (offices) {
			for (Office office : offices) {
				if (office.getUseCount() == 0) {
					chosen = office;
				}
			}
			if (null == chosen ) {
                            if(offices.size() < MAX_OFFICES){
				try {
					chosen = new Office();
					offices.add(chosen);
				} catch (Exception e) {
					e.printStackTrace();
				}
                            }
                            else{
		                int min = Integer.MAX_VALUE;
                                for (Office office : offices) {
                                    if (office.getUseCount() < min) {
                                        min = office.getUseCount();
                                        chosen = office;
                                    }
			        }       
                            }
			}
		}
		return chosen;

	}

	public void ask(Question question) {
		String subject = question.subject;
		Teacher teacher = subjectMap.get(subject);
		if (null != teacher) {
			refer(question, teacher);
		}
	}

	public void requestHelp(Object object, String subject, String help)throws Exception {
		Teacher teacher = subjectMap.get(subject);
		if (null != teacher) {
			teacher.help(help, object);
		}
	}

	public void assign(Teacher teacher) {
		teacher.setHouse(this);
		Set<String> subjects = teacher.getSubjects();
		if (null != subjects) {
			for (String subject : subjects) {
				subjectMap.put(subject, teacher);
			}
		}
	}

	public void refer(Question question, Teacher teacher) {
		teacher.answer(question);
	}

}
