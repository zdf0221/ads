package wu.leizi.data;

public interface Data {
	public <T> void put(String key, T value);
	public <T> T get(String key);
}
