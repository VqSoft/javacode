package vq.tools;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Generate recurring date times based on some special pattern
 * @author ezhao3
 *
 */
public class RecurringDateTimeTools {
	
	/**
	 * Repeat type
	 * @author ezhao3
	 *
	 */
	public static enum EnumRecurringRepeatType {
		
		enmDaily(1,"Daily"),
		enmWeekly(2,"Weekly"),
		enmMonthly(3,"Monthly"),
		enumSelectDates(4,"On Selected Dates");
		
		private int index;
		private String name;
		
		public int getIndex() {
			return index;
		}

		public String getName() {
			return name;
		}
		
		public static EnumRecurringRepeatType getByName(String name){
			for (EnumRecurringRepeatType recurType : EnumRecurringRepeatType.values()) {
				if (recurType.name.equalsIgnoreCase(name)) {
					return recurType;
				}
			}
			throw new IllegalArgumentException("name : " + name + " is invalid.");
		}
		
		public static EnumRecurringRepeatType getByIndex(int idx){
			for (EnumRecurringRepeatType recurType : EnumRecurringRepeatType.values()) {
				if (recurType.index == idx) {
					return recurType;
				}
			}
			throw new IllegalArgumentException("index :" + idx + " is invalid.");
		}
		
		private EnumRecurringRepeatType(int idx,String name){
			this.index = idx;
			this.name = name;
		}
	}

	/**
	 * the datetime of pattern only contains date info, not contain time info,
	 * time is always 00:00
	 */
	private RecurringPattern pattern;
	
	private List<RecurringDateTime> base;
	
	public RecurringPattern getPattern() {
		return pattern;
	}

	public void setPattern(RecurringPattern pattern) {
		this.pattern = pattern;
	}

	public List<RecurringDateTime> getBase() {
		return base;
	}

	public void setBase(List<RecurringDateTime> base) {
		this.base = base;
	}
	
	public String valid(){
		StringBuilder errMsg = new StringBuilder();

		errMsg.append(this.pattern.valid());
		
		//check duration must <= frequency
		//check date range not supported. 
		if (pattern.getType() == EnumRecurringRepeatType.enmDaily) {
			errMsg.append(validDaily());
		}
		else if (pattern.getType() == EnumRecurringRepeatType.enmWeekly){
			errMsg.append(validWeekly());
		}
		else if (pattern.getType() == EnumRecurringRepeatType.enmMonthly){
			errMsg.append(validMonthly());
		}
		else if (pattern.getType() == EnumRecurringRepeatType.enumSelectDates) {
			errMsg.append(this.validSelectDate());	
		}
		
		//check base
		return errMsg.toString();
	}
	
	
	private final static String ERR_DURATION_OVER_FREQUENCY = "The duration of booking must be shorter than how frequently it occurs. Please review your duration and change the recurrence pattern.";
	
	private String validDaily(){
		String errMsg = "";
		
		for (RecurringDateTime recurringBase : base) {
		
			Duration duration = Duration.between(recurringBase.getStartScheduleDatetime(), 
					recurringBase.getEndScheduleDatetime());
			
			int durationDays = (int)duration.toHours() / 24;
			
			if(durationDays < pattern.getFrequency()){
				return errMsg;
			}
			
			if (durationDays > pattern.getFrequency()) {
				return ERR_DURATION_OVER_FREQUENCY;
			}
			
			if (isEndTimeLargerThanStartTime(recurringBase.getStartScheduleDatetime().toLocalTime(),
					recurringBase.getEndScheduleDatetime().toLocalTime())) {
				return ERR_DURATION_OVER_FREQUENCY;
			}
			
		}
		
		return errMsg;
	}
	
