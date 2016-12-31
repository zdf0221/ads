package wu.leizi.project.offline;

import wu.leizi.Driver.HeartBeat.HBClientDriver;


public class offlineComput extends HBClientDriver {
	private volatile static offlineComput instance = null;
	private Thread schedulerThread = null;
	private adsStatisDriver staticsInfo = null;
	private offlineBehaviorTarget BT = null;
	private static final int checkIntrval = 10000;
	public static offlineComput getInstance() {
		if (instance == null) instance = new offlineComput(null);
		return instance;
	}
	
	private offlineComput(String id) {
		super("offlineComput");
		// TODO Auto-generated constructor stub
		staticsInfo = new adsStatisDriver("offlineComput");
		BT = new offlineBehaviorTarget("offlineComput");
	}
	
	public void start() {
		super.put();
		schedulerThread = new Thread(new Scheduler());
		schedulerThread.start();
	}
	
	
	public String ServiceStatus() {
		return super.ServiceStatus() + " scheduler Thread:"
				+ (schedulerThread != null || schedulerThread.isAlive());
	}
	
	class Scheduler implements Runnable {
		
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					Thread.sleep(checkIntrval);
					staticsInfo.start();
					staticsInfo.ctrStatis();
					BT.start();
					BT.offlineBT();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				staticsInfo.close();
				BT.close();
			}
		}
		
		
	}

}
