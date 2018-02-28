package com.DaoClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.EncryptionDecryption.Decryption;
import com.EncryptionDecryption.Encryption;
import com.EncryptionDecryption.SecretKeyClass;
import com.EntityClasses.Batch_Master;
import com.EntityClasses.Booking_Master;
import com.EntityClasses.Bus_Master;
import com.EntityClasses.Location_Master;
import com.EntityClasses.Pickup_Location_Master;
import com.EntityClasses.Schedule_Master;
import com.EntityClasses.UserRole;
import com.EntityClasses.User_Info;
import com.HibernateUtil.HibernateUtil;
import com.ModelClasses.Mail;
import com.ModelClasses.Project_Model;
import com.ModelClasses.Reset_Password;
import com.client_mail.ApplicationConfig;
//import com.ServiceClasses.usersService;
//import com.client_mail.ApplicationConfig;
//import com.client_mail.MailService;
import com.client_mail.MailService;





public class test {
	
	public static void main (String args[]) throws ParseException{
		int arr[] =new int[2];
		arr[0]=1;
		arr[1]=2;
		System.out.println(moveSimple(arr,16,10,5));
		
		
	}
	
	
	public static Booking_Master getBookingById (int id){
		Booking_Master booking= new Booking_Master();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 = session.beginTransaction();
            String queryString = "from Booking_Master where id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",id);
            booking=(Booking_Master)query.uniqueResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return booking;
        } finally {
            session.flush();
            session.close();
        }
        return booking;
		
	}
	
	
	
	public static String getScheduleSequence(){
		List<Schedule_Master> schedules = new ArrayList<Schedule_Master>();
		schedules = new userDaoImpl().getAllHistoricalSchedules();
		int code;
		String scode= "000001";
		if(schedules.size()>0){
			code = 1000000+schedules.get(schedules.size()-1).getId()+1;
			scode = Integer.toString(code);
			scode = scode.substring(1);
			return scode;
		}
		else 
			return scode;
		
	}
	
	public static int moveSimple(int arr[], int old_id, int new_id, int bookings)
	{
		Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        int a[]=arr;    
        try {
            trns19 =  session.beginTransaction();
            for (int i = 0; i < a.length; i++)
   		   {
              System.out.println(a[i]);
              Booking_Master booking = getBookingById(a[i]);
              booking.setSchedule_id(new_id);
   		      session.update(booking);
   		     
   		   }
            Schedule_Master old_schedule = getScheduleById(old_id);
            old_schedule.setNumber_booking(old_schedule.getNumber_booking()-bookings);
            old_schedule.setRemaining_seat(old_schedule.getRemaining_seat()+bookings);
            
            Schedule_Master new_schedule = getScheduleById(new_id);
            new_schedule.setNumber_booking(new_schedule.getNumber_booking()+bookings);
            new_schedule.setRemaining_seat(new_schedule.getRemaining_seat()-bookings);
            
            session.update(old_schedule);
            session.update(new_schedule);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
        	if (trns19 != null) {
                trns19.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            session.flush();
            session.close();
        }
        return 1;
	}
	
	
	public static Schedule_Master getScheduleById (int id){
		Schedule_Master schedule= new Schedule_Master();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 = session.beginTransaction();
            String queryString = "from Schedule_Master where id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",id);
            schedule=(Schedule_Master)query.uniqueResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return schedule;
        } finally {
            session.flush();
            session.close();
        }
        return schedule;
		
	}
	
	
	
	
}