	private String validWeekly(){
		String errMsg = "";
		
		for (RecurringDateTime recurringBase : base) {
			Duration duration = Duration.between(recurringBase.getStartScheduleDatetime(), 
					recurringBase.getEndScheduleDatetime());
			
			int durationDays = (int)duration.toHours() / 24;
			
			if(durationDays < pattern.getFrequency() * 7){
				return errMsg;
			}
			
			if (durationDays > pattern.getFrequency() * 7) {
				return ERR_DURATION_OVER_FREQUENCY;
			}
			
			if (isEndTimeLargerThanStartTime(recurringBase.getStartScheduleDatetime().toLocalTime(),
					recurringBase.getEndScheduleDatetime().toLocalTime())) {
				return ERR_DURATION_OVER_FREQUENCY;
			}
		}
		
		return errMsg;
	}
	
	private String validMonthly(){
		String errMsg = "";
		
		for (RecurringDateTime recurringBase : base) {
			Period p = Period.between(recurringBase.getStartScheduleDatetime().toLocalDate(), 
					recurringBase.getEndScheduleDatetime().toLocalDate());
			
			if (p.getMonths() < pattern.getFrequency()) {
				return errMsg;
			}
			
			if (p.getMonths() > pattern.getFrequency()) {
				return ERR_DURATION_OVER_FREQUENCY;
			}
			
			if (p.getDays() > 0) {
				return ERR_DURATION_OVER_FREQUENCY;
			}
			
			if (isEndTimeLargerThanStartTime(recurringBase.getStartScheduleDatetime().toLocalTime(),
					recurringBase.getEndScheduleDatetime().toLocalTime())) {
				return ERR_DURATION_OVER_FREQUENCY;
			}
			
		}
		
		return errMsg;
	}
	
	private boolean isEndTimeLargerThanStartTime(LocalTime startTime,LocalTime endTime){
		Duration duration = Duration.between(startTime, endTime);
		return duration.toMinutes() > 0;
	}
	
	private String validSelectDate(){
		String errMsg = "";
		List<LocalDateTime> sortedSelectDates = pattern.getDates().stream().sorted().collect(Collectors.toList());
		for (RecurringDateTime recurringBase : base) {
			Duration duration = Duration.between(recurringBase.getStartScheduleDatetime(), recurringBase.getEndScheduleDatetime());
			LocalDateTime startDateTime = recurringBase.getStartScheduleDatetime();
			
			for(int i =0 ; i < sortedSelectDates.size() -1; i ++){
				LocalDateTime first = sortedSelectDates.get(i).plusHours(startDateTime.getHour()).plusMinutes(startDateTime.getMinute());
				LocalDateTime second = sortedSelectDates.get(i + 1);
				if (first.plus(duration).isAfter(second)) {
					//TODO need BA confirm err msg
					errMsg = ERR_DURATION_OVER_FREQUENCY;
				}
			}
		}
		
		return errMsg;
	}
	
	public List<RecurringDateTime> generateRecurringBooking() throws Exception{
		
		EnumRecurringRepeatType repeatType = pattern.getType();
		List<RecurringDateTime> list = null;
		
		if (repeatType == EnumRecurringRepeatType.enmDaily ) {
			list = generateRecurringBookingDaily();
		}
		else if (repeatType == EnumRecurringRepeatType.enmWeekly) {
			list = generateRecurringBookingWeekly();
		}
		else if (repeatType == EnumRecurringRepeatType.enmMonthly) {
			list = generateRecurringBookingMonthly();
		}
		else if (repeatType == EnumRecurringRepeatType.enumSelectDates){
			list = generateRecurringBookingSelectDates();
		}
		else {
			throw new Exception("Invalid EnumRecurringRepeatType: " + repeatType);
		}
		
		if (list == null || list.size() <1) {
			throw new Exception("No recurrence will be generated before end date. Please change the end date of recurrence pattern." );
		}
		
		int totalBookingCount = list.size() + base.size();
		if (totalBookingCount > 100) {
			throw new Exception("exceed the count");
		}
		
		return list;
		
	}
	
