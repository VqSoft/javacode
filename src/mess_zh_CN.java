import java.util.ListResourceBundle;

public class mess_zh_CN extends ListResourceBundle {
	
	private final Object myData[][] = {
			{"msg","消息内容"}	
	};
	
	@Override
	protected Object[][] getContents() {
		return myData;
	}
}
