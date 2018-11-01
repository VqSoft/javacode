package javacode.internationaldemo;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javacode.Demo;

public class InternationalDemo extends Demo {

	public static void demo(){
		messageFormatDemo();
	}
	
	private static void showAvailableCountry(){
		Locale[] locales = Locale.getAvailableLocales();
		for (Locale locale : locales) {
			printLine(locale.getDisplayCountry() + "=" + locale.getCountry()
					+ ","+locale.getDisplayLanguage() + "=" + locale.getLanguage());
		}
	}
	
	private static void localeDemo(){
		Locale defaultLocale = Locale.getDefault(Locale.Category.FORMAT);
		ResourceBundle bundle = ResourceBundle.getBundle("mess", defaultLocale);
		printLine(bundle.getString("hello"));
	}
	
	private static void messageFormatDemo(){
		Locale myLocale = Locale.getDefault(Locale.Category.FORMAT);
		ResourceBundle bundle = ResourceBundle.getBundle("mess", myLocale);
		String msg = bundle.getString("msg");
		printLine(MessageFormat.format(msg, "Eric",new Date()));//你好，Eric!今天是17-3-12 下午9:59
	}
}