	private List<RecurringDateTime> generateRecurringBookingDaily(){
		List<RecurringDateTime> bookings = new ArrayList<RecurringDateTime>();
		
		Comparator<LocalDateTime> copareDate =  new Comparator<LocalDateTime>() {
			public int compare(LocalDateTime o1, LocalDateTime o2) {
				return o1.isAfter(o2) ? 1 : -1;
			};
		};
		LocalDateTime baseEndDateTime = base.stream().map(bo -> bo.getEndScheduleDatetime()).max(copareDate).get();
		
		int frequency = pattern.getFrequency();
		int repeatCount = pattern.getCount();
		
		LocalDateTime lastEndDate = null;
		if (pattern.getCount() > 0) {
			lastEndDate = baseEndDateTime.plusDays(frequency * repeatCount);
		}
		else {
			lastEndDate = pattern.getEndAt().plusHours(baseEndDateTime.getHour()).plusMinutes(baseEndDateTime.getMinute());
		}
		
		LocalDateTime guardDate = baseEndDateTime.plusDays(frequency);
		int tmpCount = 1;
		
		while(!guardDate.isAfter(lastEndDate)){
			for (RecurringDateTime recurringBase : base) {
				RecurringDateTime book = recurringBase.cloneSelf();
				
				book.setStartEventDatetime(recurringBase.getStartEventDatetime().plusDays(tmpCount * frequency));
				book.setEndEventDatetime(recurringBase.getEndEventDatetime().plusDays(tmpCount * frequency));
				book.setStartScheduleDatetime(recurringBase.getStartScheduleDatetime().plusDays(tmpCount * frequency));
				book.setEndScheduleDatetime(recurringBase.getEndScheduleDatetime().plusDays(tmpCount * frequency));
				
				bookings.add(book);
			}
			
			guardDate = guardDate.plusDays(frequency);
			tmpCount++;
		}//while
		
		return bookings;
	}

	private List<RecurringDateTime> generateRecurringBookingWeekly(){
		List<RecurringDateTime> bookings = new ArrayList<RecurringDateTime>();
		
		Comparator<LocalDateTime> copareDate =  new Comparator<LocalDateTime>() {
			public int compare(LocalDateTime o1, LocalDateTime o2) {
				return o1.isAfter(o2) ? 1 : -1;
			};
		};
		//LocalDateTime baseStartDateTime = base.stream().map(bo -> bo.getStartScheduleDatetime()).min(copareDate).get();
		LocalDateTime baseEndDateTime = base.stream().map(bo -> bo.getEndScheduleDatetime()).max(copareDate).get();

	
		
		int frequency = pattern.getFrequency();
		int repeatCount = pattern.getCount();
		LocalDateTime lastEndDate = null;
		
		if (pattern.getCount() > 0) {
			lastEndDate = baseEndDateTime.plusDays(7 * frequency * repeatCount);
		}
		else {
			lastEndDate = pattern.getEndAt().plusHours(baseEndDateTime.getHour()).plusMinutes(baseEndDateTime.getMinute());
		}
		
		LocalDateTime guardDate = baseEndDateTime.plusDays(7 * frequency);
		int tmpCount = 1;
		
		while(!guardDate.isAfter(lastEndDate)){
			for (RecurringDateTime recurringBase : base) {
				RecurringDateTime book = recurringBase.cloneSelf();
				
				book.setStartEventDatetime(recurringBase.getStartEventDatetime().plusDays(7 * tmpCount * frequency));
				book.setEndEventDatetime(recurringBase.getEndEventDatetime().plusDays(7 * tmpCount * frequency));
				book.setStartScheduleDatetime(recurringBase.getStartScheduleDatetime().plusDays(7 * tmpCount * frequency));
				book.setEndScheduleDatetime(recurringBase.getEndScheduleDatetime().plusDays(7 * tmpCount * frequency));
				
				bookings.add(book);
			}
			
			guardDate = guardDate.plusDays(7 * frequency);
			tmpCount++;
		}//while
		
		return bookings;
	}
	
