package com.DaoClasses;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	
	
	public static void main(String args[]) throws ParseException
	{
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String today=sdf.format(cal.getTime());
	    Date d = new userDaoImpl().getScheduleById(14).getDept_date();
	    long diff = d.getTime() - sdf.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
	    System.out.println(today);
	    System.out.println(d);
	    System.out.println();
	}
	
	
	
}