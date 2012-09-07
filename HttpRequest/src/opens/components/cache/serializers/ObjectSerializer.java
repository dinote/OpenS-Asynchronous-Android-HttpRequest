package opens.components.cache.serializers;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectSerializer implements CacheSerializer {

	private static ObjectSerializer instance;
	
	public static ObjectSerializer instance() {
		if (instance == null) {
			instance = new ObjectSerializer();
		}
		return instance;
	}
	
	public Object deserializeFromInputStream(InputStream is) throws IOException {		
		ObjectInputStream ois = new ObjectInputStream(is);
		Object object = null;
		try {
			object = ois.readObject();
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} finally {
			ois.close();
		}
		return object;
	}

	public void serializeToOutputStream(OutputStream os, Object object)
			throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(object);
		oos.close();
	}

}
