package hogwarts.example.common;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class MathQuestion implements Parcelable{
	
	public int op1,op2;
	public int answer;

	protected MathQuestion(int a, int b) {
		op1 = a;
		op2 = b;
	}
	
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(op1);
		out.writeInt(op2);
    }


	
	public abstract void onCalculated();
	
}
