package hogwarts.school.resource;

import android.os.Handler;
import android.os.Looper;

public class Classroom extends Thread {
	private Handler handler;
	

	public void run() {
		Looper.prepare();
		handler = new Handler();
		Looper.loop();
	}
}
