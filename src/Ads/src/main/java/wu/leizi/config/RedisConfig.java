package wu.leizi.config;

import redis.clients.jedis.Jedis;
public class RedisConfig implements config {
	private String host;
	private Jedis jedis;
	private static RedisConfig instance = null;
	private RedisConfig() {
		host = "localhost";
		jedis = new Jedis(host);
	}
	
	public static RedisConfig getInstance() {
		if (instance == null) instance = new RedisConfig();
		return instance;
	}
	
	public String getHostName() {
		return host;
	}
	
	public Jedis getJedis() {
		if (jedis == null || !jedis.isConnected()) jedis = new Jedis(host);
		return jedis;
	}
}
