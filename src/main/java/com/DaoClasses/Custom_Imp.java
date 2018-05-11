package com.DaoClasses;

import getInfoLogin.IdUser;

import java.io.ByteArrayOutputStream; 
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.EntityClasses.Booking_Master;
import com.EntityClasses.Booking_Request_Master;
import com.EntityClasses.Bus_Master;
import com.EntityClasses.Dept_Time_Master;
import com.EntityClasses.Location_Master;
import com.EntityClasses.Pickup_Location_Master;
import com.EntityClasses.Schedule_Master;
import com.EntityClasses.User_Info;
import com.HibernateUtil.HibernateUtil;
import com.ModelClasses.Customer_Booking;
import com.ModelClasses.New_Pickup_Location;
import com.ModelClasses.UserModel;

public class Custom_Imp implements Custom_Dao{
	IdUser user=new IdUser();
	public String TimeNow(){
		Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String currentDateTimeString = sdf.format(d);
        System.out.println(currentDateTimeString);
        return currentDateTimeString;
	}
	public String DateNow(){
		Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-M-dd");
        String currentDateTimeString = sdf.format(d);
        System.out.println(currentDateTimeString);
        return currentDateTimeString;
	}
	public String DateTimeNow(){
		Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        String currentDateTimeString = sdf.format(d);
        System.out.println(currentDateTimeString);
        return currentDateTimeString;
	}
	public Date TomorrowDate(){
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		return dt;
	}
	public static String getScheduleSequence(){ 
		  List<Schedule_Master> schedules = new ArrayList<Schedule_Master>(); 
		  schedules = new Custom_Imp().getAllSchedules(); 
		  int code; 
		  String scode= "000001"; 
		  for(Schedule_Master s : schedules) 
		   System.out.println(s.getId()); 
		  if(schedules.size()>0){ 
		   code = 1000000+schedules.get(schedules.size()-1).getId()+1; 
		   scode = Integer.toString(code); 
		   scode = scode.substring(1); 
		   return "S"+scode; 
		  } 
		  else  
		   return "S"+scode; 
		   
		 }
	public List <Schedule_Master> getAllSchedules(){ 
		  List <Schedule_Master> schedules  = new ArrayList<Schedule_Master>(); 
		        Transaction trns19 = null; 
		        Session session = HibernateUtil.getSessionFactory().openSession(); 
		        try { 
		            trns19 =  session.beginTransaction(); 
		            String queryString = "from Schedule_Master order by id asc"; 
		            Query query = session.createQuery(queryString); 
		            schedules=(List<Schedule_Master>)query.list(); 
		        } catch (RuntimeException e) { 
		            e.printStackTrace(); 
		            return schedules; 
		        } finally { 
		            session.flush(); 
		            session.close(); 
		        } 
		        return schedules; 
		   
		 }
	 public static String getBookingSequence(){ 
		  List<Booking_Master> bookings = new ArrayList<Booking_Master>(); 
		  bookings = new Custom_Imp().getAllBookings(); 
		  int code; 
		  String scode= "000001"; 
		  for(Booking_Master s : bookings) 
		   System.out.println(s.getId()); 
		  if(bookings.size()>0){ 
		   code = 1000000+bookings.get(bookings.size()-1).getId()+1; 
		   scode = Integer.toString(code); 
		   scode = scode.substring(1); 
		   return "B"+scode; 
		  } 
		  else  
		   return "B"+scode; 
		   
	}
	 public List <Booking_Master> getAllBookings(){ 
		  List <Booking_Master> bookings  = new ArrayList<Booking_Master>(); 
		        Transaction trns19 = null; 
		        Session session = HibernateUtil.getSessionFactory().openSession(); 
		        try { 
		            trns19 =  session.beginTransaction(); 
		            String queryString = "from Booking_Master"; 
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
	public Map<String,Object> user_info(){
		IdUser user= new IdUser();
		System.out.println(user.getAuthentic());
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
		List<User_Info> users = new ArrayList<User_Info>();
		Map<String,Object> map=new HashMap<String,Object>();
		try {
            trns1 = session.beginTransaction();
            int user_id=user.getAuthentic();          
            User_Info us = (User_Info) session.load(User_Info.class,user_id);
                
           // System.out.println("PP: "+users.size());
            //System.out.println("username: "+us.getUsername());
            map.put("username", us.getUsername());
            map.put("phone_number", us.getPhone_number());
            map.put("email",us.getEmail());
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		finally {
            session.flush();
            session.close();
        }
		return map;
	}
	public Map<String, Map<String, List<Pickup_Location_Master>>> location(){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();       
		List<Location_Master> locat = new ArrayList<Location_Master>();
		Map<String,Map<String, List<Pickup_Location_Master>>> pickup=new HashMap<String,Map<String, List<Pickup_Location_Master>>>();
		Map<String, List<Pickup_Location_Master>> list= new HashMap<String, List<Pickup_Location_Master>>();
		List<Pickup_Location_Master> pick= new ArrayList<Pickup_Location_Master>();
		try {
            trns1 = session.beginTransaction();
            locat = session.createQuery("from Location_Master where enabled=?").setBoolean(0, true).list();
            System.out.println(locat.get(0).getId());
            for(int i=0;i<locat.size();i++){
            	pick = session.createQuery("from Pickup_Location_Master where location_id=? and enabled=? and permanent=?")
						.setParameter(0, locat.get(i).getId()).setBoolean(1, true).setBoolean(2,true).list();
            	System.out.println(pick.size());
            	list.put(locat.get(i).getName().toString(), pick);
            }
            pickup.put("location", list);
            System.out.println(pickup);
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }finally {
            session.flush();
            session.close();
        }              

		return pickup;
	}
	public Map<String, Map<String, List<Pickup_Location_Master>>> create_custom_pickup_location(New_Pickup_Location np){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();       
		List<Location_Master> locat = new ArrayList<Location_Master>();
		Map<String,Map<String, List<Pickup_Location_Master>>> pickup=new HashMap<String,Map<String, List<Pickup_Location_Master>>>();
		Map<String, List<Pickup_Location_Master>> list= new HashMap<String, List<Pickup_Location_Master>>();
		Pickup_Location_Master pick= new Pickup_Location_Master();
		List<Pickup_Location_Master> all_pick=new ArrayList<Pickup_Location_Master>();
		Custom_Imp c=new Custom_Imp();
		Boolean assign=true;
		try {
            trns1 = session.beginTransaction();
            pick.setName(np.getPickup_name());
            pick.setLocation_id(np.getLocation_id());
            pick.setEnabled(true);
            pick.setPermanent(false);
            pick.setCreated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
            pick.setUpdated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
            session.save(pick);
            
            trns1.commit();
            
            locat = session.createQuery("from Location_Master where enabled=?").setBoolean(0, true).list();
            System.out.println(locat.get(0).getId());
            for(int i=0;i<locat.size();i++){
            	all_pick = session.createQuery("from Pickup_Location_Master where location_id=? and enabled=? and permanent=?").setParameter(0, locat.get(i).getId()).setBoolean(1, true).setBoolean(2, true).list();
            	if(locat.get(i).getId()==np.getLocation_id()&&assign){
            		System.out.println("Hello August!!!");
            		all_pick.add(pick);
            		assign=false;
            	}
            	list.put(locat.get(i).getName().toString(), all_pick);
            }
            pickup.put("location", list);
            
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }finally {
            session.flush();
            session.close();
        }              

		return pickup;
	}
	
	public Map<String, Object> create_custom_dropoff_location(New_Pickup_Location np){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
		Pickup_Location_Master drop= new Pickup_Location_Master();
		Map<String, Object> map=new HashMap<String, Object>();
		Custom_Imp c=new Custom_Imp();
//		java.sql.Timestamp.valueOf(c.DateTimeNow())
		try {
            trns1 = session.beginTransaction();
            drop.setName(np.getDropoff_name());
            drop.setLocation_id(np.getLocation_id());
            drop.setEnabled(true);
            drop.setPermanent(false);
            drop.setCreated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
            drop.setUpdated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
            session.save(drop);
            
            Location_Master locat = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, np.getLocation_id()).list().get(0);
            
            trns1.commit();
            
            map.put("drop_off_name", drop.getName());
            map.put("id", drop.getId());
            map.put("location_id", drop.getLocation_id());  
            map.put("location_name", locat.getName()); 
         
            
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }finally {
            session.flush();
            session.close();
        }              
		return map;
	}
	
	public Map<String, Map<String, List<Pickup_Location_Master>>> check_location(int id){
		System.out.println("KK: "+id);
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();       
		List<Location_Master> locat = new ArrayList<Location_Master>();
		Map<String,Map<String, List<Pickup_Location_Master>>> pickup=new HashMap<String,Map<String, List<Pickup_Location_Master>>>();
		Map<String, List<Pickup_Location_Master>> list= new HashMap<String, List<Pickup_Location_Master>>();
		List<Pickup_Location_Master> pick= new ArrayList<Pickup_Location_Master>();
		List<Pickup_Location_Master> pick_selected= new ArrayList<Pickup_Location_Master>();
		try {
            trns1 = session.beginTransaction();
            pick_selected = session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, id).list();
            System.out.println(pick_selected);
            
            locat = session.createQuery("from Location_Master where id!=?").setParameter(0, pick_selected.get(0).getLocation_id()).list();
            System.out.println(locat.get(0).getId());
            for(int i=0;i<locat.size();i++){
            	pick = session.createQuery("from Pickup_Location_Master where location_id=? and enabled=? and permanent=?").setParameter(0, locat.get(i).getId()).setBoolean(1, true).setBoolean(2, true).list();
            	System.out.println(pick.size());
            	list.put(locat.get(i).getName().toString(), pick);
            }
            pickup.put("location", list);
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }finally {
            session.flush();
            session.close();
        }              

		return pickup;
	}
	public List<Map<String,Object>> departure_time_info(){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();      
		List<Dept_Time_Master> dept = new ArrayList<Dept_Time_Master>();	
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		try {
            trns1 = session.beginTransaction();
            dept = session.createQuery("from Dept_Time_Master where enabled=?").setBoolean(0, true).list();
            System.out.println("KK");
            for(int i=0; i<dept.size();i++){
            	Map<String,Object> map=new HashMap<String,Object>();
            	System.out.println(dept.get(i).getDept_time());
            	map.put("id", String.valueOf(dept.get(i).getId()));
            	map.put("dept_time", dept.get(i).getDept_time().toString());
            	list.add(map);
            }
            System.out.println(list);
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		finally {
            session.flush();
            session.close();
        }
		return list;
	}
	public List<Map<String,Object>> cusomer_booking_history(){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();   
        List<Booking_Master> cr = new ArrayList<Booking_Master>();	
		
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		try {
            trns1 = session.beginTransaction();
            cr = session.createQuery("from Booking_Master where user_id=? and dept_date>=? order by dept_date desc").setParameter(0, user.getAuthentic()).setDate(1, new Date()).list();
            System.out.println("KK");
            
            //current to future
            for(int i=0; i<cr.size();i++){
            	Pickup_Location_Master pick_source=new Pickup_Location_Master();
            	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, cr.get(i).getSource_id()).list().get(0);
            	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
            	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, cr.get(i).getDestination_id()).list().get(0);
            	Location_Master source=new Location_Master();
            	source = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, pick_source.getLocation_id()).list().get(0);
            	Location_Master destin=new Location_Master();
            	destin = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, pick_destin.getLocation_id()).list().get(0);
            	List<Schedule_Master> sch_ma=new ArrayList<Schedule_Master>();
            	sch_ma =  session.createQuery("from Schedule_Master where id=?").setParameter(0, cr.get(i).getSchedule_id()).list();

            	
            	
            	ByteArrayOutputStream out = QRCode.from(cr.get(i).getQr().toString()).to(ImageType.PNG).stream();  
				byte[] test = out.toByteArray();
				String encodedImage = Base64.getEncoder().encodeToString(test);
            	
            	Map<String,Object> map=new HashMap<String,Object>();
            	map.put("id", cr.get(i).getId());
            	map.put("booking_code", cr.get(i).getCode());
            	map.put("dept_date", cr.get(i).getDept_date().toString());
            	map.put("dept_time", cr.get(i).getDept_time().toString());
            	map.put("scource", source.getName());
            	map.put("pick_up", pick_source.getName());
            	map.put("destination", destin.getName());
            	map.put("drop_off", pick_destin.getName());
            	map.put("number_of_ticket", String.valueOf(cr.get(i).getNumber_booking()));
            	
            	map.put("notification", cr.get(i).getNotification());
            	map.put("qr", encodedImage);
            	if(sch_ma.size()>0){
            		Bus_Master bus=new Bus_Master();
                	bus = (Bus_Master) session.createQuery("from Bus_Master where id=?").setParameter(0, sch_ma.get(0).getBus_id()).list().get(0);
                	map.put("bus_model", bus.getModel());
                	map.put("plate_number", bus.getPlate_number());
                	if(sch_ma.get(0).getDriver_id()==0){
                		map.put("diver_name", "no_driver");
                    	map.put("diver_phone_number", 0);
                	}	
                	else{
                		User_Info driver=new User_Info();
                    	driver = (User_Info) session.createQuery("from User_Info where id=?").setParameter(0, sch_ma.get(0).getDriver_id()).list().get(0);
                    	map.put("diver_name", driver.getName());
                    	map.put("diver_phone_number", driver.getPhone_number());
                	}
            	}
            	
            	list.add(map);
            }
            
