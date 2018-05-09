package com.DaoClasses;

import java.sql.Time;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.EntityClasses.Booking_Master;
import com.EntityClasses.Pickup_Location_Master;
import com.EntityClasses.Schedule_Master;
import com.ModelClasses.Customer_Booking;

public interface Student_Dao {
	public Student_Imp student_schedule(Session session,Customer_Booking cb) throws ParseException;
	public Map<Object, List<Booking_Master>> create_schedule(Student_Imp booking,Session session, List<Map<String,Object>> all_bus, List<Booking_Master> all_booker1, int total_seat_of_all_bus,int number_of_passenger);
	public List<List<Map<String,Object>>> choose_correct_bus(Student_Imp booking,List<Map<String,Object>> all_bus, int number_of_passenger, int total_seat_of_all_bus);
	public List<Booking_Master> get_all_booker(Session session,int from_id, int to_id, String time, String date);
	public List<Map<String,Object>> get_all_bus(Session session,Customer_Booking cb,int from, int to) throws ParseException;
	public List<Map<String,Object>> same_date_same_route(Session session,Customer_Booking cb,int from, int to) throws ParseException;
	public List<Map<String,Object>> same_date_differ_route(Session session,Customer_Booking cb,int from, int to) throws ParseException;
	public Boolean time_same_date(String user_time, String time,long time_dura) throws ParseException;
	public int delete_Schedule(Session session, int from_id,int to_id,String time, String date);
	public List<Time> getAvailableTime(String tmr_dt, Session session);
	public List<int[]> getListRound(Session session);
	public List <Schedule_Master> get_schedules(Session session,Customer_Booking cb);
	public void asign_to_existing_schedule(Session session,List<Booking_Master> list_stu,List<Schedule_Master> list_schedule);
}
