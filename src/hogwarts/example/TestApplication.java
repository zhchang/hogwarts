package hogwarts.example;

import hogwarts.school.House;
import hogwarts.school.owl.Owlery;
import android.app.Application;

public class TestApplication extends Application {

	@Override
	public void onCreate(){
		House.startService(this, MathHouse.class);
		Owlery.getInstance().init(this);
	}

	@Override
	public void onTerminate(){
		Owlery.getInstance().finish();
	}
	
	
}
