import java.util.List;

public class Manager implements Runnable{
	//Attributes
	private Crawler myCrawler;
	private VideoManager myVideoManager;
	private List<Camera> myCameras;
	private long updateInterval;
    private Thread videManagerThread;

	
	//Constructors
	public Manager(int updateInterval) {
		this.myCrawler = new Crawler();
		this.myCameras = myCrawler.getCameras();
		this.updateInterval = updateInterval;
	}
	
	public Manager(int updateInterval, int videoChunkSize) {
		this(updateInterval);
		this.myVideoManager = new VideoManager(videoChunkSize, this.myCameras);
		this.videManagerThread = new Thread(this.myVideoManager);
	}

	//Getters/Setters
	public void setUpdateIterval(long interval) {
		this.updateInterval = interval;
	}
	
	public long getUpdateInterval() {
		return this.updateInterval;
	}
	
	//Runnable method	
	@Override
	public void run() {
		if(myCameras != null) {
			boolean updated = false;
			boolean firstRun = true;
			
			while(true){
				for(Camera camera : this.myCameras) {
					long currentTime = System.currentTimeMillis();
					long tmpCamTime = camera.getStreamTime();
					
					//5mins
					if(currentTime - tmpCamTime > this.updateInterval || camera.getCurrentStreamURL() == null) {
						camera.updateStream(this.myCrawler.getM3U(camera.getLink()), System.currentTimeMillis());
						System.out.println(camera.getName() + ": " +camera.getCurrentStreamURL());
						updated = true;
					}
				}		
				
				if(firstRun) {
					this.videManagerThread.start();
					firstRun = false;
				}
				
				if(updated){
					this.myVideoManager.setCameraList(this.myCameras);
					updated = false;
					System.out.println("***\n");
				}
			}
		}		
	}
}
