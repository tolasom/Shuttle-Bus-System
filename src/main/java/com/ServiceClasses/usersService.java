package com.ServiceClasses;

import java.text.ParseException;
import java.util.List;

import com.EntityClasses.Bus_Master;

import java.util.Map;

import com.EntityClasses.Batch_Master;
import com.EntityClasses.Booking_Master;
import com.EntityClasses.Booking_Request_Master;
import com.EntityClasses.Bus_Master;
import com.EntityClasses.Location_Master;
import com.EntityClasses.Pickup_Location_Master;
import com.EntityClasses.Schedule_Master;
import com.EntityClasses.User_Info;
import com.ModelClasses.Schedule_Model;
import com.ModelClasses.UserModel;


public interface usersService {
	public boolean createUser(UserModel user);
	public int saveBus(Bus_Master bus);
	public List<Bus_Master> getAllBuses();
	public Bus_Master getBusById (int id);
	public int updateBus(Bus_Master bus);
	public int deleteBus(int id);
	public int saveLocation(Location_Master location);
	public List<Location_Master> getAllLocations();
	public int savePickUpLocation(Pickup_Location_Master p_location);
	public List<Pickup_Location_Master> getAllPickUpLocations();
	public Location_Master getLocationById(int id);
	public int updateLocation(Location_Master location);
	public Pickup_Location_Master getPickUpLocationById(int id);
	public int updatePickUpLocation(Pickup_Location_Master p_location);
	public int deleteLocation(int id);
	public int deletePickUpLocation(int id);
	public List <Booking_Master> getAllCurrentBookings();
	public List <Booking_Master> getAllHistoricalBookings();
	public Booking_Master getBookingById (int id);
	public List <Schedule_Master> getAllSchedulesByMonth(String month, String year) throws ParseException, java.text.ParseException;
	public List <Schedule_Master> schedule_list(String date, String month, String year) throws ParseException, java.text.ParseException;
	public List<Booking_Master> getBookingByScheduleId (int id);
	public Schedule_Master getScheduleById (int id);
	public List <Schedule_Master> getAllHistoricalSchedules();
	public List <Schedule_Master> getAllCurrentSchedules();
	public List <Schedule_Master> getAllSchedules();
	public List<User_Info> getAllUsers();
	public int saveSchedule(Schedule_Model schedule)throws ParseException;
	public int updateSchedule(Schedule_Model schedule) throws ParseException;
	public List <Booking_Request_Master> getAllCurrentBookingRequests();
	public Booking_Request_Master getBookingRequestById (int id);
	public int confirmRequest(Booking_Request_Master request);
	public List <Booking_Request_Master> getAllHistoricalBookingRequests();
	public int rejectRequest(Booking_Request_Master request);
	public int deleteSchedule(int id);
	public int moveSchedule(int arr[], int id);
	public int saveSchedule2(Schedule_Model schedule)throws ParseException;
	public int moveSimple(int arr[], int old_id, int new_id, int bookings);
}
