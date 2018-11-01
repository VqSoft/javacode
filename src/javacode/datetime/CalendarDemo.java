package javacode.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.print.attribute.standard.PrinterLocation;

import javacode.Demo;

public class CalendarDemo extends Demo {
	public static void demo() {
		
		setDemo();
	}
	
	private static void setDemo(){
		Calendar cal = Calendar.getInstance();
		cal.set(2017,7,31);//2017-08-31
		cal.set(Calendar.MONTH, 8);
		
		//printLine(cal.getTime().toString());//2017-10-01
		cal.set(Calendar.DATE,5);
		
		printLine(cal.getTime().toString());//2017-09-31
	}
	
	public static void defaultTime(){
		printLine("default time:");
		Calendar calendar = Calendar.getInstance();
		
		printLine(calendar.toString());
	}
	
	public static void timeZoneAndLocal(){
		printLine("TimeZone and Local:");
		//global setting is best and safest.
		Locale.setDefault(Locale.ENGLISH);
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		
		//local setting
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"), Locale.ENGLISH);
	}
	
	public static void dateFormat(){
		printLine("Dateformat line:");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy hh:mm");
		String dtStr = "02/14/2017 1:30 AM";
		String dtStr2 = "12/30/1899 07:30";
		Date dt1 = null;
		Date dt2 = null;
		
		try {
			dt1 = sdf.parse(dtStr);
			dt2 = sdf2.parse(dtStr2);
		} catch (Exception e) {
			printLine("sdf.parse(dtStr) error: " + e.getMessage());
		}
		
		Calendar c = Calendar.getInstance();
		printLine("Calendar.getInstance() = " + c.getTime());//Calendar.getInstance() = Fri Feb 17 14:52:54 CST 2017
		c.setTime(dt1);
		printLine("After setTime, calendar is: " + c.getTime());//After setTime, calendar is: Tue Feb 14 00:00:00 CST 2017
		
		//
		Calendar c2 = Calendar.getInstance();
		c2.setTime(dt2);
		c2.set(Calendar.YEAR, c.get(Calendar.YEAR));
		c2.set(Calendar.MONTH, c.get(Calendar.MONTH));
		c2.set(Calendar.DATE, c.get(Calendar.DATE));
		printLine("After c2.set(Calendar.YEAR, c.get(Calendar.YEAR)) is : " + c2.getTime());//After c2.set(Calendar.YEAR, c.get(Calendar.YEAR)) is : Tue Feb 14 07:30:00 CST 2017
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("MM/dd/yyyy hh:mm");
		String dtStr3 = "12/30/1899 7:30";
		Date dt3 = null;
		try {
			 dt3 = sdf3.parse(dtStr3);
		} catch (Exception e) {
			printLine(e.getMessage());
		}
		
		//SimpleDateFormat("MM/dd/yyyy hh:mm").parse 12/30/1899 7:30 is: Sat Dec 30 07:30:00 CST 1899
		printLine("SimpleDateFormat(\"MM/dd/yyyy hh:mm\").parse 12/30/1899 7:30 is: " + dt3); 
		
		
	}
	
	private static void offset(){
		printLine("offset **************************");
		 //1、取得本地时间：    
	    final java.util.Calendar cal = java.util.Calendar.getInstance();   
	    System.out.println(cal.getTime());  //Fri Feb 17 16:54:32 CST 2017
	    //2、取得时间偏移量：    
	    final int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);   
	    System.out.println("zoneOffset : " + zoneOffset);  //zoneOffset : 28800000
	    //3、取得夏令时差：    
	    final int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
	    System.out.println("dstOffset : " + dstOffset);  //dstOffset : 0
	    //4、从本地时间里扣除这些差量，即可以取得UTC时间：    
	    cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));    
	    System.err.println(cal.getTime());  //Fri Feb 17 08:54:32 CST 2017
	}
	
	
}
