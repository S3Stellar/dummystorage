package demo;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {
	private StorageService storageService;

	@RequestMapping(
			path = "/keyValueStore/{key}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public KeyValuePair store (
			@PathVariable("key") String key, 
			@RequestBody Map<String, Object> value){
		return this.storageService.store(key, value);
	}
	
	@RequestMapping(
			path = "/keyValueStore/{key}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getValueByKey (
			@PathVariable("key") String key){
		return this.storageService
				.getValueByKey(key);
	}
	
	@RequestMapping(
			path = "/keyValueStore",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public KeyValuePair[] getAll (
			@RequestParam(name="size", required = false, defaultValue = "10") int size, 
			@RequestParam(name="page", required = false, defaultValue = "0") int page){
		return this.storageService
				.getAll(size, page)
				.toArray(new KeyValuePair[0]);
	}
	
	@RequestMapping(
			path = "/keyValueStore/{key}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateExistingValue (
			@PathVariable("key") String key, 
			@RequestBody Map<String, Object> updatedValue) {
		this.storageService
			.updateExistingValue(key, updatedValue);
	}
	
	@RequestMapping(
			path = "/keyValueStore/{key}",
			method = RequestMethod.DELETE)
	public void deleteValueByKey (
			@PathVariable("key") String key){
		this.storageService
			.deleteValueByKey (key);
	}
	
	@RequestMapping(
			path = "/keyValueStore",
			method = RequestMethod.DELETE)
	public void deleteAll (){
		this.storageService
			.deleteAll();
	}
}






