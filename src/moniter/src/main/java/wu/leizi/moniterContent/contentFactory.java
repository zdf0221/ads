package wu.leizi.moniterContent;

public class contentFactory {
	private static contentFactory instance = null;
	private contentFactory() {
		
	}
	public static contentFactory getInstance() {
		return instance;
	}
	public HBContent createMC() {
		return new HBContent();
	}
}
