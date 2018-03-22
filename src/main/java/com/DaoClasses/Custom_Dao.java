package com.DaoClasses;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.EntityClasses.Booking_Master;
import com.EntityClasses.Pickup_Location_Master;
import com.ModelClasses.Customer_Booking;
import com.ModelClasses.New_Pickup_Location;
import com.ModelClasses.UserModel;

public interface Custom_Dao {
	Map<String,Object> user_info();
	public Map<String, Map<String, List<Pickup_Location_Master>>> location();
	public Map<String, Map<String, List<Pickup_Location_Master>>> check_location(int id);
	public String customer_booking(Customer_Booking cb);
	public List<Map<String,Object>> departure_time_info();
	public List<Map<String,Object>> cusomer_booking_history();
	public String customer_request_booking(Customer_Booking cb);
	public List<Map<String,Object>> get_request_booking();
	public List<Booking_Master> get_all_booker(Session session,int from_id, int to_id, String time, String date);
	public List<Map<String,Object>> get_all_bus(Session session);
	public int delete_Schedule(Session session, int from_id,int to_id,String time, String date);
	public Map<Object, List<Booking_Master>> create_schedule(Session session,List<Map<String,Object>> all_bus, List<Booking_Master> all_booker1, Pickup_Location_Master pick_source, Pickup_Location_Master pick_destin, Customer_Booking cb, int total_seat_of_all_bus, int number_of_passenger);
	public List<List<Map<String,Object>>> choose_correct_bus(List<Map<String,Object>> all_bus, Pickup_Location_Master pick_source, Pickup_Location_Master pick_destin, int number_of_passenger, int total_seat_of_all_bus);
	public Map<String, Map<String, List<Pickup_Location_Master>>> create_custom_pickup_location(New_Pickup_Location np);
	public Map<String, Object> create_custom_dropoff_location(New_Pickup_Location np);
	public String request_book_now(int id);
	public String cancel_booking_ticket(int id);
	public String confirm_phone_number(UserModel cb);
}
