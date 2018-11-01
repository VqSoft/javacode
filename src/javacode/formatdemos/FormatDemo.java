package javacode.formatdemos;

import java.text.NumberFormat;
import java.util.Locale;

import javacode.Demo;

public class FormatDemo extends Demo {

	public static void demo(){
		numberFormatDemo();
	}
	
	private static void numberFormatDemo(){
		double db = 1234000.567;
		Locale[] locales = {Locale.CHINA, Locale.JAPAN,Locale.GERMAN,Locale.US};
		NumberFormat[] nfs = new NumberFormat[12];
		for(int i = 0; i < locales.length; i ++){
			nfs[i * 3] = NumberFormat.getNumberInstance(locales[i]);
			nfs[i * 3 + 1] = NumberFormat.getPercentInstance(locales[i]);
			nfs[i * 3 + 2] = NumberFormat.getCurrencyInstance(locales[i]);
		}//for
		
		for(int i =0; i < locales.length; i ++){
			String tip = i == 0 ? "---中国的格式---" :
				i == 1 ? "---日本的格式---" :
				i == 2 ? "---德国的格式---" : "---美国的格式---";
			printLine(tip);
			printLine("数值格式化：" + nfs[i * 3].format(db));
			printLine("百分比格式化：" + nfs[i * 3 + 1].format(db));
			printLine("货币格式化：" + nfs[i * 3 + 2].format(db));
		}//for
	}//numberFormatDemo
}
