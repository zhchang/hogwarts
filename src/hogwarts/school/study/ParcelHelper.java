package hogwarts.school.study;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

public class ParcelHelper {

	public static void writeBundle(Parcel parcel, Bundle value) {
		writeBoolean(parcel, value != null);
		if (value != null) {
			parcel.writeBundle(value);
		}
	}

	public static void writeInt(Parcel parcel, int value) {
		parcel.writeInt(value);
	}

	public static void writeLong(Parcel parcel, long value) {
		parcel.writeLong(value);
	}

	public static void writeByte(Parcel parcel, byte value) {
		parcel.writeByte(value);
	}

	public static void writeDouble(Parcel parcel, double value) {
		parcel.writeDouble(value);
	}

	public static void writeBoolean(Parcel parcel, boolean value) {
		parcel.writeByte((byte) (value ? 1 : 0));
	}

	public static void writeString(Parcel parcel, String value) {
		parcel.writeString(value);
	}

	public static void writeParcelable(Parcel parcel, Parcelable value) {
		writeBoolean(parcel, value != null);
		if (value != null) {
			parcel.writeParcelable(value, Parcelable.CONTENTS_FILE_DESCRIPTOR);
		}
	}
	
	public static void writeBinder(Parcel parcel, IBinder value){
		writeBoolean(parcel,value!=null);
		if(null!=value){
			parcel.writeStrongBinder(value);
		}
	}

	public static <T extends Parcelable> void writeList(Parcel parcel,
			List<T> values) {
		writeBoolean(parcel, values != null);
		if (values != null) {
			writeInt(parcel, values.size());
			for (Parcelable value : values) {
				writeParcelable(parcel, value);
			}
		}
	}

	public static void writeStringList(Parcel parcel, List<String> values) {
		writeBoolean(parcel, values != null);
		if (values != null) {
			writeInt(parcel, values.size());
			for (String value : values) {
				writeString(parcel, value);
			}
		}
	}

	public static <T extends Parcelable> void writeSparseArray(Parcel parcel,
			SparseArray<T> values) {
		writeBoolean(parcel, values != null);
		if (values != null) {
			int count = values.size();
			writeInt(parcel, count);
			for (int i = 0; i < count; i++) {
				int key = values.keyAt(i);
				writeInt(parcel, key);
				writeParcelable(parcel, values.get(key));
			}
		}
	}

	public static void writeSparseString(Parcel parcel,
			SparseArray<String> values) {
		writeBoolean(parcel, values != null);
		if (values != null) {
			int count = values.size();
			writeInt(parcel, count);
			for (int i = 0; i < count; i++) {
				int key = values.keyAt(i);
				writeInt(parcel, key);
				writeString(parcel, values.get(key));
			}
		}
	}

	public static void writeStringMap(Parcel parcel, Map<String, String> values) {
		writeBoolean(parcel, values != null);
		if (values != null) {
			int count = values.size();
			writeInt(parcel, count);
			for (Map.Entry<String, String> entry : values.entrySet()) {
				writeString(parcel, entry.getKey());
				writeString(parcel, entry.getValue());
			}
		}
	}

	public static <T extends Parcelable> void writeMap(Parcel parcel,
			Map<String, T> values) {
		writeBoolean(parcel, values != null);
		if (values != null) {
			int count = values.size();
			writeInt(parcel, count);
			for (Map.Entry<String, T> entry : values.entrySet()) {
				writeString(parcel, entry.getKey());
				writeParcelable(parcel, entry.getValue());
			}
		}
	}
	
	public static <T extends Parcelable> void writeListMap(Parcel parcel, Map<String,List<T>> values){
		writeBoolean(parcel, values != null);
		if (values != null) {
			int count = values.size();
			writeInt(parcel, count);
			for (Map.Entry<String, List<T>> entry : values.entrySet()) {
				writeString(parcel, entry.getKey());
				writeList(parcel, entry.getValue());
			}
		}
	}
	
