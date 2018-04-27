
public class Runner {

	public static void main(String[] args) {
		//Manager myLinkManager = new Manager(300000, 600000);
		Manager myLinkManager = new Manager(300000, 120000); //5mins link update 2 min video chunks
	    Thread slUpdateThread = new Thread(myLinkManager);
	    slUpdateThread.start();
	    
		//cc.getM3U(cc.getCamLinks());
	}
}
