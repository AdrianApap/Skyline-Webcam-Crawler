import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
	private final String BASE_URL = "https://www.skylinewebcams.com";
	
	public Crawler() {}

	/**
	 * Crawls the main page to obtain the page URLs of the camera live streams
	 * @return a List of Camera URLs
	 */
	public List<String> getCamLinks() {
		List<String> result = null;
		Document doc;

		try {
			doc = Jsoup.connect("https://www.skylinewebcams.com/en/webcam/malta/malta/traffic.html").userAgent(USER_AGENT).get();
			result = new ArrayList<String>();
			Elements webcams = doc.getElementsByClass("webcam");

			for(Element element : webcams) {
				String tmpLink = element.getElementsByTag("a").attr("href");
				if(!tmpLink.equalsIgnoreCase("#")) {
					result.add(BASE_URL+tmpLink);
				}
			}

		} catch (IOException e) {}

		return result;
	}
	
	/**
	 * Crawls the main page to obtain the page URLs of the camera live streams
	 * @return a List of Camera URLs
	 */
	public List<Camera> getCameras() {
		List<Camera> result = null;
		Document doc;

		try {
			doc = Jsoup.connect("https://www.skylinewebcams.com/en/webcam/malta/malta/traffic.html").userAgent(USER_AGENT).get();
			result = new ArrayList<Camera>();
			Elements webcams = doc.getElementsByClass("webcam");

			int count = 0;
			for(Element element : webcams) {
				String tmpLink = element.getElementsByTag("a").attr("href");
				
				if(!tmpLink.equalsIgnoreCase("#")) {
					String tmpName = element.getElementsByClass("title").get(0).html();
					result.add(new Camera(BASE_URL+tmpLink, tmpName, count));
					count ++;
				}
			}

		} catch (IOException e) {}

		return result;
	}

	/**
	 * Returns a list of live stream links
	 * @param camList The list of page URLs of the cameras
	 * @return a String list of the live stream links
	 */
	public List<String> getM3U(List<String> camList) {
		List<String> result = new ArrayList<String>();

		for(String link : camList) {		
			result.add(this.getM3U(link));
		}

		return result;
	}

	/**
	 * Returns a string link of the camera live stream
	 * @param camUrl The page URL of the camera
	 * @return the string link of the camera stream
	 */
	public String getM3U(String camUrl) {
		try {
			Document doc = Jsoup.connect(camUrl).userAgent(USER_AGENT).get();
			String html = doc.toString();

			int camIdx = html.indexOf("https://hddn01.skylinewebcams.com/live.m3u8");
			if(camIdx != -1){
				int endIdx = html.indexOf("}", camIdx);
				if(endIdx != -1) {
					endIdx -= 1;
					return html.substring(camIdx, endIdx);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}	
}
