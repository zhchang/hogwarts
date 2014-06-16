package hogwarts.school.study;

import hogwarts.school.House;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
	public IBinder ipc;
	public IBinder lifeCycle;
	public String subject;
	public String id;

	public Question(IBinder ipc,IBinder lifeCycle,String subject,String id) {
		this.ipc = ipc;
		this.lifeCycle = lifeCycle;
		this.subject = subject;
		this.id = id;
	}

	public Question(Parcel in) {
		id = in.readString();
		subject = in.readString();
		ipc = ParcelHelper.readBinder(in, this.getClass().getClassLoader());
		lifeCycle = ParcelHelper.readBinder(in, this.getClass().getClassLoader());
	}

	public void ask(Context context) {
		System.out.println(System.currentTimeMillis());
		if (null != context) {
			Intent intent = new Intent(context, House.serviceClass);
			intent.setAction("question");
			intent.putExtra("question", this);
			context.startService(intent);
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		ParcelHelper.writeString(dest,id);
		ParcelHelper.writeString(dest,subject);
		ParcelHelper.writeBinder(dest, ipc);
		ParcelHelper.writeBinder(dest, lifeCycle);
	}

	public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
		public Question createFromParcel(Parcel in) {
			return new Question(in);
		}

		public Question[] newArray(int size) {
			return new Question[size];
		}
	};

}
