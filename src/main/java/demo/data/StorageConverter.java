package demo.data;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.KeyValuePair;

@Component
public class StorageConverter {
	private ObjectMapper jackson;

	@PostConstruct
	public void setup() {
		jackson = new ObjectMapper();
	}

	public KeyValuePair convertFromEntity(KeyValuePairEntity kvpEntity) {
		return new KeyValuePair(this.fromEntityId(kvpEntity.getKey()), unMarshKvpAttr(kvpEntity.getValue()));
	}

	public KeyValuePairEntity convertToEntity(KeyValuePair kvp) {
		return new KeyValuePairEntity(this.toEntityId(kvp.getKey()), marshKvpAttr(kvp.getValue()));
	}

	private Map<String, Object> unMarshKvpAttr(String actionAttributes) {
		Map<String, Object> actionUnMarshaling;
		try {
			actionUnMarshaling = jackson.readValue(actionAttributes, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return actionUnMarshaling;
	}

	public String marshKvpAttr(Map<String, Object> actionAttributes) {
		String actionMarshaling;
		try {
			actionMarshaling = jackson.writeValueAsString(actionAttributes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return actionMarshaling;
	}

	public Long toEntityId(String actionId) {
		if (actionId != null) {
			return Long.parseLong(actionId);
		} else {
			return null;
		}
	}

	public String fromEntityId(Long actionId) {
		if (actionId != null) {
			return actionId.toString();
		} else {
			return null;
		}
	}
}
