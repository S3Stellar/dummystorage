package demo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApplicationTests {
	private RestTemplate restTemplate;
	private String url = "localhost/keyValueStore/";
	private int port;

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@PostConstruct
	public void init() {
		this.restTemplate=new RestTemplate();
		this.url = "http://localhost:" + this.port + "/keyValueStore/";
	}
	
	@AfterEach
	public void teardown() {
		this.restTemplate.delete(this.url);
	}
	
	@Test
	public void create_one_pair_and_check_length() throws Exception {
		String key = "1";
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("test", 123);
		this.restTemplate.postForObject(this.url+key, value, KeyValuePair.class);
		KeyValuePair[] allPairs = this.restTemplate.getForObject(this.url, KeyValuePair[].class);
		if(allPairs.length!=1)
			throw new Exception("not good");
	}
	
	@Test
	public void create_pair_and_check_if_value_matches() throws Exception {
		String key = "1";
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("test", 123);
		KeyValuePair kvp = this.restTemplate.postForObject(this.url+key, value, KeyValuePair.class);
		Map<String, Object> valFromDB = this.restTemplate.getForObject(this.url+kvp.getKey(), Map.class);
		if(value.get("test")!=valFromDB.get("test"))
			throw new Exception("not equal");
	}
	
	@Test
	public void create_pair_update_and_check_if_updated() throws Exception {
		String key = "1";
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("test", 123);
		KeyValuePair kvp = this.restTemplate.postForObject(this.url+key, value, KeyValuePair.class);
		value.put("test", 124);
		this.restTemplate.put(this.url+kvp.getKey(), value);
		Map<String, Object> valFromDB = this.restTemplate.getForObject(this.url+kvp.getKey(), Map.class);
		if(value.get("test")!=valFromDB.get("test"))
			throw new Exception("not equal");
	}
	
	@Test
	public void create_pair_delete_and_check_if_deleted() throws Exception{
		String key = "1";
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("test", 123);
		KeyValuePair kvpFromDB = this.restTemplate.postForObject(this.url+key, value, KeyValuePair.class);
		this.restTemplate.delete(this.url + kvpFromDB.getKey());
		KeyValuePair[] allPairs = this.restTemplate.getForObject(this.url, KeyValuePair[].class);
		if(allPairs.length!=0)
			throw new Exception("not good");
		
	}
	

}
