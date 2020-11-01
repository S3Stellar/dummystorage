package demo.data;

public class KeyValuePairEntity {
	private Long key;
	private String value;
	
	public KeyValuePairEntity(Long key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public KeyValuePairEntity() {
	}
	
	public Long getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
