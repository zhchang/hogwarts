package hogwarts.example;

import android.os.Parcel;
import android.os.Parcelable;

public class MathData implements Parcelable {
	
	public MathData(int op1, int op2){
		this.op1 = op1;
		this.op2 = op2;
	}
	
	public MathData(Parcel in){
		this.op1 = in.readInt();
		this.op2 = in.readInt();
		this.sum = in.readInt();
	}
	
	int op1;
	int op2;
	int sum;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {

		dest.writeInt(op1);
		dest.writeInt(op2);
		dest.writeInt(sum);
	}
	
	public static final Parcelable.Creator<MathData> CREATOR = new Parcelable.Creator<MathData>() {
		public MathData createFromParcel(Parcel in) {
			return new MathData(in);
		}

		public MathData[] newArray(int size) {
			return new MathData[size];
		}
	};

}
