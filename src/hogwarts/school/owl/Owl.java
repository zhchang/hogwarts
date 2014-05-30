package hogwarts.school.owl;

import android.os.Bundle;

public class Owl {

	private OwlOwner owner;
	
	public Owl(OwlOwner owner){
		this.owner = owner;
	}
	
	public void onPost(Bundle bundle){
		if(null != owner){
			owner.onPost(bundle);
		}
	}
	
	public void onNews(String news, Bundle bundle){
		if(null != owner){
			owner.onNews(news, bundle);
		}
	}
	
	public void setOwner(OwlOwner owner){
		this.owner = owner;
	}
}
