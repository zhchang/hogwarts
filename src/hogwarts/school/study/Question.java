package hogwarts.school.study;

import hogwarts.school.House;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class Question<T extends Parcelable> implements Parcelable {
	public IBinder ipc;
	public T param;
	public String subject;
	public String id;

	public Question(IBinder ipc, T param,String subject,String id) {
		this.ipc = ipc;
		this.param = param;
		this.subject = subject;
		this.id = id;
	}

	public Question(Parcel in) {
		id = in.readString();
		subject = in.readString();
		param = in.readParcelable(this.getClass().getClassLoader());
		ipc = in.readStrongBinder();
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
		dest.writeString(id);
		dest.writeString(subject);
		dest.writeParcelable(param, Parcelable.CONTENTS_FILE_DESCRIPTOR);
		dest.writeStrongBinder(ipc);
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
