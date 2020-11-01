package demo.logic;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.KeyValuePair;
import demo.StorageService;
import demo.data.KeyValuePairEntity;
import demo.data.StorageConverter;
import demo.validations.KeyNotFoundException;

@Service
public class StorageServiceMockup implements StorageService {

	private Map<Long, KeyValuePairEntity> database;
	private StorageConverter storageConverter;
	private AtomicLong nextId;

	
	public StorageServiceMockup() {
	}

	@PostConstruct
	public void init() {
		database = Collections.synchronizedMap(new TreeMap<>());
		nextId = new AtomicLong(0L);
	}

	@Autowired
	public void setStorageConverter(StorageConverter storageConverter) {
		this.storageConverter = storageConverter;
	}

	@Override
	public KeyValuePair store(String key, Map<String, Object> value) {
		Long newId = nextId.getAndIncrement();
		KeyValuePair kvp = new KeyValuePair(key, value);
		KeyValuePairEntity kvpEntity = this.storageConverter.convertToEntity(kvp);
		kvpEntity.setKey(newId);
		this.database.put(newId, kvpEntity);
		return this.storageConverter.convertFromEntity(kvpEntity);
	}

	@Override
	public Map<String, Object> getValueByKey(String key) {
		KeyValuePairEntity kvpEntity = this.database.get(this.storageConverter.toEntityId(key));
		if (kvpEntity != null) {
			return this.storageConverter.convertFromEntity(kvpEntity).getValue();
		} else {
			throw new KeyNotFoundException("Could not find this key");
		}
	}

	@Override
	public void updateExistingValue(String key, Map<String, Object> updatedValue) {
		if (key != null && this.database.containsKey(this.storageConverter.toEntityId(key))) {
			KeyValuePair kvp = new KeyValuePair(key, updatedValue);
			KeyValuePairEntity kvpEntity = this.storageConverter.convertToEntity(kvp);
			this.database.put(this.storageConverter.toEntityId(key), kvpEntity);
		}
	}

	@Override
	public void deleteValueByKey(String key) {
		if (this.database.remove(this.storageConverter.toEntityId(key)) == null) {
			throw new KeyNotFoundException("Could not find this key");
		}
	}

	@Override
	public void deleteAll() {
		this.database.clear();
	}

	@Override
	public List<KeyValuePair> getAll(int size, int page) {

		return this.database.values().stream().skip(size * page).limit(size)
				.map(this.storageConverter::convertFromEntity).collect(Collectors.toList());
	}
}
