package com.DaoClasses;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.EntityClasses.Booking_Master;
import com.EntityClasses.Bus_Master;
import com.EntityClasses.Schedule_Master;
import com.EntityClasses.UserRole;
import com.EntityClasses.User_Info;
import com.HibernateUtil.HibernateUtil;
import com.ModelClasses.Customer_Booking;


public class test {
	
	public static List <Booking_Master> getAllBookings(){
		List <Booking_Master> bookings  = new ArrayList<Booking_Master>();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 =  session.beginTransaction();
            String queryString = "from Booking_Master b order by b.description";
            Query query = session.createQuery(queryString);
            bookings=(List<Booking_Master>)query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return bookings;
        } finally {
            session.flush();
            session.close();
        }
        return bookings;
		
	}
	
	public static void main(String args[]) throws ParseException
	{
		List <Booking_Master> bookings = getAllBookings();
		
		for (Booking_Master booking:bookings)
			System.out.println(booking.getDescription());
		
	}
	
	
	
}