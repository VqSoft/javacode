package javacode.datetime;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

import javacode.Demo;
public class Java8DateTimeDemo extends Demo {
	
	public static void demo(){
		dateToLocalTime();
	}
	
	private static void dateToLocalTime(){
		Date date = new Date();
	
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String str = format.format(date);
		printLine(str);
	}
	
	private static void instantDateConvert(){
		LocalDateTime dt =LocalDateTime.now().withHour(12).withMinute(0).withSecond(0).withNano(0);
		dt =LocalDateTime.of(1970, 1, 1, 12, 0, 0);
		
		Instant instant = dt.atZone(ZoneId.systemDefault()).toInstant();
		Date dt1 = Date.from(instant);
		printLine(dt1.toString());
		Instant begin = Instant.now();
		
		Instant end = Instant.now();
		
		
	}
		
	private static void durationPeriodDemo(){
		LocalDate before = LocalDate.of(2017, 3, 6);
		LocalDate end = LocalDate.of(2017, 4, 6);
		Period period = Period.between(before, end);
		
		LocalDateTime beforeDt = LocalDateTime.of(2017, 3, 6,23,55,0);
		LocalDateTime endDt = LocalDateTime.of(2017, 3, 8,0,5,0);
		Duration duration = Duration.between(beforeDt, endDt);

		System.out.println("Period: month:" + period.getMonths() + " , days: " + period.getDays());
		
		System.out.println("Duration: " + " toDays:" + duration.toDays() + " , toHours: " + duration.toHours() + " ,toMinutes: " + duration.toMinutes());
		long days = duration.toMinutes() / (24 * 60);
		long mins = duration.toMinutes() % (24 * 60);
		printLine("days: "+ days + ", mins: " + mins);
	}
	
	
	public static void showCurrentDate(){
		LocalDate today = LocalDate.now();
		//直接会输出格式化的日期
		System.out.println("Current Date: " + today);// 2017-01-11
	}
	
	public static void showYearMonthDay(){
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		int month = today.getMonthValue();
		
		int day = today.getDayOfMonth();
		DayOfWeek dayOfweek = today.getDayOfWeek();
		//Year: 2017, Month: 1, DayofMonth: 11, Dayofweek: WEDNESDAY
		System.out.printf("Year: %d, Month: %d, DayofMonth: %d, Dayofweek: %s", year,month,day,dayOfweek);
		
		DayOfWeek monday = DayOfWeek.valueOf("Sunday".toUpperCase());
		//Dayofweek: THURSDAYDayOfWeek.valueOf("SUNDAY") is: SUNDAY
		System.out.println("DayOfWeek.valueOf(\"SUNDAY\") is: " + monday);
	}
	
	public static void showSpecificDate(){
		LocalDate date = LocalDate.of(999, 11, 12);
		//Specific date: 0999-11-12
		System.out.println("Specific date: " + date);
	}
	
	public static void dateEqual(){
		LocalDate today = LocalDate.now();
		LocalDate date = LocalDate.of(2017, 1, 11);
		if (today.equals(date)) {
			//Today 2017-01-11 and date1 2017-01-11 are same date.
			System.out.printf("Today %s and date1 %s are same date.", today,date);
		}
		
	}
	
	public static void repeatDay(){
		LocalDate dateOfBirth = LocalDate.of(1980, 1, 11);
		MonthDay birthday = MonthDay.of(dateOfBirth.getMonthValue(), dateOfBirth.getDayOfMonth());
		MonthDay currentMonthDay = MonthDay.from(LocalDate.now());
		if (birthday.equals(currentMonthDay)) {
			System.out.println("Today is your birthday!");
		}
		else {
			System.out.println("Today is not your birthday!");
		}
		
		
		
	}
	
	public static void showCurrentTime(){
		LocalTime time = LocalTime.now();
		//local time : 17:28:36.978   默认格式是 hh:mm:ss:nm
		System.out.println("local time : " + time);
	}
	
	public static void addTime(){
		LocalTime time = LocalTime.now();
		LocalTime newTime = time.plusHours(2);
		//local time : 20:05:25.404
		System.out.println("local time : " + newTime);
		
		LocalDate today = LocalDate.now();
		LocalDate oneWeekLater = today.plus(1, ChronoUnit.WEEKS);
		//one week later : 2017-01-18
		System.out.println("one week later : " + oneWeekLater);
		
		LocalDate oneYearLater = today.plus(1,ChronoUnit.YEARS);
		LocalDate oneYearBefore = today.minus(1,ChronoUnit.YEARS);
		
		//One year before: 2016-01-11, one year later: 2018-01-11
		System.out.printf("One year before: "+ oneYearBefore +", one year later: " + oneYearLater);
	}
	
