package hogwarts.school.resource;

import hogwarts.school.study.Work;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.os.Handler;
import android.os.Looper;

public class Office extends Thread {
	private Handler handler;
	private Queue<Runnable> tasks = new ConcurrentLinkedQueue<Runnable>();
	private int useCount = 0;

	public Office() {
		this.start();
	}
	
	public int getUseCount(){
		return useCount;
	}

	public synchronized void enter(Work work) {
		work.setOffice(this);
		if (null == handler) {
			if (!tasks.contains(work)) {
				tasks.add(work);
			}
		} else {
			handler.post(work);
		}
		useCount++;
	}

	public synchronized void leave() {
		useCount--;
	}

	public void run() {
		Looper.prepare();
		handler = new Handler();
		while (tasks.size() > 0) {
			tasks.remove().run();
		}
		Looper.loop();
	}

}
