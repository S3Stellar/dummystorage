package demo;

import java.util.Map;

public class KeyValuePair {
	private String key;
	private Map<String, Object> value;

	public KeyValuePair() {
	}

	public KeyValuePair(String key, Map<String, Object> value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, Object> getValue() {
		return value;
	}

	public void setValue(Map<String, Object> value) {
		this.value = value;
	}

}