	private List<RecurringDateTime> generateRecurringBookingMonthly(){
		List<RecurringDateTime> bookings = new ArrayList<RecurringDateTime>();
		
		//repeat N times. *****************************************
		if (pattern.getCount() > 0) {
			int repeatCount = pattern.getCount();
			int tmpCount = 1;
			while(tmpCount <= repeatCount){
				appendBookingMonthly(bookings,null,null);
				
				tmpCount++;
			}//while
			
			return bookings;
		}//if Repeat N times
		
		//repeat until end date. ***************************************************
		Comparator<LocalDateTime> copareDate =  new Comparator<LocalDateTime>() {
			public int compare(LocalDateTime o1, LocalDateTime o2) {
				return o1.isAfter(o2) ? 1 : -1;
			};
		};
		LocalDateTime baseEndDateTime = base.stream().map(bo -> bo.getEndScheduleDatetime()).max(copareDate).get();
		LocalDateTime lastEndDate = pattern.getEndAt().plusHours(baseEndDateTime.getHour()).plusMinutes(baseEndDateTime.getMinute());
		LocalDateTime guardDate = baseEndDateTime;
		while(!appendBookingMonthly(bookings,lastEndDate,guardDate)){
			
		}//while
		
		return bookings;
	}
	
	public static int getMonthDays(LocalDate dt){
		switch (dt.getMonthValue()) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			return (dt.isLeapYear() ? 29 : 28);
		default:
			return -1;
		}
	}
	
	/**
	 * 
	 * @param bookings
	 * @param lastEndDate
	 * @param guardDate
	 * @return True: means should terminate as the date has after the end date. 
	 * False: should continue 
	 */
	private boolean appendBookingMonthly(List<RecurringDateTime> bookings,LocalDateTime lastEndDate ,LocalDateTime guardDate){
		int frequency = pattern.getFrequency();
		LocalDateTime tmpStartEventDate = null;
		LocalDateTime tmpEndEventDate = null;
		LocalDateTime tmpStartDate = null;
		LocalDateTime tmpEndDate = null;
		
		for (RecurringDateTime recurringBase : base) {
			RecurringDateTime book = recurringBase.cloneSelf();
			
			//unit is month , we should handle this special case
			boolean isMonthUnit = recurringBase.getReservationPeriodUnit() == 1;
			
			Duration durationOfEventStartEnd = Duration.between(recurringBase.getStartEventDatetime(), recurringBase.getEndEventDatetime());
			Duration durationOfStartEnd = Duration.between(recurringBase.getStartScheduleDatetime(), recurringBase.getEndScheduleDatetime());
			
			tmpStartEventDate = recurringBase.getStartEventDatetime().plusMonths(frequency);
			
			tmpEndEventDate =isMonthUnit ? recurringBase.getEndEventDatetime().plusMonths(frequency) : tmpStartEventDate.plus(durationOfEventStartEnd);
			tmpStartDate = recurringBase.getStartScheduleDatetime().plusMonths(frequency);
			tmpEndDate = isMonthUnit ? recurringBase.getEndScheduleDatetime().plusMonths(frequency) : tmpStartDate.plus(durationOfStartEnd);
			
			int monthDaysOfStartEvent = 0;
			int monthDayOfStartSch = 0;
			
			if (pattern.isDayOfMonth) {
				while(tmpStartDate.getDayOfMonth() < recurringBase.getStartScheduleDatetime().getDayOfMonth()){
					
					monthDaysOfStartEvent = getMonthDays(tmpStartEventDate.plusMonths(1).toLocalDate());
					monthDayOfStartSch = getMonthDays(tmpStartDate.plusMonths(1).toLocalDate());
					
					tmpStartEventDate = tmpStartEventDate.plusMonths(1).withDayOfMonth(monthDaysOfStartEvent);
					tmpEndEventDate = tmpStartEventDate.plus(durationOfEventStartEnd);
					tmpStartDate = tmpStartDate.plusMonths(1).withDayOfMonth(monthDayOfStartSch);
					tmpEndDate = tmpStartDate.plus(durationOfStartEnd);
				}
			}
			else {
				int weeks = recurringBase.getStartScheduleDatetime().getDayOfMonth() / 7;
				if (recurringBase.getStartScheduleDatetime().getDayOfMonth() % 7 > 0) {
					weeks ++;
				}
				
				LocalDateTime firstEventMonthDayDate = tmpStartEventDate.withDayOfMonth(1);
				LocalDateTime firstMonthDayDate = tmpStartDate.withDayOfMonth(1);
				
				int offset = recurringBase.getStartScheduleDatetime().getDayOfWeek().getValue() - firstMonthDayDate.getDayOfWeek().getValue();
				int addDays = offset + (weeks - (offset >=0 ? 1 : 0))*7;
				
				tmpStartEventDate = firstEventMonthDayDate.plusDays(addDays);
				tmpEndEventDate = tmpStartEventDate.plus(durationOfEventStartEnd);
				tmpStartDate = firstMonthDayDate.plusDays((addDays));
				tmpEndDate = tmpStartDate.plus(durationOfStartEnd);
				
				while(firstMonthDayDate.getMonthValue() != tmpStartDate.getMonthValue()){
					tmpStartEventDate = tmpStartEventDate.plusMonths(1);
					tmpEndEventDate = tmpEndEventDate.plusMonths(1);
					tmpStartDate = tmpStartDate.plusMonths(1);
					tmpEndDate = tmpEndDate.plusMonths(1);
					
					firstEventMonthDayDate = tmpStartEventDate.withDayOfMonth(1);
					firstMonthDayDate = tmpStartDate.withDayOfMonth(1);
					
				    offset = recurringBase.getStartScheduleDatetime().getDayOfWeek().getValue() - firstMonthDayDate.getDayOfWeek().getValue();
					addDays = offset + (weeks - (offset >=0 ? 1 : 0))*7;
					
					tmpStartEventDate = firstEventMonthDayDate.plusDays(addDays);
					tmpEndEventDate = tmpStartEventDate.plus(durationOfEventStartEnd);
					tmpStartDate = firstMonthDayDate.plusDays((addDays));
					tmpEndDate = tmpStartDate.plus(durationOfStartEnd);
				}
			}
			if (guardDate != null) {
				guardDate = tmpEndDate;
				if (guardDate.isAfter(lastEndDate)) {
					return true;
				}
			}
			
			recurringBase.setStartEventDatetime(LocalDateTime.from(tmpStartEventDate));
			recurringBase.setEndEventDatetime(LocalDateTime.from(tmpEndEventDate));
			recurringBase.setStartScheduleDatetime(LocalDateTime.from(tmpStartDate));
			recurringBase.setEndScheduleDatetime(LocalDateTime.from(tmpEndDate));
			
			book.setStartEventDatetime(LocalDateTime.from(tmpStartEventDate));
			book.setEndEventDatetime(LocalDateTime.from(tmpEndEventDate));
			book.setStartScheduleDatetime(LocalDateTime.from(tmpStartDate));
			book.setEndScheduleDatetime(LocalDateTime.from(tmpEndDate));
			
			bookings.add(book);
		}//for
		
		return false;
	}
	
	private List<RecurringDateTime> generateRecurringBookingSelectDates(){
		List<RecurringDateTime> bookings = new ArrayList<RecurringDateTime>();
				LocalDateTime startEventDt = null;
		LocalDateTime startSchDt = null;
		for (LocalDateTime selectDate : pattern.getDates()) {
			for (RecurringDateTime recurringBase : base) {
				RecurringDateTime book = recurringBase.cloneSelf();
				
				startEventDt = recurringBase.getStartEventDatetime();
				startSchDt = recurringBase.getStartScheduleDatetime();
				
				Duration eventDuration = Duration.between(startEventDt, recurringBase.getEndEventDatetime());
				Duration duration = Duration.between(startSchDt, recurringBase.getEndScheduleDatetime());
				
				book.setStartEventDatetime(selectDate.plusHours(startEventDt.getHour()).plusMinutes(startEventDt.getMinute()));
				book.setEndEventDatetime(book.getStartEventDatetime().plus(eventDuration));
				book.setStartScheduleDatetime(selectDate.plusHours(startSchDt.getHour()).plusMinutes(startSchDt.getMinute()));
				book.setEndScheduleDatetime(book.getStartScheduleDatetime().plus(duration));
				
				bookings.add(book);
			}//for
		}//for
		
		return bookings;
	}
	
}//class RecurringDateTimeTools
