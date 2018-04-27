
public class Camera {
	//Attributes
	private String cLink;
	private String cName;
	private String cCurrentStreamURL;
	private long cStreamTime;
	private int cIndex;
	
	//Constructors
	public Camera() {
		this.cLink = "";
		this.cName = "";
		this.cStreamTime = System.currentTimeMillis();
		this.cIndex = 0;
	}
	
	public Camera(String link, String name, int idx) {
		this();
		this.cLink = link;
		this.cName = name;
		this.cIndex = idx;
	}
	
	//getters/setters
	public void setLink(String link) {
		this.cLink = link;
	}
	
	public void setName(String name) {
		this.cName = name;
	}
	
	public void setCurrentStreamURL(String link) {
		this.cCurrentStreamURL = link;
	}
	
	public void setStreamTime(long time) {
		this.cStreamTime = time;
	}
	
	public void setIndex(int idx) {
		this.cIndex = idx; 
	}
	
	public String getLink() {
		return this.cLink;
	}
	
	public String getName() {
		return this.cName;
	}
	
	public String getCurrentStreamURL() {
		return this.cCurrentStreamURL;
	}
	
	public long getStreamTime() {
		return this.cStreamTime;
	}
	
	public int getIndex() {
		return this.cIndex;
	}
	
	//Other
	public void updateStream(String URL, long time){
		this.cCurrentStreamURL = URL;
		this.cStreamTime = time;
	}
}