	public static <T extends Parcelable> Map<String,List<T>> readListMap(Parcel parcel, ClassLoader cl){
		Map<String, List<T>> result = null;
		boolean notNull = readBoolean(parcel);
		if (notNull) {
			result = new HashMap<String, List<T>>();
			int count = readInt(parcel);
			for (int i = 0; i < count; i++) {
				String key = readString(parcel);
				List<T> obj = readList(parcel,cl);
				result.put(key, obj);
			}
		}
		return result;
	}

	public static Map<String, String> readStringMap(Parcel parcel) {
		Map<String, String> result = null;
		boolean notNull = readBoolean(parcel);
		if (notNull) {
			result = new HashMap<String, String>();
			int count = readInt(parcel);
			for (int i = 0; i < count; i++) {
				result.put(readString(parcel), readString(parcel));
			}
		}
		return result;
	}

	public static <T extends Parcelable> Map<String, T> readMap(Parcel parcel,ClassLoader cl) {
		Map<String, T> result = null;
		boolean notNull = readBoolean(parcel);
		if (notNull) {
			result = new HashMap<String, T>();
			int count = readInt(parcel);
			for (int i = 0; i < count; i++) {
				String key = readString(parcel);
				T obj = readParcelable(parcel,cl);
				result.put(key, obj);
			}
		}
		return result;
	}

	public static int readInt(Parcel parcel) {
		return parcel.readInt();
	}

	public static byte readByte(Parcel parcel) {
		return parcel.readByte();
	}

	public static long readLong(Parcel parcel) {
		return parcel.readLong();
	}

	public static double readDouble(Parcel parcel) {
		return parcel.readDouble();
	}

	public static boolean readBoolean(Parcel parcel) {
		return parcel.readByte() == 1;
	}

	public static String readString(Parcel parcel) {
		return parcel.readString();
	}
	
	public static IBinder readBinder(Parcel parcel, ClassLoader cl){
		boolean notNull = readBoolean(parcel);
		if(notNull){
			return parcel.readStrongBinder();
		}
		return null;
	}

	public static <T> T readParcelable(Parcel parcel,
			ClassLoader cl) {
		T result = null;
		boolean notNull = readBoolean(parcel);
		if (notNull) {
			result = parcel.readParcelable(cl);
		}
		return result;
	}

	public static Bundle readBundle(Parcel parcel) {
		boolean notNull = readBoolean(parcel);
		if (notNull) {
			return parcel.readBundle();
		}
		return null;
	}

	public static List<String> readStringList(Parcel parcel) {
		List<String> things = null;
		boolean notNull = readBoolean(parcel);
		if (notNull) {
			things = new ArrayList<String>();
			int count = readInt(parcel);
			for (int i = 0; i < count; i++) {
				things.add(readString(parcel));
			}
		}
		return things;
	}

	public static <T extends Parcelable> List<T> readList(
			Parcel parcel, ClassLoader cl) {
		List<T> things = null;
		boolean notNull = readBoolean(parcel);
		if (notNull) {
			things = new ArrayList<T>();
			int count = readInt(parcel);
			for (int i = 0; i < count; i++) {
				T thing = readParcelable(parcel, cl);
				things.add(thing);
			}
		}
		return things;
	}

	public static <T extends Parcelable> SparseArray<T> readSparseArray(
			Parcel parcel, ClassLoader cl) {
		SparseArray<T> things = null;
		boolean notNull = readBoolean(parcel);
		if (notNull) {
			things = new SparseArray<T>();
			int count = readInt(parcel);
			for (int i = 0; i < count; i++) {
				int key = readInt(parcel);
				T thing = readParcelable(parcel, cl);
				things.put(key, thing);
			}
		}
		return things;
	}

	public static SparseArray<String> readSparseString(Parcel parcel) {
		SparseArray<String> things = null;
		boolean notNull = readBoolean(parcel);
		if (notNull) {
			things = new SparseArray<String>();
			int count = readInt(parcel);
			for (int i = 0; i < count; i++) {
				int key = readInt(parcel);
				String thing = readString(parcel);
				things.put(key, thing);
			}
		}
		return things;
	}
}
