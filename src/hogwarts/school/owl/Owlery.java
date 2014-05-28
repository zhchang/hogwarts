package hogwarts.school.owl;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class Owlery extends BroadcastReceiver {
	Map<String, Owl> owls = new ConcurrentHashMap<String, Owl>();
	Queue<Owl> recycled = new ConcurrentLinkedQueue<Owl>();

	private Context context;
	private static Owlery instance;

	private Owlery() {

	}

	public static synchronized Owlery getInstance() {
		if (null == instance) {
			instance = new Owlery();
		}
		return instance;
	}

	public void finish() {
		if (null != context) {
			context.unregisterReceiver(this);
		}
	}

	public void init(Context context) {
		System.out.println("initing with context: " + context
				+ " for owlery : " + this);
		if (null == this.context) {
			this.context = context;
			IntentFilter filter = new IntentFilter();
			filter.addAction("owlpost");
			filter.addCategory("hogwarts");
			this.context.registerReceiver(this, filter);
		}
	}

	public void post(String name, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction("owlpost");
		intent.addCategory("hogwarts");
		intent.putExtra("post", bundle);
		intent.putExtra("name", name);
		context.sendBroadcast(intent);
	}

	public Owl getOwl(String name, OwlOwner owner) {
		Owl owl = owls.get(name);
		if (null == owl) {
			owl = recycled.poll();
			if (null == owl) {
				owl = new Owl(owner);
			}
			owls.put(name, owl);
		}
		owl.setOwner(owner);
		return owl;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("on receive owlpost: " + intent);
		String name = intent.getStringExtra("name");
		Bundle bundle = intent.getBundleExtra("post");
		if (null != name) {
			Owl owl = owls.get(name);
			if (null != owl) {
				owl.onPost(bundle);
			}
		}
	}
}
