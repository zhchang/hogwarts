package hogwarts.school.study;

import hogwarts.example.common.MyParcelable;
import hogwarts.school.Gryffindor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class Question implements Parcelable {

	private Bundle bundle = new Bundle();
	protected String subject;
	protected String id;
	private IInterface callback;
	private Context context;

	public Question(Context context, String subject, String id,
			IInterface callback) {
		this.context = context;
		this.subject = subject;
		this.id = id;
		this.callback = callback;
	}
	
	public Question(Parcel in){
		subject = in.readString();
		id = in.readString();
		callback = in.readStrongBinder();
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeString(subject);
		out.writeString(id);
		out.writeStrongBinder(callback.asBinder());
	}

	public void ask() {
		bundle.clear();
		bundle.putParcelable("question", this);
		Intent intent = new Intent(context, Gryffindor.class);
		intent.getExtras().putBundle("question", bundle);
		context.startService(intent);

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