            //past
            if(cr.size()<10){
            	List<Booking_Master> bh = new ArrayList<Booking_Master>();	
                bh = session.createQuery("from Booking_Master where user_id=? and dept_date<? order by dept_date desc").setParameter(0, user.getAuthentic()).setDate(1, new Date()).setMaxResults(10-cr.size()).list();
                for(int i=0; i<bh.size();i++){
                	Pickup_Location_Master pick_source=new Pickup_Location_Master();
                	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getSource_id()).list().get(0);
                	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
                	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getDestination_id()).list().get(0);
                	Location_Master source=new Location_Master();
                	source = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, pick_source.getLocation_id()).list().get(0);
                	Location_Master destin=new Location_Master();
                	destin = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, pick_destin.getLocation_id()).list().get(0);
                	Schedule_Master sch_ma=new Schedule_Master();
                	sch_ma = (Schedule_Master) session.createQuery("from Schedule_Master where id=?").setParameter(0, bh.get(i).getSchedule_id()).list().get(0);
                	System.out.println("LL: "+bh.get(i).getSchedule_id());
                	Bus_Master bus=new Bus_Master();
                	bus = (Bus_Master) session.createQuery("from Bus_Master where id=?").setParameter(0, sch_ma.getBus_id()).list().get(0);

                	
                	ByteArrayOutputStream out = QRCode.from(bh.get(i).getQr().toString()).to(ImageType.PNG).stream();
    				byte[] test = out.toByteArray();
    				String encodedImage = Base64.getEncoder().encodeToString(test);

                	Map<String,Object> map=new HashMap<String,Object>();
                	map.put("booking_code", bh.get(i).getCode());
                	map.put("id", bh.get(i).getId());
                	map.put("dept_date", bh.get(i).getDept_date().toString());
                	map.put("dept_time", bh.get(i).getDept_time().toString());
                	map.put("scource", source.getName());
                	map.put("pick_up", pick_source.getName());
                	map.put("destination", destin.getName());
                	map.put("drop_off", pick_destin.getName());
                	map.put("number_of_ticket", String.valueOf(bh.get(i).getNumber_booking()));
                	map.put("bus_model", bus.getModel());
                	map.put("plate_number", bus.getPlate_number());
                	map.put("notification", bh.get(i).getNotification());
                	map.put("qr", encodedImage);
                	if(sch_ma.getDriver_id()==0){
                		map.put("diver_name", "no_driver");
                    	map.put("diver_phone_number", 0);
                	}	
                	else{
                		User_Info driver=new User_Info();
                    	driver = (User_Info) session.createQuery("from User_Info where id=?").setParameter(0, sch_ma.getDriver_id()).list().get(0);
                    	map.put("diver_name", driver.getName());
                    	map.put("diver_phone_number", driver.getPhone_number());
                	}
                	list.add(map);
                }
            }
            
            System.out.println(list);
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		finally {
            session.flush();
            session.close();
        }
		return list;
	}
	public List<Map<String,Object>> get_request_booking(){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();      
		List<Booking_Request_Master> bh = new ArrayList<Booking_Request_Master>();	
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		Custom_Imp c=new Custom_Imp();
		try {
            trns1 = session.beginTransaction();
            bh = session.createQuery("from Booking_Request_Master where user_id=? and dept_date>=? and enabled='true' order by dept_date asc")
            		.setParameter(0, user.getAuthentic()).setDate(1, java.sql.Date.valueOf(c.DateNow())).list();
     
            System.out.println(bh.size());
            for(int i=0; i<bh.size();i++){
            	System.out.println("KK time: "+java.sql.Time.valueOf(c.TimeNow()));
                System.out.println("KK time: "+bh.get(i).getDept_time());
            	if(bh.get(i).getDept_date().after(new Date())){
            		Pickup_Location_Master pick_source=new Pickup_Location_Master();
                	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getSource_id()).list().get(0);
                	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
                	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getDestination_id()).list().get(0);
                	
                	Location_Master source=new Location_Master();
                	source = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getFrom_id()).list().get(0);
                	Location_Master destin=new Location_Master();
                	destin = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getTo_id()).list().get(0);
                	Map<String,Object> map=new HashMap<String,Object>();
                	map.put("id", String.valueOf(bh.get(i).getId()));
                	map.put("dept_date", bh.get(i).getDept_date().toString());
                	if(bh.get(i).getStatus().equals("Confirmed")){
                		map.put("dept_time", bh.get(i).getProvided_time());
                	}else{
                		map.put("dept_time", bh.get(i).getDept_time().toString());
                	}
                	map.put("pick_source_id", String.valueOf(pick_source.getId()));
                	map.put("pick_source_name", String.valueOf(pick_source.getName()));
                	map.put("drop_dest_id", String.valueOf(pick_destin.getId()));
                	map.put("drop_dest_name", String.valueOf(pick_destin.getName()));
                	map.put("scource", source.getName());
                	map.put("scource_id", String.valueOf(source.getId()));
                	map.put("destination", destin.getName());
                	map.put("destination_id", String.valueOf(destin.getId()));
                	map.put("number_of_ticket", String.valueOf(bh.get(i).getNumber_of_booking()));
                	map.put("provided_time", bh.get(i).getProvided_time());
                	map.put("status", bh.get(i).getStatus());
                	map.put("time_status", "future");
                	list.add(map);
            	}else if(bh.get(i).getDept_time().after(java.sql.Time.valueOf(c.TimeNow()))){
            		Pickup_Location_Master pick_source=new Pickup_Location_Master();
                	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getSource_id()).list().get(0);
                	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
                	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getDestination_id()).list().get(0);
                	
                	Location_Master source=new Location_Master();
                	source = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getFrom_id()).list().get(0);
                	Location_Master destin=new Location_Master();
                	destin = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getTo_id()).list().get(0);
                	Map<String,Object> map=new HashMap<String,Object>();
                	map.put("id", String.valueOf(bh.get(i).getId()));
                	map.put("dept_date", bh.get(i).getDept_date().toString());
                	if(bh.get(i).getStatus().equals("Confirmed")){
                		map.put("dept_time", bh.get(i).getProvided_time());
                	}else{
                		map.put("dept_time", bh.get(i).getDept_time().toString());
                	}
                	map.put("pick_source_id", String.valueOf(pick_source.getId()));
                	map.put("pick_source_name", String.valueOf(pick_source.getName()));
                	map.put("drop_dest_id", String.valueOf(pick_destin.getId()));
                	map.put("drop_dest_name", String.valueOf(pick_destin.getName()));
                	map.put("scource", source.getName());
                	map.put("scource_id", String.valueOf(source.getId()));
                	map.put("destination", destin.getName());
                	map.put("destination_id", String.valueOf(destin.getId()));
                	map.put("number_of_ticket", String.valueOf(bh.get(i).getNumber_of_booking()));
                	map.put("provided_time", bh.get(i).getProvided_time());
                	map.put("status", bh.get(i).getStatus());
                	map.put("time_status", "future");
                	list.add(map);
            	}else{
            		Pickup_Location_Master pick_source=new Pickup_Location_Master();
                	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getSource_id()).list().get(0);
                	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
                	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getDestination_id()).list().get(0);
                	
                	Location_Master source=new Location_Master();
                	source = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getFrom_id()).list().get(0);
                	Location_Master destin=new Location_Master();
                	destin = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getTo_id()).list().get(0);
                	Map<String,Object> map=new HashMap<String,Object>();
                	map.put("id", String.valueOf(bh.get(i).getId()));
                	map.put("dept_date", bh.get(i).getDept_date().toString());
                	if(bh.get(i).getStatus().equals("Confirmed")){
                		map.put("dept_time", bh.get(i).getProvided_time());
                	}else{
                		map.put("dept_time", bh.get(i).getDept_time().toString());
                	}
                	map.put("pick_source_id", String.valueOf(pick_source.getId()));
                	map.put("pick_source_name", String.valueOf(pick_source.getName()));
                	map.put("drop_dest_id", String.valueOf(pick_destin.getId()));
                	map.put("drop_dest_name", String.valueOf(pick_destin.getName()));
                	map.put("scource", source.getName());
                	map.put("scource_id", String.valueOf(source.getId()));
                	map.put("destination", destin.getName());
                	map.put("destination_id", String.valueOf(destin.getId()));
                	map.put("number_of_ticket", String.valueOf(bh.get(i).getNumber_of_booking()));
                	map.put("provided_time", bh.get(i).getProvided_time());
                	map.put("status", bh.get(i).getStatus());
                	map.put("time_status", "past");
                	list.add(map);
            	}
            	System.out.println("KKKKKKKKKKKKKKK");
            	System.out.println(list);
            }
            
            System.out.println("KKKKKKKKKKKKKKK111111111111111");
        	System.out.println(list);
        	 System.out.println("KKKKKKKKKKKKKKK111111111111111:     "+bh.size());
            if(bh.size()<10){
            	bh = session.createQuery("from Booking_Request_Master where user_id=? and dept_date<? and enabled='true' order by dept_date desc")
                		.setParameter(0, user.getAuthentic()).setDate(1, java.sql.Date.valueOf(c.DateNow())).setMaxResults(10-bh.size()).list();
         
            	for(int i=0; i<bh.size();i++){
            		Pickup_Location_Master pick_source=new Pickup_Location_Master();
                	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getSource_id()).list().get(0);
                	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
                	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getDestination_id()).list().get(0);
                	
                	Location_Master source=new Location_Master();
                	source = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getFrom_id()).list().get(0);
                	Location_Master destin=new Location_Master();
                	destin = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getTo_id()).list().get(0);
                	Map<String,Object> map=new HashMap<String,Object>();
                	map.put("id", String.valueOf(bh.get(i).getId()));
                	map.put("dept_date", bh.get(i).getDept_date().toString());
                	if(bh.get(i).getStatus().equals("Confirmed")){
                		map.put("dept_time", bh.get(i).getProvided_time());
                	}else{
                		map.put("dept_time", bh.get(i).getDept_time().toString());
                	}
                	map.put("pick_source_id", String.valueOf(pick_source.getId()));
                	map.put("pick_source_name", String.valueOf(pick_source.getName()));
                	map.put("drop_dest_id", String.valueOf(pick_destin.getId()));
                	map.put("drop_dest_name", String.valueOf(pick_destin.getName()));
                	map.put("scource", source.getName());
                	map.put("scource_id", String.valueOf(source.getId()));
                	map.put("destination", destin.getName());
                	map.put("destination_id", String.valueOf(destin.getId()));
                	map.put("number_of_ticket", String.valueOf(bh.get(i).getNumber_of_booking()));
                	map.put("provided_time", bh.get(i).getProvided_time());
                	map.put("status", bh.get(i).getStatus());
                	map.put("time_status", "past");
                	list.add(map);
            	}
            }
            System.out.println("KKKKKKKKKKKKKKK444444444");
            System.out.println(list);
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		finally {
            session.flush();
            session.close();
        }
		return list;
	}
	public List<Map<String,Object>> get_request_booking_id(int id){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();      
		List<Booking_Request_Master> bh = new ArrayList<Booking_Request_Master>();	
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		try {
            trns1 = session.beginTransaction();
            bh = session.createQuery("from Booking_Request_Master where user_id=? and id=?")
            		.setParameter(0, user.getAuthentic()).setParameter(1, id).list();
            System.out.println(bh.size());
            for(int i=0; i<bh.size();i++){
            	Pickup_Location_Master pick_source=new Pickup_Location_Master();
            	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getSource_id()).list().get(0);
            	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
            	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getDestination_id()).list().get(0);
            	
            	Location_Master source=new Location_Master();
            	source = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getFrom_id()).list().get(0);
            	Location_Master destin=new Location_Master();
            	destin = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getTo_id()).list().get(0);
            	Map<String,Object> map=new HashMap<String,Object>();
            	map.put("id", String.valueOf(bh.get(i).getId()));
            	map.put("dept_date", bh.get(i).getDept_date().toString());
            	if(bh.get(i).getStatus().equals("Confirmed")){
            		map.put("dept_time", bh.get(i).getProvided_time());
            	}else{
            		map.put("dept_time", bh.get(i).getDept_time().toString());
            	}
            	map.put("pick_source_id", String.valueOf(pick_source.getId()));
            	map.put("pick_source_name", String.valueOf(pick_source.getName()));
            	map.put("drop_dest_id", String.valueOf(pick_destin.getId()));
            	map.put("drop_dest_name", String.valueOf(pick_destin.getName()));
            	map.put("scource", source.getName());
            	map.put("scource_id", String.valueOf(source.getId()));
            	map.put("destination", destin.getName());
            	map.put("destination_id", String.valueOf(destin.getId()));
            	map.put("number_of_ticket", String.valueOf(bh.get(i).getNumber_of_booking()));
            	map.put("provided_time", bh.get(i).getProvided_time());
            	map.put("status", bh.get(i).getStatus());
            	
            	list.add(map);
            }
            System.out.println(list);
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		finally {
            session.flush();
            session.close();
        }
		return list;
	}
	public List<Map<String,Object>> get_sch_bus_info(int id){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();      
		List<Booking_Master> bh = new ArrayList<Booking_Master>();	
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		try {
            trns1 = session.beginTransaction();
            bh = session.createQuery("from Booking_Master where user_id=? and id=?")
            		.setParameter(0, user.getAuthentic()).setParameter(1, id).list();
            System.out.println(bh.size());
            for(int i=0;i<bh.size();i++){
            	List<Schedule_Master> sch = new ArrayList<Schedule_Master>();	
            	sch = session.createQuery("from Schedule_Master where id=?")
                		.setParameter(0, bh.get(i).getSchedule_id()).list();
            	
            	List<Bus_Master> bus = new ArrayList<Bus_Master>();	
            	bus = session.createQuery("from Bus_Master where id=?")
                		.setParameter(0, sch.get(0).getBus_id()).list();
            	
            	Map<String,Object> map=new HashMap<String,Object>();
            	map.put("id", bus.get(0).getId());
            	map.put("bus_model", bus.get(0).getModel());
            	map.put("plate_number", bus.get(0).getPlate_number());
            	map.put("number_of_seat", bus.get(0).getNumber_of_seat());
            	
            	list.add(map);
            } 	
            System.out.println(list);
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		finally {
            session.flush();
            session.close();
        }
		return list;
	}
	public List<Map<String,Object>> get_sch_driver_info(int id){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();      
		List<Booking_Master> bh = new ArrayList<Booking_Master>();	
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		try {
            trns1 = session.beginTransaction();
            bh = session.createQuery("from Booking_Master where user_id=? and id=?")
            		.setParameter(0, user.getAuthentic()).setParameter(1, id).list();
            System.out.println(bh.size());
            for(int i=0;i<bh.size();i++){
            	List<Schedule_Master> sch = new ArrayList<Schedule_Master>();	
            	sch = session.createQuery("from Schedule_Master where id=?")
                		.setParameter(0, bh.get(i).getSchedule_id()).list();
            	
            	List<User_Info> bus = new ArrayList<User_Info>();	
            	bus = session.createQuery("from User_Info where id=?")
                		.setParameter(0, sch.get(0).getDriver_id()).list();
            	
            	Map<String,Object> map=new HashMap<String,Object>();
            	map.put("id", bus.get(0).getId());
            	map.put("name", bus.get(0).getName());
            	map.put("email", bus.get(0).getEmail());
            	map.put("phone_number", bus.get(0).getPhone_number());
            	
            	list.add(map);
            } 	
            System.out.println(list);
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		finally {
            session.flush();
            session.close();
        }
		return list;
	}
	//======================== combination for choosing bus till ============================
	List<List<Map<String,Object>>> list =new ArrayList<List<Map<String,Object>>>();
	List<Integer> total_choosen_bus_list=new ArrayList<Integer>();
	int number_of_bus=0;		// temporary use (value will always change)
	int total_bus=0;			// permanent use (value will never change)
	List<List<Map<String,Object>>> list_bus_choosen =new ArrayList<List<Map<String,Object>>>();	
		
	public String customer_booking(Customer_Booking[] customer_booking) throws ParseException{	
		System.out.println("customer_booking");
		Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession(); 
        
		try {
            trns = session.beginTransaction();
            
            //======================== Start loop create schedule ====================================
            for(int x=0;x<customer_booking.length;x++){
            	System.out.println("cb.length...");
            	Customer_Booking cb=customer_booking[x];
            	int total_seat_of_all_bus=0;
                int number_of_passenger=0;
                List<Booking_Master> all_booker1=new ArrayList<Booking_Master>();
                List<Map<String,Object>> all_bus =new ArrayList<Map<String,Object>>();  
                List<Schedule_Master> schedule=new ArrayList<Schedule_Master>();
                Custom_Dao custom_imp=new Custom_Imp();
                Custom_Imp c=new Custom_Imp();
                Custom_Imp booking=new Custom_Imp();
                booking.list=new ArrayList<List<Map<String,Object>>>();
                booking.total_choosen_bus_list=new ArrayList<Integer>();
                booking.list_bus_choosen=new ArrayList<List<Map<String,Object>>>();
                booking.number_of_bus=0;
                booking.total_bus=0;
                
                Pickup_Location_Master pick_source=new Pickup_Location_Master();
              	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, cb.getSource()).list().get(0);
              	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
              	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, cb.getDestination()).list().get(0);
                
              	schedule=session.createQuery("from Schedule_Master where dept_date=:date and dept_time=:time and to_id=:to and from_id=:from")
    					.setDate("date",java.sql.Date.valueOf(cb.getDate()))
    					.setTime("time", java.sql.Time.valueOf(cb.getTime()))
    					.setParameter("to", pick_destin.getLocation_id())
    					.setParameter("from", pick_source.getLocation_id()).list();
              	
              	Boolean check_ass=true; // Check whether we can assign this passenger to existing schedule or not
              	for(int i=0;i<schedule.size();i++){
              		if(schedule.get(i).getRemaining_seat()>=cb.getNumber_of_seat()){
              			//Assign New Passenger here
        				Booking_Master new_booker=new Booking_Master();
        				new_booker.setSource_id(pick_source.getId());
        				new_booker.setFrom_id(pick_source.getLocation_id());
        				new_booker.setDestination_id(pick_destin.getId());
        				new_booker.setTo_id(pick_destin.getLocation_id());
        				new_booker.setDept_time(java.sql.Time.valueOf(cb.getTime()));
        				new_booker.setDept_date(java.sql.Date.valueOf(cb.getDate()));
        				new_booker.setCreated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
        				new_booker.setUpdated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
        				new_booker.setUser_id(user.getAuthentic());
        				new_booker.setNumber_booking(cb.getNumber_of_seat());
        				new_booker.setNotification("Booked");
        				new_booker.setCode(Custom_Imp.getBookingSequence());
        				new_booker.setSchedule_id(schedule.get(i).getId());
        				new_booker.setAdult(cb.getAdult());
        				new_booker.setChild(cb.getChild());
        				new_booker.setDescription("customer");
        				new_booker.setQr(pick_source.getLocation_id()+""+pick_destin.getLocation_id()+""+cb.getDate()+""+cb.getTime()+""+user.getAuthentic());
        				session.save(new_booker);
        				
        				
        				Query query = session.createQuery("update Schedule_Master set number_booking=:num_booking, remaining_seat=:remain_seat, number_customer=:number_customer" +
                				" where id = :id");
                        query.setParameter("num_booking", schedule.get(i).getNumber_booking()+cb.getNumber_of_seat());
                        query.setParameter("remain_seat", schedule.get(i).getRemaining_seat()-cb.getNumber_of_seat());
                        query.setParameter("number_customer", schedule.get(i).getNumber_customer()+cb.getNumber_of_seat());
                        query.setParameter("id", schedule.get(i).getId());
                        System.out.println(query);
                        int result = query.executeUpdate();
                                         
                        check_ass=false;
                        break;
              		}
              	}
              	
              	if(check_ass){
              		//1. query list of all available bus (result must be in order number of seat for small to big )
                    all_bus= custom_imp.get_all_bus(session,cb,pick_source.getLocation_id(),pick_destin.getLocation_id());
                    //2. get all bookers
                    all_booker1=custom_imp.get_all_booker(session, pick_source.getLocation_id(), pick_destin.getLocation_id(), cb.getTime(), cb.getDate());
                    //3. Find out number of total passengers in DB 
                    for(int p=0;p<all_booker1.size();p++){
        	            number_of_passenger+=all_booker1.get(p).getNumber_booking();
        	        }
        	        number_of_passenger+=cb.getNumber_of_seat();
        	        
        	        //4. if have bus or have no bus
                    if(all_bus.size()>0){
                    	//5. Fins total seat of all bus
                    	for(int i=0;i<all_bus.size();i++){
           	             	total_seat_of_all_bus+=Integer.valueOf((String) all_bus.get(i).get("number_of_seat")); 
                    	}
                    	//check whether people is over all available bus seat or not
                    	if(number_of_passenger<=total_seat_of_all_bus){
                    		//6. create new schedule
                        	Map<Object, List<Booking_Master>> sch_with_users=custom_imp.create_schedule(booking,session,all_bus,all_booker1,pick_source,pick_destin,cb,total_seat_of_all_bus,number_of_passenger); 			// 6. Set/Reset New Schedule 
                        	if(sch_with_users.size()==0){
                        		custom_imp.create_unassigned_booking(session,cb,pick_source,pick_destin);
                        		//return "over_bus_available";
                        	}else{
                        		//Get List Existing Driver Assign match with Bus
                        		List<Integer> existing_bus_driver= custom_imp.get_existing_bus_and_driver(booking,session,cb,pick_source.getLocation_id(),pick_destin.getLocation_id());
                        		//7.Delete Schedule
                            	int delete=delete_Schedule(session,pick_source.getLocation_id(),pick_destin.getLocation_id(),cb.getTime(), cb.getDate());	// 5. Delete old Schedule 
                        		
                            	System.out.println(booking.list_bus_choosen);
                            	for(int h=0;h<sch_with_users.size();h++){
                            		int num_booking=0;
                            		int num_customer=0;
                            		int number_stu=0;
                            		Schedule_Master sch=new Schedule_Master();
                            		sch.setBus_id(Integer.valueOf((String) booking.list_bus_choosen.get(0).get(h).get("id")));
                            		sch.setDriver_id(existing_bus_driver.get(h));
                            		sch.setSource_id(pick_source.getId());
                            		sch.setDestination_id(pick_destin.getId());
                            		sch.setFrom_id(pick_source.getLocation_id());
                            		sch.setTo_id(pick_destin.getLocation_id());
                            		sch.setDept_date(java.sql.Date.valueOf(cb.getDate()));
                            		sch.setDept_time(java.sql.Time.valueOf(cb.getTime()));
                            		sch.setCreated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
                            		sch.setUpdated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
                            		sch.setCode(Custom_Imp.getScheduleSequence());
                            		session.save(sch);
                            		for(int y=0;y<sch_with_users.get(h).size();y++){
                            			System.out.println("kkkkk");
                            			num_booking+=sch_with_users.get(h).get(y).getNumber_booking();
                            			if(sch_with_users.get(h).get(y).getDescription().equals("customer")){
											num_customer+=sch_with_users.get(h).get(y).getNumber_booking();
										}else       //student
											{
											number_stu+=sch_with_users.get(h).get(y).getNumber_booking();
										}
                            			Query query = session.createQuery("update Booking_Master set schedule_id = :sch_id, qr= :qr" +
                                				" where id = :id");
        			                    query.setParameter("sch_id", sch.getId());
        			                    query.setParameter("qr", pick_source.getLocation_id()+""+pick_destin.getLocation_id()+""+cb.getDate()+""+cb.getTime()+""+sch_with_users.get(h).get(y).getId());
        			                    query.setParameter("id", sch_with_users.get(h).get(y).getId());
        			                    int result = query.executeUpdate();
                        			}
                            		
                            		sch.setNumber_booking(num_booking);
                            		sch.setRemaining_seat(Integer.valueOf((String) booking.list_bus_choosen.get(0).get(h).get("number_of_seat"))-num_booking);
                            		sch.setNumber_customer(num_customer);
                            		sch.setNumber_staff(0);
                            		sch.setNumber_student(number_stu);
                        			for(int y=0;y<sch_with_users.get(h).size();y++){
                        				System.out.print(sch_with_users.get(h).get(y).getId()+" ");
                        			}
                        			System.out.println(" ");
                        			for(int y=0;y<sch_with_users.get(h).size();y++){
                        				System.out.print(sch_with_users.get(h).get(y).getNumber_booking()+" ");
                        			}
                        			System.out.println(" ");
                        		}
                        	}
                    	}else{
                    		System.out.println("Over All Bus available seat 1!!!");
                    		custom_imp.create_unassigned_booking(session,cb,pick_source,pick_destin);
                    		//return "over_bus_available";
                    		
                    	}
                    	
                    }else{
        	      	    //System.out.println("No Bus available!!!");
        	      	    custom_imp.create_unassigned_booking(session,cb,pick_source,pick_destin);
        	      	    //return "no_bus_available";
        	        }
              	}
            }
            //======================== End loop create schedule ====================================        
            trns.commit();
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	trns.rollback();
        	return "error";
        }finally {
            session.flush();
            session.close();
        }           
		return "success";
	}	
	public void create_unassigned_booking(Session session,Customer_Booking cb,Pickup_Location_Master pick_source,Pickup_Location_Master pick_destin){
		Custom_Imp c=new Custom_Imp();
		try{
			// Record Booking of customer but haven't assign schedule yet
    		Booking_Master new_booker=new Booking_Master();
			new_booker.setSource_id(pick_source.getId());
			new_booker.setFrom_id(pick_source.getLocation_id());
			new_booker.setDestination_id(pick_destin.getId());
			new_booker.setTo_id(pick_destin.getLocation_id());
			new_booker.setDept_time(java.sql.Time.valueOf(cb.getTime()));
			new_booker.setDept_date(java.sql.Date.valueOf(cb.getDate()));
			new_booker.setCreated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
			new_booker.setUpdated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
			new_booker.setUser_id(user.getAuthentic());
			new_booker.setNumber_booking(cb.getNumber_of_seat());
			new_booker.setNotification("Unassigned");
			new_booker.setCode(Custom_Imp.getBookingSequence());
			new_booker.setAdult(cb.getAdult());
			new_booker.setChild(cb.getChild());
			new_booker.setDescription("customer");
			new_booker.setQr(pick_source.getLocation_id()+""+pick_destin.getLocation_id()+""+cb.getDate()+""+cb.getTime()+""+user.getAuthentic());
			session.save(new_booker);
		} catch (RuntimeException e) {
	    	e.printStackTrace();
	    }        
	}
	public Map<Object, List<Booking_Master>> create_schedule(Custom_Imp booking,Session session, List<Map<String,Object>> all_bus, List<Booking_Master> all_booker1, Pickup_Location_Master pick_source, Pickup_Location_Master pick_destin, Customer_Booking cb, int total_seat_of_all_bus,int number_of_passenger){	
		System.out.println("create_schedule");
		Boolean recursive=true;
		List<Booking_Master> user_sch_assign=new ArrayList<Booking_Master>();
        Map<Object,List<Booking_Master>> sch_with_users=new HashMap<Object,List<Booking_Master>>();
        Custom_Dao custom_imp=new Custom_Imp();  
        Custom_Imp c=new Custom_Imp();
//		java.sql.Timestamp.valueOf(c.DateTimeNow())
		while(recursive){
			int ib=0; //index of passenger
			Boolean last_bus_choosing=true;
			Boolean current_pass_assign=true;
			booking.list_bus_choosen =new ArrayList<List<Map<String,Object>>>();
			user_sch_assign=new ArrayList<Booking_Master>();
			int total_seat_of_bus_chosen = number_of_passenger; //for recursive purpose to increase passenger in order to extend more bus
			int next_total_seat_of_bus_chosen = 0; 
			try {
				//Find out number of total passengers in DB again
				number_of_passenger=0;
	            for(int p=0;p<all_booker1.size();p++){
		            number_of_passenger+=all_booker1.get(p).getNumber_booking();
		        }
	            
				//Final Bus Chosen the correct bus because people always accept even 1
	            booking.list_bus_choosen=custom_imp.choose_correct_bus(booking,all_bus,pick_source,pick_destin,total_seat_of_bus_chosen,total_seat_of_all_bus);   //total_seat_of_bus_chosen==number_of_passenger
	            
	            if(booking.list_bus_choosen.size()>0){
	            	List<Map<String, Object>> sort_bus =new ArrayList<Map<String, Object>>(booking.list_bus_choosen.get(0));
			        Collections.sort(sort_bus, new Comparator<Map<String,Object>> () {
				         public int compare(Map<String, Object> m1, Map<String, Object> m2) {
				             return (Integer.valueOf((String) m2.get("number_of_seat"))).compareTo(Integer.valueOf((String) m1.get("number_of_seat"))); //descending order
				         }
				     });
			        booking.list_bus_choosen=new ArrayList<List<Map<String,Object>>>();
			        booking.list_bus_choosen.add(sort_bus);
			        List<Integer> tem_pass_assign=new ArrayList<Integer>();
			        next_total_seat_of_bus_chosen=0;  //for recursive purpose
			        
		            for(int i=0;i<booking.list_bus_choosen.get(0).size();i++){
		            	System.out.println("Number of toal seat: "+booking.list_bus_choosen.get(0).get(i).get("number_of_seat"));
		            	List<Booking_Master> user_each_bus=new ArrayList<Booking_Master>();
		            	next_total_seat_of_bus_chosen+=Integer.valueOf((String) booking.list_bus_choosen.get(0).get(i).get("number_of_seat"));
		            	int total_pass_each_sch=0;
		        		for(int j=0;j<all_booker1.size()&&total_pass_each_sch<Integer.valueOf((String) booking.list_bus_choosen.get(0).get(i).get("number_of_seat"));j++){
		        			Boolean status_assign=true;
		        			//check user already assign or not yet(if already assign break loop;
		        			for(int k=0;k<user_sch_assign.size();k++){
		        				if(user_sch_assign.get(k).getId()==all_booker1.get(j).getId()){
		        					status_assign=false;
		        				}
		        			}
		        			System.out.println(status_assign);
		        			if(status_assign){
		        				if(total_pass_each_sch+all_booker1.get(j).getNumber_booking()<Integer.valueOf((String) booking.list_bus_choosen.get(0).get(i).get("number_of_seat"))){
		        					user_sch_assign.add(all_booker1.get(j)); 
		        					user_each_bus.add(all_booker1.get(j));
		        					tem_pass_assign.add(j);
		        					total_pass_each_sch+=all_booker1.get(j).getNumber_booking();
		        				}else if(total_pass_each_sch+all_booker1.get(j).getNumber_booking()==Integer.valueOf((String) booking.list_bus_choosen.get(0).get(i).get("number_of_seat"))){
		        					user_sch_assign.add(all_booker1.get(j)); 
		        					tem_pass_assign.add(j);
		        					user_each_bus.add(all_booker1.get(j)); 
		        					total_pass_each_sch+=all_booker1.get(j).getNumber_booking();
		        					break; // Bus Already Full, No more assign
		        				}     				
		        			}
		        		}
		        		
		        		if(current_pass_assign&&total_pass_each_sch+cb.getNumber_of_seat()<=Integer.valueOf((String) booking.list_bus_choosen.get(0).get(i).get("number_of_seat"))){
		    				//Assign New Passenger here
		    				Booking_Master new_booker=new Booking_Master();
		    				new_booker.setSource_id(pick_source.getId());
		    				new_booker.setFrom_id(pick_source.getLocation_id());
		    				new_booker.setDestination_id(pick_destin.getId());
		    				new_booker.setTo_id(pick_destin.getLocation_id());
		    				new_booker.setDept_time(java.sql.Time.valueOf(cb.getTime()));
		    				new_booker.setDept_date(java.sql.Date.valueOf(cb.getDate()));
		    				new_booker.setCreated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
		    				new_booker.setUpdated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
		    				new_booker.setUser_id(user.getAuthentic());
		    				new_booker.setNumber_booking(cb.getNumber_of_seat());
		    				new_booker.setNotification("Booked");
		    				new_booker.setCode(Custom_Imp.getBookingSequence());
		    				new_booker.setAdult(cb.getAdult());
		    				new_booker.setChild(cb.getChild());
		    				new_booker.setDescription("customer");
		    				session.save(new_booker);
		    				
		    				
		    				current_pass_assign=false;
		    				total_pass_each_sch+=cb.getNumber_of_seat();
		    				user_each_bus.add(new_booker);
		    			}
		        		System.out.println("total_pass_each_sch: "+total_pass_each_sch);
		        		sch_with_users.put(i, user_each_bus);
		        	}

		            //if(tem_pass_assign.size()<all_booker1.size()+cb.getNumber_of_seat()&&total_seat_of_bus_chosen<next_total_seat_of_bus_chosen){
		            if(tem_pass_assign.size()==all_booker1.size()&&(!current_pass_assign)){
		            	System.out.println("Recursive!!!! 1");		
		            	break;
		    		}else if((tem_pass_assign.size()<all_booker1.size()||current_pass_assign)&&last_bus_choosing){
		    			System.out.println("Recursive!!!! 3");
		    			number_of_passenger=next_total_seat_of_bus_chosen+1;
		    			if(total_seat_of_all_bus==next_total_seat_of_bus_chosen){
		    				last_bus_choosing=false;
		    			}
		    		}else{
		    			System.out.println("Recursive!!!! 4");
		    			sch_with_users=new HashMap<Object,List<Booking_Master>>();
		    			break;
		    		}
	            }else{
	            	System.out.println("Recursive!!!! 5");
	    			sch_with_users=new HashMap<Object,List<Booking_Master>>();
	    			break;
	            }
		        
	        } catch (RuntimeException e) {
	        	e.printStackTrace();
	        }  
		}
		  
		return sch_with_users;
	}
	public List<List<Map<String,Object>>> choose_correct_bus(Custom_Imp booking,List<Map<String,Object>> all_bus, Pickup_Location_Master pick_source, Pickup_Location_Master pick_destin, int number_of_passenger, int total_seat_of_all_bus){ 
		System.out.println("choose_correct_bus");
		List<List<Map<String,Object>>> list_bus_choosen =new ArrayList<List<Map<String,Object>>>(); 
		booking.total_bus = all_bus.size();											
		booking.number_of_bus=all_bus.size();
  	    // Reset old data to empty
		booking.total_choosen_bus_list=new ArrayList<Integer>();
		booking.list =new ArrayList<List<Map<String,Object>>>();     
		try {	  
            
		         //=========3. Start The Process of choosing the correct total bus
		         for(int i=1;i<=booking.total_bus;i++){
		             printCombination(booking,all_bus, all_bus.size(), i,number_of_passenger, total_seat_of_all_bus);
		         }
		         //=========4. After choosing the correct total bus
		         Collections.sort(booking.total_choosen_bus_list);							
		         for(int i=0;i<booking.list.size();i++){
		            int sum_each_bus=0;
		            for(int j=0;j<booking.list.get(i).size();j++){
		                sum_each_bus+=Integer.valueOf((String) booking.list.get(i).get(j).get("number_of_seat"));
		            }
		            if(sum_each_bus==booking.total_choosen_bus_list.get(0)){ 
		               list_bus_choosen.add(booking.list.get(i));
		               break;
		            }
		         } 
	
        } catch (RuntimeException e) {
        	e.printStackTrace();
        } 
		System.out.println("List :      "+booking.list.size());
		return list_bus_choosen;
	}
	public List<Booking_Master> get_all_booker(Session session,int from_id, int to_id, String time, String date){  
		System.out.println("get_all_booker");
		List<Booking_Master> all_booker1=new ArrayList<Booking_Master>();    
		try {
            all_booker1 = session.createQuery("from Booking_Master where description='customer' and from_id=? and to_id=? and dept_time=? and dept_date=? order by number_booking desc")
            		.setParameter(0,from_id).setParameter(1, to_id).setTime(2, java.sql.Time.valueOf(time)).setDate(3,java.sql.Date.valueOf(date)).list();

        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		return all_booker1;
	}
	
	public List<Map<String,Object>> get_all_bus(Session session,Customer_Booking cb,int from, int to) throws ParseException{
		System.out.println("get_all_bus");
		List<Bus_Master> query_all_bus=new ArrayList<Bus_Master>();
		List<Map<String,Object>> all_bus =new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> same_date_route =new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> same_date_diff_route =new ArrayList<Map<String,Object>>();
		Custom_Dao custom_imp=new Custom_Imp();
		try {
			same_date_route=custom_imp.same_date_same_route(session, cb, from, to);
			List<Integer> unava1= (List<Integer>) same_date_route.get(0).get("unavailable_bus");
			same_date_diff_route=custom_imp.same_date_differ_route(session, cb, from, to);
			List<Integer> unava2= (List<Integer>) same_date_diff_route.get(0).get("unavailable_bus");
			String excep="";
			for(int i=0;i<unava1.size();i++){
				excep+=" and id!="+unava1.get(i);
			}
			for(int i=0;i<unava2.size();i++){
				excep+=" and id!="+unava2.get(i);
			}
            query_all_bus = session.createQuery("from Bus_Master where enabled=?"+excep+" order by number_of_seat asc").setBoolean(0, true).list();  
            System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
            System.out.println("from Bus_Master where enabled=?"+excep+" order by number_of_seat asc");
            System.out.println("query_all_bus size: "+ query_all_bus.size());
            if(query_all_bus.size()>0){            
	              for(int i=0;i<query_all_bus.size();i++){	
	                  Map<String,Object> map =new HashMap<String,Object>();				
	                  map.put("bus_model", query_all_bus.get(i).getModel());
	                  map.put("number_of_seat", String.valueOf(query_all_bus.get(i).getNumber_of_seat()));
	                  map.put("id", String.valueOf(query_all_bus.get(i).getId()));
	                  all_bus.add(map);
	              } 
            }
	              
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		return all_bus;
	}
	//Check Bus Available and not from the same route 
	
	public List<Map<String,Object>> same_date_same_route(Session session,Customer_Booking cb,int from, int to) throws ParseException{
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
	public List<Map<String,Object>> same_date_differ_route(Session session,Customer_Booking cb,int from, int to) throws ParseException{
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
	public Boolean time_same_date(String user_time, String time,long time_dura) throws ParseException{
 
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
	public int delete_Schedule(Session session, int from_id,int to_id,String time, String date){
		System.out.println("delete_Schedule");
		try {
            Query q = session.createQuery("delete Schedule_Master where from_id=? and to_id=? and dept_time=? and dept_date=?");
        	q.setParameter(0, from_id);
        	q.setParameter(1, to_id);
        	q.setTime(2, java.sql.Time.valueOf(time));
        	q.setDate(3, java.sql.Date.valueOf(date));
        	int result = q.executeUpdate();
        	return result;
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	return 0;
        }    
	}
	
    static void combinationUtil(Custom_Imp booking,List<Map<String,Object>> all_bus, List<Map<String,Object>> data, int start,
                                int end, int index, int r,int all_p,int all_bus_seat)
    {
        if (index == r)
        {
        	List<Map<String,Object>> bus_choosen=new ArrayList<Map<String,Object>>();
            int total_current_bus_seat=0;
            for (int j=0; j<r; j++){
               /// total_current_bus_seat+=data[j];
               /// bus_choosen.add(data[j]);
            	System.out.print(data.get(j).get("number_of_seat")+" ");
            	total_current_bus_seat+=Integer.valueOf((String) data.get(j).get("number_of_seat"));
            	Map<String,Object> map=new HashMap<String,Object>();
            	map.put("bus_model", data.get(j).get("bus_model"));
                map.put("number_of_seat", data.get(j).get("number_of_seat"));
                map.put("id", data.get(j).get("id"));
            	bus_choosen.add(map);
            }
           System.out.println(" ");
           System.out.println(total_current_bus_seat);
           System.out.println(" ");
            if(total_current_bus_seat>=all_p){
            	if(bus_choosen.size()<booking.number_of_bus){
            		booking.number_of_bus=bus_choosen.size();
            		booking.list.add(bus_choosen);
            		booking.total_choosen_bus_list.add(total_current_bus_seat);
            	} else if(bus_choosen.size()==booking.total_bus){
            		booking.list.add(bus_choosen);
            		booking.total_choosen_bus_list.add(total_current_bus_seat);
            	}
            }else{
            	System.out.println("Over available bus");
            }
            return;
        }

        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            ///data[index] = Integer.valueOf(all_bus.get(i).get("Bus_Seat"));
        	
        	Map<String,Object> map=new HashMap<String,Object>();
//        	map.put("Bus_Seat", all_bus.get(i).get("Bus_Seat"));
//        	map.put("Bus_Model", all_bus.get(i).get("Bus_Model"));
        	 map.put("bus_model", all_bus.get(i).get("bus_model"));
             map.put("number_of_seat", all_bus.get(i).get("number_of_seat"));
             map.put("id", all_bus.get(i).get("id"));
        	data.add(index,map);
        	
            combinationUtil(booking,all_bus, data, i+1, end, index+1, r,all_p, all_bus_seat);
        }
    }

    static void printCombination(Custom_Imp booking,List<Map<String,Object>> all_bus, int n, int r,int all_p,int all_bus_seat)
    {
        // A temporary array to store all combination one by one
        ///int data[]=new int[r];
    	List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();

        // Print all combination using temprary array 'data[]'
        combinationUtil(booking,all_bus, data, 0, n-1, 0, r,all_p,all_bus_seat);
    }
  
    public List<Integer> get_existing_bus_and_driver(Custom_Imp booking,Session session,Customer_Booking cb,int from, int to){
    	List<Schedule_Master> sch=new ArrayList<Schedule_Master>();
    	List<Integer> final_driver_list=new ArrayList<Integer>();
		try {
			
			sch=session.createQuery("from Schedule_Master where dept_date=:date and dept_time=:time and to_id=:to and from_id=:from")
					.setDate("date",java.sql.Date.valueOf(cb.getDate()))
					.setTime("time", java.sql.Time.valueOf(cb.getTime()))
					.setParameter("to", to)
					.setParameter("from", from).list();
			
			// Find driver list and assign driver
			List<Integer> driver_list=new ArrayList<Integer>();
			List<Integer> assign_driver=new ArrayList<Integer>();
			for(int i=0; i<booking.list_bus_choosen.get(0).size();i++){
				Boolean status=true;
				for(int j=0;j<sch.size();j++){
					if(sch.get(j).getBus_id()==Integer.valueOf((String) booking.list_bus_choosen.get(0).get(i).get("id"))){
						driver_list.add(sch.get(j).getDriver_id());
						status=false;
						assign_driver.add(j);
						break;
					}
				}
				if(status){
					driver_list.add(0);
				}
			}
			
			// Find un-assign diver
			List<Integer> unassign_driver=new ArrayList<Integer>();
			for(int i=0;i<sch.size();i++){
				Boolean status=true;
				if(assign_driver.size()>0){
					for(int j=0;j<assign_driver.size();j++){
						if(i==assign_driver.get(j)){
							status=false;
							break;
						}
					}
					if(status){
						unassign_driver.add(sch.get(i).getDriver_id());
					}
				}else{
					//not assign at all
					unassign_driver.add(sch.get(i).getDriver_id());
				}
			}
			
			// Add unassign driver to driver list
			int un_assign=0;
			for(int i=0;i<driver_list.size();i++){
				if(driver_list.get(i)==0){
					if(un_assign<unassign_driver.size()){
						final_driver_list.add(unassign_driver.get(un_assign));
						un_assign++;
					}else{
						final_driver_list.add(0);
					}
				}else{
					final_driver_list.add(driver_list.get(i));
				}
			}
			
	              
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		return final_driver_list;
    }
    //======================== combination for choosing bus till here ============================

	public String customer_request_booking(Customer_Booking cb){
		IdUser user= new IdUser();
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();       
        Booking_Request_Master book = new Booking_Request_Master();
        Custom_Imp c=new Custom_Imp();
//		java.sql.Timestamp.valueOf(c.DateTimeNow())
        System.out.println(""+cb.getSource());
		try {
            trns1 = session.beginTransaction();
            Pickup_Location_Master pick_source=new Pickup_Location_Master();
          	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?")
					.setParameter(0, cb.getSource()).list().get(0);
          	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
          	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?")
					.setParameter(0, cb.getDestination()).list().get(0);
            
          	
            book.setCreated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
            book.setUpdated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
            book.setDescription(cb.getDescription());
            book.setSource_id(cb.getSource());
            book.setFrom_id(pick_source.getLocation_id());
            book.setDestination_id(cb.getDestination());
            book.setTo_id(pick_destin.getLocation_id());
            book.setDept_date(java.sql.Date.valueOf(cb.getDate()));
            book.setDept_time(java.sql.Time.valueOf(cb.getTime()));
            book.setNumber_of_booking(cb.getNumber_of_seat());
            book.setAdult(cb.getAdult());
            book.setChild(cb.getChild());
            book.setUser_id(user.getAuthentic());
            book.setEnabled(true);
            book.setStatus("Pending");
            session.save(book);
            trns1.commit();
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	return "error";
        }    
		finally {
            session.flush();
            session.close();
        }
		return "success";
	}
	public String request_book_now(int id) throws ParseException{
		Request_Booking_Dao cus=new Request_Booking();
		Transaction trns1 = null;
		Customer_Booking cb=new Customer_Booking();
		Customer_Booking[] arr_cb=new Customer_Booking[1];
        Session session = HibernateUtil.getSessionFactory().openSession();     
        String book=null;
		try {
            trns1 = session.beginTransaction();
            Booking_Request_Master br= (Booking_Request_Master) session.createQuery("from Booking_Request_Master where id=?").setParameter(0, id).list().get(0);
           
            cb.setDate(br.getDept_date().toString().subSequence(0, 10).toString());
            cb.setTime(br.getProvided_time());
            cb.setSource(br.getSource_id());
            cb.setDestination(br.getDestination_id());
            cb.setNumber_of_seat(br.getNumber_of_booking());
            arr_cb[0]=cb;
            System.out.println();
            System.out.println(arr_cb[0].getDate());
            System.out.println(arr_cb[0].getTime());
            System.out.println(arr_cb[0].getSource());
            System.out.println(arr_cb[0].getDestination());
            book=cus.customer_booking(arr_cb);
           // if(book.equals("success")||book.equals("over_bus_available")||book.equals("no_bus_available")){
//            	Query query = session.createQuery("update Booking_Request_Master set enabled='false' where id = :id");
//            	query.setParameter("id", id);
//            	int result = query.executeUpdate();
//            	trns1.commit();
//            }
//            else if(book.equals("over_bus_available")||book.equals("no_bus_available")){
//            	Query query = session.createQuery("update Booking_Request_Master set status='Rejected' where id = :id");
//            	query.setParameter("id", id);
//            	int result = query.executeUpdate();
//            	trns1.commit();
//            }
            
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	return "error";
        }finally {
            session.flush();
            session.close();
        }              
		return book;
	}
	public String confirm_phone_number(UserModel id){
		Custom_Dao cus=new Custom_Imp();
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();     
		try {
            trns1 = session.beginTransaction();
            Query query = session.createQuery("update User_Info set phone_number=:phone_number where id = :id");
            query.setParameter("id", user.getAuthentic());
            query.setParameter("phone_number", id.getPhone());
            int result = query.executeUpdate();
            trns1.commit();
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	return "error";
        }finally {
            session.flush();
            session.close();
        }              
		return "success";
	}
	public String cancel_booking_ticket(int id){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();     
        List<Booking_Master> bo=new ArrayList<Booking_Master>();
        List<Map<String,Object>> all_bus =new ArrayList<Map<String,Object>>();  
        List<Schedule_Master> schedule=new ArrayList<Schedule_Master>();
        String book;
		try {
            trns1 = session.beginTransaction();
            
            bo=session.createQuery("from Booking_Master where id=:id")
					.setParameter("id", id).list();
            
            if(bo.size()>0){
            	schedule=session.createQuery("from Schedule_Master where id=:id")
    					.setParameter("id", bo.get(0).getSchedule_id()).list();
            	
            	Query query = session.createQuery("update Booking_Master set notification='Cancelled'" +
        				" where id = :id");
                query.setParameter("id", id);
                int result = query.executeUpdate();
                
                Query query1 = session.createQuery("update Schedule_Master set number_booking=:num_booking, remaining_seat=:remain_seat, number_customer=:number_customer" +
        				" where id = :id");
                query1.setParameter("num_booking", schedule.get(0).getNumber_booking()-bo.get(0).getNumber_booking());
                query1.setParameter("remain_seat", schedule.get(0).getRemaining_seat()+bo.get(0).getNumber_booking());
                query1.setParameter("number_customer", schedule.get(0).getNumber_customer()-bo.get(0).getNumber_booking());
                query1.setParameter("id", schedule.get(0).getId());
                int result1 = query1.executeUpdate();
                

                trns1.commit();  
            }else{
            	return "no_record";
            }
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	return "error";
        }finally {
            session.flush();
            session.close();
        }
		return "success";
	}
	
	public List<Map<String,Object>> get_qrcode(int id){
			Transaction trns1 = null;
	        Session session = HibernateUtil.getSessionFactory().openSession();   
	        List<Booking_Master> cr = new ArrayList<Booking_Master>();	
			
			List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
			try {
	            trns1 = session.beginTransaction();
	            cr = session.createQuery("from Booking_Master where id=:id").setParameter("id", id).list();
	            if(cr.get(0).getUser_id()==user.getAuthentic()&&cr.size()>0){
	            	Pickup_Location_Master pick_source=new Pickup_Location_Master();
	            	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, cr.get(0).getSource_id()).list().get(0);
	            	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
	            	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, cr.get(0).getDestination_id()).list().get(0);
	            	Location_Master source=new Location_Master();
	            	source = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, pick_source.getLocation_id()).list().get(0);
	            	Location_Master destin=new Location_Master();
	            	destin = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, pick_destin.getLocation_id()).list().get(0);
	            	Schedule_Master sch_ma=new Schedule_Master();
	            	sch_ma = (Schedule_Master) session.createQuery("from Schedule_Master where id=?").setParameter(0, cr.get(0).getSchedule_id()).list().get(0);

	            	Bus_Master bus=new Bus_Master();
	            	bus = (Bus_Master) session.createQuery("from Bus_Master where id=?").setParameter(0, sch_ma.getBus_id()).list().get(0);
	            	
	            	Map<String,Object> map=new HashMap<String,Object>();
	            	ByteArrayOutputStream out = QRCode.from(cr.get(0).getQr().toString()).to(ImageType.PNG).stream();  
					byte[] test = out.toByteArray();
					String encodedImage = Base64.getEncoder().encodeToString(test);
					
	            	map.put("id", cr.get(0).getId());
	            	map.put("dept_date", cr.get(0).getDept_date().toString().substring(0, 10));
	            	map.put("dept_time", cr.get(0).getDept_time().toString());
	            	map.put("scource", source.getName());
	            	map.put("pick_up", pick_source.getName());
	            	map.put("destination", destin.getName());
	            	map.put("drop_off", pick_destin.getName());
	            	map.put("number_of_ticket", String.valueOf(cr.get(0).getNumber_booking()));
	            	map.put("bus_model", bus.getModel());
	            	map.put("plate_number", bus.getPlate_number());
	            	map.put("notification", cr.get(0).getNotification());
	            	map.put("qrcode", encodedImage);
	            	if(sch_ma.getDriver_id()==0){
	            		map.put("diver_name", "no_driver");
	                	map.put("diver_phone_number", 0);
	            	}	
	            	else{
	            		User_Info driver=new User_Info();
	                	driver = (User_Info) session.createQuery("from User_Info where id=?").setParameter(0, sch_ma.getDriver_id()).list().get(0);
	                	map.put("diver_name", driver.getName());
	                	map.put("diver_phone_number", driver.getPhone_number());
	            	}
	            	list.add(map);
	            }	            
	            System.out.println(list);
	        } catch (RuntimeException e) {
	        	e.printStackTrace();
	        }    
			finally {
	            session.flush();
	            session.close();
	        }
			return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
		
	
	public static void main(String args[]){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();      
		List<Booking_Request_Master> bh = new ArrayList<Booking_Request_Master>();	
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		try {
            trns1 = session.beginTransaction();
            bh = session.createQuery("from Booking_Request_Master where user_id=? and dept_date>=? and enabled='true' order by dept_date asc")
            		.setParameter(0, 16).setDate(1, new Date()).list();
            System.out.println("KK");
            System.out.println(new Date().getTime());
            System.out.println(bh.size());
            for(int i=0; i<bh.size();i++){
            	Pickup_Location_Master pick_source=new Pickup_Location_Master();
            	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getSource_id()).list().get(0);
            	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
            	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getDestination_id()).list().get(0);
            	
            	Location_Master source=new Location_Master();
            	source = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getFrom_id()).list().get(0);
            	Location_Master destin=new Location_Master();
            	destin = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getTo_id()).list().get(0);
            	Map<String,Object> map=new HashMap<String,Object>();
            	map.put("id", String.valueOf(bh.get(i).getId()));
            	map.put("dept_date", bh.get(i).getDept_date().toString());
            	if(bh.get(i).getStatus().equals("Confirmed")){
            		map.put("dept_time", bh.get(i).getProvided_time());
            	}else{
            		map.put("dept_time", bh.get(i).getDept_time().toString());
            	}
            	map.put("pick_source_id", String.valueOf(pick_source.getId()));
            	map.put("pick_source_name", String.valueOf(pick_source.getName()));
            	map.put("drop_dest_id", String.valueOf(pick_destin.getId()));
            	map.put("drop_dest_name", String.valueOf(pick_destin.getName()));
            	map.put("scource", source.getName());
            	map.put("scource_id", String.valueOf(source.getId()));
            	map.put("destination", destin.getName());
            	map.put("destination_id", String.valueOf(destin.getId()));
            	map.put("number_of_ticket", String.valueOf(bh.get(i).getNumber_of_booking()));
            	map.put("provided_time", bh.get(i).getProvided_time());
            	map.put("status", bh.get(i).getStatus());
            	
            	list.add(map);
            }
            System.out.println(list);
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		finally {
            session.flush();
            session.close();
        }
	}

}
