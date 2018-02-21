package com.ServiceClasses;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DaoClasses.usersDao;
import com.EntityClasses.Batch_Master;
import com.EntityClasses.Booking_Master;
import com.EntityClasses.Bus_Master;
import com.EntityClasses.Location_Master;
import com.EntityClasses.Pickup_Location_Master;
import com.EntityClasses.Schedule_Master;
import com.EntityClasses.User_Info;



@Service
public class usersServiceImpl implements usersService{

	@Autowired
	usersDao usersDao1;

	public int saveBus(Bus_Master bus) {
		return usersDao1.saveBus(bus);
	}
	public List<Bus_Master> getAllBuses(){
		return usersDao1.getAllBuses();
	}
	public Bus_Master getBusById (int id){
		return usersDao1.getBusById(id);
	}
	public int updateBus(Bus_Master bus){
		return usersDao1.updateBus(bus);
	}
	public int deleteBus(int id){
		return usersDao1.deleteBus(id);
	}
	public int saveLocation(Location_Master location) {
		return usersDao1.saveLocation(location);
	}
	public List<Location_Master> getAllLocations(){
		return usersDao1.getAllLocations();
	}
	public int savePickUpLocation(Pickup_Location_Master p_location){
		return usersDao1.savePickUpLocation(p_location);
	}
	public List<Pickup_Location_Master> getAllPickUpLocations(){
		return usersDao1.getAllPickUpLocations();
	}
	public Location_Master getLocationById(int id){
		return usersDao1.getLocationById(id);
	}
	public int updateLocation(Location_Master location){
		return usersDao1.updateLocation(location);
	}
	public Pickup_Location_Master getPickUpLocationById(int id){
		return usersDao1.getPickUpLocationById(id);
	}
	public int updatePickUpLocation(Pickup_Location_Master p_location){
		return usersDao1.updatePickUpLocation(p_location);
	}
	public int deleteLocation(int id){
		return usersDao1.deleteLocation(id);
	}
	public int deletePickUpLocation(int id){
		return usersDao1.deletePickUpLocation(id);
	}
	public List <Booking_Master> getAllCurrentBookings(){
		return usersDao1.getAllCurrentBookings();
	}
	public List <Booking_Master> getAllHistoricalBookings(){
		return usersDao1.getAllHistoricalBookings();
	}
	public Booking_Master getBookingById (int id){
		return usersDao1.getBookingById(id);
	}
	public List <Schedule_Master> getAllSchedulesByMonth(String month, String year) throws ParseException{
		return usersDao1.getAllSchedulesByMonth(month, year);
	}
	public List <Schedule_Master> schedule_list(String date, String month, String year) throws ParseException{
		return usersDao1.schedule_list(date, month, year);
	}
	public List<Booking_Master> getBookingByScheduleId (int id){
		return usersDao1.getBookingByScheduleId(id);
	}
	public Schedule_Master getScheduleById (int id){
		return usersDao1.getScheduleById(id);
	}
	public List <Schedule_Master> getAllHistoricalSchedules(){
		return usersDao1.getAllHistoricalSchedules();
	}
	public List <Schedule_Master> getAllCurrentSchedules(){
		return usersDao1.getAllCurrentSchedules();
	}
	public List<User_Info> getAllUsers(){
		return usersDao1.getAllUsers();
	}
	public int saveSchedule(Schedule_Master schedule){
		return usersDao1.saveSchedule(schedule);
	}
}


