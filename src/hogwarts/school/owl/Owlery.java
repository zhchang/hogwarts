package hogwarts.school.owl;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.os.Bundle;

public class Owlery {
	Map<String, Owl> owls = new ConcurrentHashMap<String, Owl>();
	Queue<Owl> recycled = new ConcurrentLinkedQueue<Owl>();

	public void post(String name, Bundle bundle) {
		Owl owl = owls.get(name);
		if (null != owl) {
			owl.onPost(bundle);
		}

	}

	public Owl getOwl(String name,OwlOwner owner) {
		Owl owl = owls.get(name);
		if (null == owl) {
			owl = recycled.poll();
			if(null == owl){
				owl = new Owl(owner);
			}
			owls.put(name,owl);
		}
		owl.setOwner(owner);
		return owl;
	}
}
