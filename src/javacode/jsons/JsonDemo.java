package javacode.jsons;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javacode.Demo;

public class JsonDemo extends Demo {

	public static void demo(){
		printLine("JSON Demo ************");
		jsonObject();
	}
	
	private static void jsonObject(){
		printLine("JsonObject **************");
		
		String recurringReservationBO = "{\"recurring_reservation_group_id\":0,\"group_pattern_description\":\"Daily, 5 times, from 25 Oct 2016\",\"group_pattern_content\":{\"type\":\"1\",\"frequency\":\"4\",\"starts_on\":\"01/17/2017 00:00 AM\",\"count\":\"5\",\"end_at\":\"02/18/2017 00:00 AM\",\"dates\":[\"01/17/2017 00:00 AM\",\"01/20/2017 00:00 AM\",\"01/19/2017 00:00 AM\",\"02/15/2017 00:00 AM\"]},\"exception_ddate_list\":[\"01/17/2017\",\"01/20/2017\",\"01/19/2017\",\"02/15/2017\"],\"recurring_booking_list\":[{\"pending_id\":\"pending_86_9873\",\"resource_booking_id\":0,\"transaction_id\":-1,\"facility_id\":86,\"facility_number\":\"\",\"facility_type\":2,\"facility_name\":\"Claire Testing\",\"reservation_period_unit\":1,\"reservation_type\":0,\"quantity\":3,\"rental_block_id\":0,\"date_range_id\":0,\"start_event_datetime\":\"11/04/2016 12:00 AM\",\"end_event_datetime\":\"11/04/2016 12:15 AM\",\"start_schedule_datetime\":\"11/03/2016 11:50 PM\",\"end_schedule_datetime\":\"11/04/2016 12:25 AM\",}]}";
		JSONObject jobj = JSONObject.parseObject(recurringReservationBO);
		JSONArray jsonArray = jobj.getJSONArray("recurring_booking_list");
		
		printLine("group_pattern_description = " + jobj.getString("group_pattern_description"));
		printLine("recurring_booking_list = " + jsonArray);
		jsonArray.stream().forEach(arrItem -> {
			printLine(arrItem.toString()); 
		});
	}
	
	
	
}
