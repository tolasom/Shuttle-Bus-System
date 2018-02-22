package com.DaoClasses;

import java.util.List;
import java.util.Map;

import com.EntityClasses.Pickup_Location_Master;
import com.ModelClasses.Customer_Booking;

public interface Custom_Dao {
	Map<Object, String> user_info();
	public Map<String, Map<String, List<Pickup_Location_Master>>> location();
	public Map<String, Map<String, List<Pickup_Location_Master>>> check_location(int id);
	public String customer_booking(Customer_Booking cb);
	public List<Map<Object, String>> departure_time_info();
	public List<Map<Object, String>> cusomer_booking_history();
	public String customer_request_booking(Customer_Booking cb);
	public List<Map<Object, String>> get_request_booking();
}