	public static void showClock(){
		//Clock 一般用来替代System.currentTimeInMillis() 和  TimeZone.getDefault()
		Clock clockNowUTC = Clock.systemUTC();
		//Clock.systemUTC : SystemClock[Z] 
		System.out.println("Clock.systemUTC : " + clockNowUTC);
		//systemUTC clock date: 2017-01-11
		System.out.println("systemUTC clock date: " + LocalDate.now(clockNowUTC));
		
		Clock clockNowDefault = Clock.systemDefaultZone();
		//Clock.systemUTC : SystemClock[Asia/Shanghai]
		System.out.println("Clock.systemDefaultZone : " + clockNowDefault);
		//Clock.systemDefaultZone date: : 2017-01-11
		System.out.println("Clock.systemDefaultZone date: : " + LocalDate.now(clockNowDefault));
	}
	
	public static void compareDate(){
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		LocalDate yesterday = LocalDate.now().minusDays(1);
		
		LocalDate today = LocalDate.now();
		if (tomorrow.isAfter(today)) {
			//Tomorrow is after today
			System.out.println("Tomorrow is after today");
		}
		if (yesterday.isBefore(today)) {
			//Yesterday is before today
			System.out.println("Yesterday is before today");
		}
		
		LocalDateTime dt1 = LocalDateTime.parse("01/31/2017 12:30 AM",DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
		LocalDateTime dt2 =  dt1.plusMonths(1);
		

		System.out.println(dt2);
	}
	
	public static void showTimeZone(){
		//java8将日期，时间和时区进行了分离，ZoneId代表某个特定的时区
		//使用ZonedDateTime 可以将本地时间转换成另一个时区的时间
		ZoneId america = ZoneId.of("America/New_York");
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime nowUSA = ZonedDateTime.of(now, america);
		
		//Current local date time: 2017-01-11T18:50:06.007
		System.out.println("Current local date time: " + now);
		//Current USA date time: 2017-01-11T18:50:06.007-05:00[America/New_York]
		System.out.println("Current USA date time: " + nowUSA);
	}
	
	public static void showFixDate(){
		//YearMonth类可以用来找出某个月有多少天
		YearMonth currentYearMonth = YearMonth.now();
		//YearMonth.now() is: 2017-01 , the days of month is: 31
		System.out.println("YearMonth.now() is: " + currentYearMonth + " , the days of month is: " 
		+ currentYearMonth.lengthOfMonth());
		
		YearMonth ccExpire = YearMonth.of(2018, Month.FEBRUARY);
		//YearMonth.of(2018, Month.FEBRUARY): 2018-02
		System.out.println("YearMonth.of(2018, Month.FEBRUARY): " + ccExpire);
	}
	
	public static void checkIsLeapYear(){
		LocalDate today = LocalDate.now();
		System.out.println("Is this year leap year?  " + today.isLeapYear());
	}
	
	
	private static final DateTimeFormatter FormatterAM = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
	
	public static void showPeriod(){
		LocalDate today = LocalDate.now();
		LocalDate before = LocalDate.of(2014, 1, 12);
		Period period = Period.between(before, today);
		
		//Years: 2, Months: 11, Days: 30
		System.out.println("Years: " + period.getYears() +  ", Months: " + period.getMonths() + ", Days: " + period.getDays());
		
		LocalDateTime dt1 = LocalDateTime.parse("01/01/2017 09:00 AM",FormatterAM);
		LocalDateTime dt2 = LocalDateTime.parse("02/01/2017 09:10 AM",FormatterAM);
		LocalTime t1 = dt1.toLocalTime();
		LocalTime t2 = dt2.toLocalTime();
		Duration d01 = Duration.between(t1, t2);
		System.out.println("t2 - t1 = " + d01.toMinutes());
		
 		
		Period p2 = Period.between(dt1.toLocalDate(), dt2.toLocalDate());
		
		System.out.println("Period : p2.getMonths() = " + p2.getMonths() + ", p2.getDays() = " + p2.getDays());
		
		Duration duration = Duration.between(dt1, dt2);
		System.out.println("duration.toHours() : " + duration.toHours());
		System.out.println("duration.toDays() : " + duration.toDays());
		
		LocalDateTime dt3 = dt1.plus(duration);
		System.out.println(dt3);
		
		LocalDateTime dt4 = LocalDateTime.parse("01/18/2017 00:00 AM",FormatterAM);
		LocalDateTime dt5 = dt4.plusMinutes(dt1.getMinute());
		System.out.println(dt5);
		
		LocalDateTime dt01 = LocalDateTime.parse("01/18/2017 10:00 AM",FormatterAM);
		System.out.println("dt1.equals(dt01) :" + dt1.equals(dt01));
		System.out.println("dt1.isEqual(dt01) :" + dt1.isEqual(dt01));
		
		LocalDateTime dt02Start = LocalDateTime.parse("07/15/2016 09:00 AM",FormatterAM);
		LocalDateTime dt02 = LocalDateTime.parse("09/30/2016 05:00 PM",FormatterAM);
		System.out.println("09/30/2016 05:00 PM plus 1 month is : " + dt02.plusMonths(1));
		Duration d02 = Duration.between(dt02Start, dt02);
		LocalDateTime dt03Start = LocalDateTime.parse("09/15/2016 09:00 AM",FormatterAM);
		System.out.println("09/15/2016 09:00 AM plus duration is : " + dt03Start.plus(d02));
		System.out.println("LocalDateTime.parse(\"09/31/2016 09:00 AM\" is : " + LocalDateTime.parse("09/31/2016 09:00 AM",FormatterAM));
	}
	
	public static void showOffseTime(){
		//ZoneOffset 代表某个时区，OffsetDateTime 是给机器用的的，ZonedDateTime 是给人看的。
		LocalDateTime today = LocalDateTime.now();
		ZoneOffset offset = ZoneOffset.of("+05:30");
		OffsetDateTime offsetDateTime = OffsetDateTime.of(today, offset);
		//OffsetDateTime.of(today, offset) is:  2017-01-11T19:10:52.655+05:30
		System.out.println("OffsetDateTime.of(today, offset) is:  " + offsetDateTime);
		
		ZonedDateTime zonedDateTime = ZonedDateTime.of(today, offset);
		//ZonedDateTime.of(today, offset) is:  2017-01-11T19:10:52.655+05:30
		System.out.println("ZonedDateTime.of(today, offset) is:  " + zonedDateTime);
	}
	
	public static void getTimestamp(){
		Instant ts = Instant.now();
		//Current instant :  2017-01-11T11:13:05.950Z , the time has minus 8 hours, so it's UTC timestamp
		System.out.println("Current instant :  " + ts);
	}
	
	public static void useDateFormat(){
		String dateStr = "20170113";
		LocalDate formatDate = LocalDate.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);
		//20170113use DateTimeFormatter.BASIC_ISO_DATE) :  2017-01-13
		System.out.println(dateStr +  "use DateTimeFormatter.BASIC_ISO_DATE) :  " + formatDate);
		
		DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
		LocalDateTime date2 = LocalDateTime.parse("01/17/2017 12:30 AM",fmt1);
		System.out.println(date2);
		
		LocalDateTime date3 = LocalDateTime.parse("01/17/2017 00:00 AM",fmt1);
		System.out.println(date3);
		
//		DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//		LocalDateTime date3 = LocalDateTime.parse("01/17/2017",dateFmt);
//		System.out.println(" LocalDateTime.parse(\"2017-01-11\",dateFmt) is : " + date3);
	}
	
	public static void useCustomFormat(){
		String dateStr = "01 18 2016";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy");
		LocalDate date = LocalDate.parse(dateStr,formatter);
		//01 18 2016use DateTimeFormatter.ofPattern("MM dd yyyy") :  2016-01-18
		System.out.println(dateStr +  "use DateTimeFormatter.ofPattern(\"MM dd yyyy\") :  " + date);
		
		LocalDate date2 = LocalDate.parse("2016-01-31");
		System.out.println("LocalDate.parse(\"2016-01-31\") is : " + date2);
		
		DateTimeFormatter fmt3 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate date3 = LocalDate.parse("11/04/2016",fmt3);
		System.out.println("LocalDate.parse(\"11/04/2016\") is : " + date3);
		
	}
	
	public static void formatDate(){
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");
		
		String dateStr = date.format(format);
		//format date is :Jan 11 2017 07:26 PM
		
		
		System.out.println("format date is :" + dateStr);
	}
}
