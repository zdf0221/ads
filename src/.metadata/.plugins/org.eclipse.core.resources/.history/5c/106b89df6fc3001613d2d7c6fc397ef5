package wu.leizi.Driver;

public class DriverFactory {
	
	public static Driver factory(String[] ent) {
		if (ent[0].equals("ClientTest")) {
			System.out.print("New Client");
			return new HBClientDriver(ent[1]);
		}
		else {
			System.out.println("New Server");
			return new HBServerDriver();
		}
	}
}
