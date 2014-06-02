package hogwarts.school.study;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class ParcelHelper {
	public Parcel parcel;
	
	public ParcelHelper(){
	}
	
	ParcelHelper writeBundle(Bundle value){
		writeBoolean(value!=null);
		if(value!=null){
			parcel.writeBundle(value);
		}
		return this;
	}
	
	ParcelHelper writeInt(int value){
		parcel.writeInt(value);
		return this;
	}
	ParcelHelper writeLong(long value){
		parcel.writeLong(value);
		return this;
	}
	ParcelHelper writeByte(byte value){
		parcel.writeByte(value);
		return this;
	}
	ParcelHelper writeDouble(double value){
		parcel.writeDouble(value);
		return this;
	}
	ParcelHelper writeBoolean(boolean value){
		parcel.writeByte((byte)(value?1:0));
		return this;
	}
	ParcelHelper writeString(String value){
		parcel.writeString(value);
		return this;
	}
	
	ParcelHelper writeParcelable(Parcelable value){
		writeBoolean(value!=null);
		if(value!=null){
			parcel.writeParcelable(value, Parcelable.CONTENTS_FILE_DESCRIPTOR);
		}
		return this;
	}
	
	ParcelHelper writeList(List<Parcelable> values){
		writeBoolean(values!=null);
		if(values!=null){
			writeInt(values.size());
			for(Parcelable value : values){
				writeParcelable(value);
			}
		}
		return this;
	}
	ParcelHelper writeStringList(List<String> values){
		writeBoolean(values!=null);
		if(values != null){
			writeInt(values.size());
			for(String value : values){
				writeString(value);
			}
		}
		return this;
	}
	
	int readInt(){
		return parcel.readInt();
	}

	byte readByte(){
		return parcel.readByte();
	}
	long readLong(){
		return parcel.readLong();
	}
	double readDouble(){
		return parcel.readDouble();
	}
	boolean readBoolean(){
		return parcel.readByte()==1;
	}
	String readString(){
		return parcel.readString();
	}
	
	<T>void readParcelable(T object,ClassLoader cl){
		boolean notNull = readBoolean();
		if(notNull){
			object = parcel.readParcelable(cl);
		}
	}
	
	Bundle readBundle(){

		boolean notNull = readBoolean();
		if(notNull){
			return parcel.readBundle();
		}
		return null;
	}
	
	List<String> readStringList(){
		List<String> things = null;
		boolean notNull = readBoolean();
		if(notNull){
			things = new ArrayList<String>();
			int count = readInt();
			for(int i = 0 ; i < count; i ++){
				things.add(readString());
			}
		}
		return things;
	}
	
	<T>List<T> readParcelableList(ClassLoader cl){
		List<T> things = null;
		boolean notNull = readBoolean();
		if(notNull){
			things = new ArrayList<T>();
			int count = readInt();
			for(int i  = 0 ; i < count; i ++){
				T thing = parcel.readParcelable(cl);
				things.add(thing);
			}
		}
		return things;
	}
}

