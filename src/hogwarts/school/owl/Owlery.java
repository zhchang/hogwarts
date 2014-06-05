package hogwarts.school.owl;

import java.util.ArrayList;
import java.util.List;
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
	Map<String, List<Owl>> subscriptions = new ConcurrentHashMap<String, List<Owl>>();
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
			filter.addAction("owlnews");
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
	
	public void publish(String news, Bundle bundle){
		Intent intent = new Intent();
		intent.setAction("owlnews");
		intent.addCategory("hogwarts");
		intent.putExtra("post", bundle);
		intent.putExtra("news", news);
		context.sendBroadcast(intent);
	}

	public Owl registerOwl(String name, OwlOwner owner) {
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

	public void unregisterOwl(Owl owl) {
		List<String> names = new ArrayList<String>();
		for (Map.Entry<String, Owl> entry : owls.entrySet()) {
			if (entry.getValue().equals(owl)) {
				names.add(entry.getKey());
			}
		}
		for (String name : names) {
			owls.remove(name);
		}
	}

	public void subscribe(String news, Owl owl) {
		if (null == news || news.length() == 0) {
			return;
		}
		List<Owl> owls = subscriptions.get(news);
		if (null == owls) {
			owls = new ArrayList<Owl>();
			subscriptions.put(news, owls);
		}
		synchronized (owls) {
			if (!owls.contains(owl)) {
				owls.add(owl);
			}
		}
		System.out.println("subscription of news["+news+"] of size("+owls.size()+")");
	}

	public void unsubscribe(String news, Owl owl) {
		if (null == news || news.length() == 0) {
			return;
		}
		List<Owl> owls = subscriptions.get(news);
		if (null != owls) {
			synchronized (owls) {
				owls.remove(owl);
			}
		}
	}

	public void unsubscribeAll(Owl owl) {
		for (Map.Entry<String, List<Owl>> entry : subscriptions.entrySet()) {
			if (null != entry.getValue() && entry.getValue().contains(owl)) {
				synchronized (entry.getValue()) {
					entry.getValue().remove(owl);
				}
			}
		}
	}

	public void disposeOwl(Owl owl) {
		unregisterOwl(owl);
		unsubscribeAll(owl);
		recycled.add(owl);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("on receive owlpost: " + intent);
		if("owlpost".equals(intent.getAction())){
			System.out.println("this is owl post");
			String name = intent.getStringExtra("name");
			Bundle bundle = intent.getBundleExtra("post");
			if (null != name) {
				Owl owl = owls.get(name);
				if (null != owl) {
					owl.onPost(bundle);
				}
			}
		}
		else if("owlnews".equals(intent.getAction())){
			System.out.println("this is owl news");
			String news = intent.getStringExtra("news");
			Bundle bundle = intent.getBundleExtra("post");
			if(null != news){
				List<Owl> owls = subscriptions.get(news);
				if(null != owls){
					System.out.println("subscription size: " + owls.size());
					synchronized(owls){
						for(Owl owl : owls){
							owl.onNews(news,bundle);
						}
					}
				}
			}
		}
	}
}
