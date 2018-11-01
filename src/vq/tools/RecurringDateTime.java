package vq.tools;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.alibaba.fastjson.JSONObject;

public class RecurringDateTime {
	
	public final static String DATE_TIME_PATTERN ="MM/dd/yyyy h:mm a";
	
	public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
	
	private int resourceBookingId;
	
	public int getResourceBookingId() {
		return resourceBookingId;
	}
	
	public void setResourceBookingId(int resourceBookingId) {
		this.resourceBookingId = resourceBookingId;
	}
	
	private int transactionId;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	
	private int resourceId;

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	
	
	private String resourceNumber;

	public String getResourceNumber() {
		return resourceNumber;
	}

	public void setResourceNumber(String resourceNumber) {
		this.resourceNumber = resourceNumber;
	}
	
	
	private int resourceType;

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	
	private String resourceName;

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	
	private int reservationPeriodUnit;

	public int getReservationPeriodUnit() {
		return reservationPeriodUnit;
	}

	public void setReservationPeriodUnit(int reservationPeriodUnit) {
		this.reservationPeriodUnit = reservationPeriodUnit;
	}
	
	
	private int reservationType;

	public int getReservationType() {
		return reservationType;
	}

	public void setReservationType(int reservationType) {
		this.reservationType = reservationType;
	}
	
	
	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	private int rentalBlockId;

	public int getRentalBlockId() {
		return rentalBlockId;
	}

	public void setRentalBlockId(int rentalBlockId) {
		this.rentalBlockId = rentalBlockId;
	}
	
	
	private int dateRangeId;

	public int getDateRangeId() {
		return dateRangeId;
	}

	public void setDateRangeId(int dateRangeId) {
		this.dateRangeId = dateRangeId;
	}
	
	
	private LocalDateTime startEventDatetime;

	public LocalDateTime getStartEventDatetime() {
		return startEventDatetime;
	}

	public void setStartEventDatetime(LocalDateTime startEventDatetime) {
		this.startEventDatetime = startEventDatetime;
	}
	
	
	private LocalDateTime endEventDatetime;

	public LocalDateTime getEndEventDatetime() {
		return endEventDatetime;
	}

	public void setEndEventDatetime(LocalDateTime endEventDatetime) {
		this.endEventDatetime = endEventDatetime;
	}
	
	
	private LocalDateTime startScheduleDatetime;

	public LocalDateTime getStartScheduleDatetime() {
		return startScheduleDatetime;
	}

	public void setStartScheduleDatetime(LocalDateTime startScheduleDatetime) {
		this.startScheduleDatetime = startScheduleDatetime;
	}
	
	
	private LocalDateTime endScheduleDatetime;

	public LocalDateTime getEndScheduleDatetime() {
		return endScheduleDatetime;
	}

	public void setEndScheduleDatetime(LocalDateTime endScheduleDatetime) {
		this.endScheduleDatetime = endScheduleDatetime;
	}
	//********** add for ANE-53551
	
	private String bookingIdentifier;
	
	public  String getBookingIdentifier(){
		return bookingIdentifier;
	}
	
	public void setBookingIdentifier(String bkIdentifier){
		this.bookingIdentifier = bkIdentifier;
	}
	
	private boolean isDeleteSchedule;
	
	public boolean getIsDeleteSchedule(){
		return isDeleteSchedule;
	}
	
	public void setIsDeleteSchedule(boolean isDelete){
		isDeleteSchedule = isDelete;
	}
	public String getStartEventDatetimeString() {
		return startEventDatetime.format(DATE_TIME_FORMATTER);
	}
	
	public Date getStartEventDatetimeByDate() {
		return convertLocalDateTimeToDate(startEventDatetime);
	}
	public String getEndEventDatetimeString() {
		return endEventDatetime.format(DATE_TIME_FORMATTER);
	}
	
	public Date getEndEventDatetimeByDate() {
		return convertLocalDateTimeToDate(endEventDatetime);
	}
	
	//************ add for ANE-53551 end
	
	public String valid(){
		String errMsg = "";
		
		
		return errMsg;
	}
	
	public RecurringDateTime cloneSelf(){
		RecurringDateTime bo = new RecurringDateTime();
		
		bo.setResourceBookingId(this.resourceBookingId);
		bo.setTransactionId(this.transactionId);
		bo.setResourceId(this.resourceId);
		bo.setResourceNumber(this.resourceNumber);
		bo.setResourceType(this.resourceType);
		bo.setResourceName(this.resourceName);//
		bo.setReservationPeriodUnit(this.reservationPeriodUnit);
		bo.setReservationType(this.reservationType);
		bo.setQuantity(this.quantity);
		bo.setRentalBlockId(this.rentalBlockId);
		bo.setDateRangeId(this.dateRangeId);
		bo.setStartEventDatetime(LocalDateTime.from(this.startEventDatetime));
		bo.setEndEventDatetime(LocalDateTime.from(this.endEventDatetime));
		bo.setStartScheduleDatetime(LocalDateTime.from(this.startScheduleDatetime));
		bo.setEndScheduleDatetime(LocalDateTime.from(this.endScheduleDatetime));
		
		return bo;
	}
	
	public static Date convertLocalDateTimeToDate(LocalDateTime ldt){
		Instant ist = ldt.toInstant(ZoneOffset.UTC);
		return Date.from(ist);
	}
}
