import java.io.File;
import java.io.IOException;
import java.util.List;

public class VideoManager implements Runnable{
	//Attributes
	private int vChunkLength;
	private List<Camera> vCameraList;

	//Constructor
	public VideoManager(int cLength, List<Camera> cameraList) {
		this.vChunkLength = cLength;
		this.vCameraList = cameraList;
	}

	//Getters/Setters
	public void setChunkLength(int length) {
		this.vChunkLength = length;
	}

	public int getChunkLength() {
		return this.vChunkLength;
	}

	public void setCameraList(List<Camera> cameraList) {
		this.vCameraList = cameraList;
	}

	public List<Camera> getCameraList() {
		return this.vCameraList;
	}

	//Runnable method
	@Override
	public void run() {
		if(this.vCameraList != null) {
			while(true) {
				for(Camera camera : this.vCameraList){
					if(camera.getCurrentStreamURL() != null) {
						File vidDir = new File("videos/");
						if(!vidDir.exists()){
							vidDir.mkdir();
						}
						String directoryName = camera.getName();
						File tmpDir = new File("videos/"+directoryName);
						
						if(!tmpDir.exists() || !tmpDir.isDirectory()) {
							tmpDir.mkdir();
						}
						
						try {
							System.out.println("ffmpeg -i "+ camera.getCurrentStreamURL() +" -c copy -t 00:"+(this.ms2mins(vChunkLength))+":00 -bsf:a aac_adtstoasc \"" + tmpDir.getAbsolutePath()+"/"+System.currentTimeMillis()+".mp4\"");
							Runtime.getRuntime().exec("ffmpeg -i "+ camera.getCurrentStreamURL() +" -c copy -t 00:"+(this.ms2mins(vChunkLength))+":00 -bsf:a aac_adtstoasc \"" + tmpDir.getAbsolutePath()+"/"+System.currentTimeMillis()+".mp4\"");
						} catch (IOException e) {}

						/*System.out.println("%%%");
						System.out.println("Index: " + camera.getIndex());
						System.out.println("Name: " + camera.getName());
						System.out.println("Link: " + camera.getLink());
						System.out.println("LS Link: " +camera.getCurrentStreamURL());*/
					}
				}
				try {
					Thread.sleep(vChunkLength);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}	
	
	private int ms2mins(int ms) {
		return (ms/1000)/60;
	}
}