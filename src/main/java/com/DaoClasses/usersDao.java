package com.DaoClasses;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;


import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;

import com.EntityClasses.Booking_Master;
import com.EntityClasses.Bus_Master;
import com.EntityClasses.Location_Master;
import com.EntityClasses.Pickup_Location_Master;
import com.EntityClasses.Schedule_Master;
import com.EntityClasses.User_Info;


public interface usersDao {
	public User_Info findByUserName(String name);
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
	public List <Schedule_Master> getAllSchedulesByMonth(String month, String year) throws ParseException;
	public List <Schedule_Master> schedule_list(String date, String month, String year) throws ParseException;
	public List<Booking_Master> getBookingByScheduleId (int id);
	public Schedule_Master getScheduleById (int id);
	public List <Schedule_Master> getAllHistoricalSchedules();
	public List <Schedule_Master> getAllCurrentSchedules();
	public List<User_Info> getAllUsers();
	public int saveSchedule(Schedule_Master schedule);
	
}
