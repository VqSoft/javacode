package vq.tools;


import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import vq.tools.RecurringDateTimeTools.EnumRecurringRepeatType;

public class RecurringPattern {
	

	private RecurringDateTimeTools.EnumRecurringRepeatType type;
	
	/**
	 * Only available when type is monthly.
	 * Ture: Day of month, False : Weekday of month
	 */
	public boolean isDayOfMonth;
	
	/**
	 * Repeat Every X 
	 */
	private int frequency;
	
	//private LocalDateTime startsOn;
	
	/**
	 * Repeat n times.
	 */
	private int count;
	
	private LocalDateTime endAt;
	
	private List<LocalDateTime> dates;
	
	public boolean getIsDayOfMonth() {
		return isDayOfMonth;
	}

	public void setIsDayOfMonth(boolean isDayOfMonth) {
		this.isDayOfMonth = isDayOfMonth;
	}
	
	public EnumRecurringRepeatType getType() {
		return type;
	}

	public void setType(EnumRecurringRepeatType type) {
		this.type = type;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

//	public LocalDateTime getStartsOn() {
//		return startsOn;
//	}
//
//	public void setStartsOn(LocalDateTime startsOn) {
//		this.startsOn = startsOn;
//	}

	public int getCount() {
		// recurrence count contains base booking ,so need minus 1 here.
		return count - 1;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public LocalDateTime getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}

	public List<LocalDateTime> getDates() {
		return dates;
	}

	public void setDates(List<LocalDateTime> dates) {
		this.dates = dates;
	}
	
	public String valid(){
		String errMsg = "";
		
		if (getCount() <= 0) {
			return "Invalid count.";
		}
		
		return errMsg;
	}
}
