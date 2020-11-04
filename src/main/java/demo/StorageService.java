package demo;

import java.util.List;
import java.util.Map;

// Set the logic service
public interface StorageService {

	public KeyValuePair store(String key, Map<String, Object> value);

	public Map<String, Object> getValueByKey(String key);

	public void updateExistingValue(String key, Map<String, Object> updatedValue);

	public void deleteValueByKey(String key);

	public void deleteAll();

	public List<KeyValuePair> getAll(int size, int page);

}
