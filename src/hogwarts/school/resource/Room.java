package hogwarts.school.resource;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.os.Handler;
import android.os.Looper;

public class Room extends Thread{

	private Handler handler;
	private Queue<Runnable> tasks = new ConcurrentLinkedQueue<Runnable>();
	protected int useCount = 0;

	protected Room(){
	    this.start();
	}
	
	public int getUseCount(){
	    return useCount;
	}
	public synchronized void leave() {
	    useCount--;
	}
	public void run() {
            synchronized(this){
		Looper.prepare();
		handler = new Handler();
		while (tasks.size() > 0) {
			tasks.remove().run();
		}
            }
	    Looper.loop();
	}
	protected synchronized void addJob(Runnable job){
            if(null == handler){
                tasks.add(job);
            }
            else{
                handler.post(job);
            }
	}
}
