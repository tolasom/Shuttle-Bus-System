package com.DaoClasses;

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

import com.EntityClasses.Bus_Master;
import com.EntityClasses.Schedule_Master;
import com.EntityClasses.UserRole;
import com.EntityClasses.User_Info;
import com.HibernateUtil.HibernateUtil;
import com.ModelClasses.Customer_Booking;


public class test2 {
	
	
	public static void main(String args[]) throws ParseException
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Customer_Booking booking = new Customer_Booking();
		int from;
		int to;
		from  = 1;
		to =2;
		booking.setAdult(0);
		booking.setChild(2);
		booking.setDate("2018-05-25");
		booking.setDestination(3);
		booking.setNumber_of_seat(2);
		booking.setSource(1);
		booking.setStatus("Booked");
		booking.setTime("15:00:00");
		//List<User_Info> drivers=new ArrayList<User_Info>();
		List<Bus_Master>  buses =  get_all_bus2(session,booking,from,to);
		for (Bus_Master b:buses)
		System.out.println(b.getModel());
		for(int i=0;i<buses.size();i++){
			String q = " and dept_date=:date and dept_time=:time and bus_id=:id";
			String queryString = "from Schedule_Master where id>0"+q;
            Query query = session.createQuery(queryString);
            query.setInteger("id",buses.get(i).getId());
            query.setDate("date",java.sql.Date.valueOf(booking.getDate()));
            query.setTime("time",java.sql.Time.valueOf(booking.getTime()));
            System.out.println(java.sql.Date.valueOf(booking.getDate()));
			if(getDriverNBusByExcep(query).size()>0)
				buses.remove(i);
		}
		System.out.println("AFTER");
		for (Bus_Master b:buses)
			System.out.println(b.getModel());
	}
	
	
	
	
	
	public static List<Schedule_Master> getDriverNBusByExcep (Query query){
		List<Schedule_Master> schedules= new ArrayList<Schedule_Master>();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 = session.beginTransaction();
            schedules=(List<Schedule_Master>)query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return schedules;
		
	}
	
	
	
	
	
	public static List<Bus_Master> get_all_bus2(Session session,Customer_Booking cb,int from, int to) throws ParseException{
		System.out.println("get_all_bus");
		List<Bus_Master> query_all_bus=new ArrayList<Bus_Master>();
		List<Bus_Master> buses=new ArrayList<Bus_Master>();
		List<Map<String,Object>> same_date_route =new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> same_date_diff_route =new ArrayList<Map<String,Object>>();
		Custom_Dao custom_imp=new Custom_Imp();
		try {
			same_date_route=same_date_same_route(session, cb, from, to);
			List<Integer> unava1= (List<Integer>) same_date_route.get(0).get("unavailable_bus");
			same_date_diff_route=same_date_differ_route(session, cb, from, to);
			List<Integer> unava2= (List<Integer>) same_date_diff_route.get(0).get("unavailable_bus");
			String excep="";
			for(int i=0;i<unava1.size();i++){
				excep+=" and id!="+unava1.get(i);
			}
			for(int i=0;i<unava2.size();i++){
				excep+=" and id!="+unava2.get(i);
			}
            query_all_bus = session.createQuery("from Bus_Master where enabled=?"+excep+" order by number_of_seat asc").setBoolean(0, true).list();  
            if(query_all_bus.size()>0){            
	              for(int i=0;i<query_all_bus.size();i++){	
	            	  Bus_Master bus = new Bus_Master();
	                  bus.setModel(query_all_bus.get(i).getModel());
	                  bus.setNumber_of_seat(query_all_bus.get(i).getNumber_of_seat());
	                  bus.setId(query_all_bus.get(i).getId());
	                  buses.add(bus);
	              } 
            }
	              
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		return buses;
	}
	
	
	
	
	
	
	
	public static List<Map<String,Object>> same_date_same_route(Session session,Customer_Booking cb,int from, int to) throws ParseException{
		List<Schedule_Master> sch=new ArrayList<Schedule_Master>();
		List<Map<String,Object>> all_bus =new ArrayList<Map<String,Object>>();
		List<Integer> ava_bus=new ArrayList<Integer>();
		List<Integer> una_bus=new ArrayList<Integer>();
		try {
			
			sch=session.createQuery("from Schedule_Master where dept_date=:date and dept_time!=:time and to_id=:to and from_id=:from")
					.setDate("date",java.sql.Date.valueOf(cb.getDate()))
					.setTime("time", java.sql.Time.valueOf(cb.getTime()))
					.setParameter("to", to)
					.setParameter("from", from).list();
			
			for(int i=0;i<sch.size();i++){
				if(time_same_date(sch.get(i).getDept_time().toString(),cb.getTime(),8)){
					ava_bus.add(sch.get(i).getBus_id());
				}else{
					una_bus.add(sch.get(i).getBus_id());
				}
			}
			Map<String, Object> map=new HashMap<String , Object>();
			map.put("unavailable_bus", una_bus);
			map.put("available_bus", ava_bus);
			all_bus.add(map);
			System.out.println("same_date_same_route:  ");
			System.out.println(all_bus);
	              
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		return all_bus;
	}
	//Check Bus Available and not from the same route 
	public static List<Map<String,Object>> same_date_differ_route(Session session,Customer_Booking cb,int from, int to) throws ParseException{
			List<Schedule_Master> sch=new ArrayList<Schedule_Master>();
			List<Map<String,Object>> all_bus =new ArrayList<Map<String,Object>>();
			List<Integer> ava_bus=new ArrayList<Integer>();
			List<Integer> una_bus=new ArrayList<Integer>();
			try {	
				sch=session.createQuery("from Schedule_Master where dept_date=:date and from_id!=:from")
						.setDate("date",java.sql.Date.valueOf(cb.getDate()))
						.setParameter("from", from).list();
				
				for(int i=0;i<sch.size();i++){
					if(time_same_date(sch.get(i).getDept_time().toString(),cb.getTime(),4)){
						ava_bus.add(sch.get(i).getBus_id());
					}else{
						una_bus.add(sch.get(i).getBus_id());
					}
				}
				Map<String, Object> map=new HashMap<String , Object>();
				map.put("unavailable_bus", una_bus);
				map.put("available_bus", ava_bus);
				all_bus.add(map);
				System.out.println("same_date_differ_route:  ");
				System.out.println(all_bus);
		              
	        } catch (RuntimeException e) {
	        	e.printStackTrace();
	        }    
			return all_bus;
		}
		
	
	public static Boolean time_same_date(String user_time, String time,long time_dura) throws ParseException{
		 
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = format.parse(user_time);
		Date date2 = format.parse(time);
		long difference = date2.getTime() - date1.getTime();
		long duration =difference/(1000*60*60);
		System.out.println("PLPL");
		System.out.println("duration: "+duration);
		System.out.println("time_dura: "+time_dura);
		if(duration>=time_dura||duration<=-time_dura){
			return true;
		}else{
			return false;
		}
	}
}


