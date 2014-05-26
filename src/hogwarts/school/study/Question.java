package hogwarts.school.study;

import hogwarts.school.Gryffindor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
	public IBinder ipc;
	public Bundle param;
	public String subject;
	public String id;

	public Question(IBinder ipc, Bundle param,String subject,String id) {
		this.ipc = ipc;
		this.param = param;
		this.subject = subject;
		this.id = id;
	}

	public Question(Parcel in) {
		id = in.readString();
		subject = in.readString();
		param = in.readBundle();
		ipc = in.readStrongBinder();
	}

	public void ask(Context context) {
		if (null != context) {
			Intent intent = new Intent(context, Gryffindor.class);
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
		dest.writeBundle(param);
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
