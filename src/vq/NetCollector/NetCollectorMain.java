package vq.NetCollector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Main class for net collector
 * @author Thinkpad
 *
 */
public final class NetCollectorMain {
	
	private NetCollectorMain(){}
	
	public static NetCollectorMain get(){
		NetCollectorMain instance = new NetCollectorMain();
		return instance;
	}
	
	private String pageUrl;
	
	public  void run() throws MalformedURLException{
		readConfig();
		
		fetchPages();
	}
	
	private void readConfig(){
		pageUrl = "https://mp.weixin.qq.com/s?__biz=MzI5ODEwMDQyNw==&mid=403085376&idx=3&sn=fc8d561c96fb261d4367f8f25f7efa9d&scene=21#wechat_redirect";
	}
	
	private void fetchPages() throws MalformedURLException{
		
		URL url = new URL(pageUrl);
		try (
				
				InputStreamReader isr = new InputStreamReader(url.openStream(), "utf-8");
				BufferedReader br = new BufferedReader(isr);	
			){
			StringBuilder content = new StringBuilder();
			String tmpStr = null;
			while ((tmpStr = br.readLine()) != null) {
				content.append(tmpStr);
				System.out.print(tmpStr);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}//class
