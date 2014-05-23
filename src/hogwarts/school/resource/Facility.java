package hogwarts.school.resource;

import hogwarts.school.study.NormalAnswer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.os.Handler;
import android.os.Looper;

public class Facility extends Thread{
	private Handler handler;
	private Queue<Runnable> tasks = new ConcurrentLinkedQueue<Runnable>();
	
	public Facility(){
		this.start();
	}
	
	public void schedule(NormalAnswer answer){
		answer.setFacility(this);
		if(null == handler){
			if(!tasks.contains(answer)){
				tasks.add(answer);
			}
		}
		else{
			handler.post(answer);
		}
	}

	public void run() {
		Looper.prepare();
		handler = new Handler();
		while(tasks.size()>0){
			tasks.remove().run();
		}
		Looper.loop();
	}

}
