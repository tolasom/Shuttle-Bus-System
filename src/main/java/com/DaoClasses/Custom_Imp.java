package com.DaoClasses;

import getInfoLogin.IdUser;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	Timestamp current_timestamp = new Timestamp(System.currentTimeMillis());
	Date current_date = new Date();
	public static String TimeNow(){
		Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss");
        String currentDateTimeString = sdf.format(d);
        System.out.println(currentDateTimeString);
        return currentDateTimeString;
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
		Boolean assign=true;
		try {
            trns1 = session.beginTransaction();
            pick.setName(np.getPickup_name());
            pick.setLocation_id(np.getLocation_id());
            pick.setEnabled(true);
            pick.setPermanent(false);
            pick.setCreated_at(current_timestamp);
            pick.setUpdated_at(current_timestamp);
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
		try {
            trns1 = session.beginTransaction();
            drop.setName(np.getDropoff_name());
            drop.setLocation_id(np.getLocation_id());
            drop.setEnabled(true);
            drop.setPermanent(false);
            drop.setCreated_at(current_timestamp);
            drop.setUpdated_at(current_timestamp);
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
            	Schedule_Master sch_ma=new Schedule_Master();
            	sch_ma = (Schedule_Master) session.createQuery("from Schedule_Master where id=?").setParameter(0, cr.get(i).getSchedule_id()).list().get(0);

            	Bus_Master bus=new Bus_Master();
            	bus = (Bus_Master) session.createQuery("from Bus_Master where id=?").setParameter(0, sch_ma.getBus_id()).list().get(0);
            	
            	Map<String,Object> map=new HashMap<String,Object>();
            	map.put("id", cr.get(i).getId());
            	map.put("dept_date", cr.get(i).getDept_date().toString());
            	map.put("dept_time", cr.get(i).getDept_time().toString());
            	map.put("scource", source.getName());
            	map.put("pick_up", pick_source.getName());
            	map.put("destination", destin.getName());
            	map.put("drop_off", pick_destin.getName());
            	map.put("number_of_ticket", String.valueOf(cr.get(i).getNumber_booking()));
            	map.put("bus_model", bus.getModel());
            	map.put("plate_number", bus.getPlate_number());
            	map.put("notification", cr.get(i).getNotification());
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
            
            //pass
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
                	
                	Map<String,Object> map=new HashMap<String,Object>();
                	map.put("id", bh.get(i).getId());
                	map.put("dept_date", bh.get(i).getDept_date().toString());
                	map.put("dept_time", bh.get(i).getDept_time().toString());
                	map.put("scource", source.getName());
                	map.put("destination", destin.getName());
                	map.put("number_of_ticket", String.valueOf(bh.get(i).getNumber_booking()));
                	map.put("bus_model", bus.getModel());
                	map.put("plate_number", bus.getPlate_number());
                	map.put("notification", bh.get(i).getNotification());
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
		try {
            trns1 = session.beginTransaction();
            bh = session.createQuery("from Booking_Request_Master where user_id=? and dept_date>=? and dept_time>? and enabled='true' order by dept_date asc")
            		.setParameter(0, user.getAuthentic()).setDate(1, new Date()).setTime(2, java.sql.Time.valueOf(TimeNow())).list();
            System.out.println("KK");
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
            	map.put("drop_dest_id", String.valueOf(pick_source.getId()));
            	map.put("drop_dest_name", String.valueOf(pick_source.getName()));
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
	
	//======================== combination for choosing bus till ============================
	static List<List<Map<String,Object>>> list =new ArrayList<List<Map<String,Object>>>();
	static List<Integer> total_choosen_bus_list=new ArrayList<Integer>();
	static int number_of_bus=0;		// temporary use (value will always change)
	static int total_bus=0;			// permanent use (value will never change)
	static List<List<Map<String,Object>>> list_bus_choosen =new ArrayList<List<Map<String,Object>>>();	
		
	public String customer_booking(Customer_Booking cb){	
		System.out.println("customer_booking");
		Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession(); 
        int total_seat_of_all_bus=0;
        int number_of_passenger=0;
        List<Booking_Master> all_booker1=new ArrayList<Booking_Master>();
        List<Map<String,Object>> all_bus =new ArrayList<Map<String,Object>>();  
        Custom_Dao custom_imp=new Custom_Imp();
		try {
            trns = session.beginTransaction();
            
            Pickup_Location_Master pick_source=new Pickup_Location_Master();
          	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, cb.getSource()).list().get(0);
          	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
          	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, cb.getDestination()).list().get(0);
            
            //1. query list of all available bus (result must be in order number of seat for small to big )
            all_bus= custom_imp.get_all_bus(session);
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
                	Map<Object, List<Booking_Master>> sch_with_users=custom_imp.create_schedule(session,all_bus,all_booker1,pick_source,pick_destin,cb,total_seat_of_all_bus,number_of_passenger); 			// 6. Set/Reset New Schedule 
                	if(sch_with_users.size()==0){
                		return "over_bus_available";
                	}else{
                		//7.Delete Schedule
                    	int delete=delete_Schedule(session,pick_source.getLocation_id(),pick_destin.getLocation_id(),cb.getTime(), cb.getDate());	// 5. Delete old Schedule 
                		
                    	System.out.println(list_bus_choosen);
                    	for(int h=0;h<sch_with_users.size();h++){
                    		int num_booking=0;
                    		Schedule_Master sch=new Schedule_Master();
                    		sch.setBus_id(Integer.valueOf((String) list_bus_choosen.get(0).get(h).get("id")));
                    		sch.setSource_id(pick_source.getId());
                    		sch.setDestination_id(pick_destin.getId());
                    		sch.setFrom_id(pick_source.getLocation_id());
                    		sch.setTo_id(pick_destin.getLocation_id());
                    		sch.setDept_date(java.sql.Date.valueOf(cb.getDate()));
                    		sch.setDept_time(java.sql.Time.valueOf(cb.getTime()));
                    		sch.setCreated_at(current_timestamp);
                    		sch.setUpdated_at(current_timestamp);
                    		sch.setCode(Custom_Imp.getScheduleSequence());
                    		session.save(sch);
                    		for(int y=0;y<sch_with_users.get(h).size();y++){
                    			System.out.println("kkkkk");
                    			num_booking+=sch_with_users.get(h).get(y).getNumber_booking();
                    			Query query = session.createQuery("update Booking_Master set schedule_id = :sch_id, qr= :qr" +
                        				" where id = :id");
			                    query.setParameter("sch_id", sch.getId());
			                    query.setParameter("qr", pick_source.getLocation_id()+""+pick_destin.getLocation_id()+""+cb.getDate()+""+cb.getTime()+""+sch_with_users.get(h).get(y).getId());
			                    query.setParameter("id", sch_with_users.get(h).get(y).getId());
			                    int result = query.executeUpdate();
                			}
                    		
                    		sch.setNumber_booking(num_booking);
                    		sch.setRemaining_seat(Integer.valueOf((String) list_bus_choosen.get(0).get(h).get("number_of_seat"))-num_booking);
                    		sch.setNumber_customer(num_booking);
                    		sch.setNumber_staff(0);
                    		sch.setNumber_student(0);
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
            		System.out.println("Over All Bus available seat!!!");
            		return "over_bus_available";
            	}
            	
            }else{
	      	  System.out.println("No Bus available!!!");
	      	  return "no_bus_available";
	        }
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
	public Map<Object, List<Booking_Master>> create_schedule(Session session, List<Map<String,Object>> all_bus, List<Booking_Master> all_booker1, Pickup_Location_Master pick_source, Pickup_Location_Master pick_destin, Customer_Booking cb, int total_seat_of_all_bus,int number_of_passenger){	
		System.out.println("create_schedule");
		Boolean recursive=true;
		List<Booking_Master> user_sch_assign=new ArrayList<Booking_Master>();
        Map<Object,List<Booking_Master>> sch_with_users=new HashMap<Object,List<Booking_Master>>();
        Custom_Dao custom_imp=new Custom_Imp();  
		while(recursive){
			int ib=0; //index of passenger
			Boolean last_bus_choosing=true;
			Boolean current_pass_assign=true;
			sch_with_users=new HashMap<Object,List<Booking_Master>>();
			list_bus_choosen =new ArrayList<List<Map<String,Object>>>();
			user_sch_assign=new ArrayList<Booking_Master>();
			int total_seat_of_bus_chosen = number_of_passenger; //for recursive purpose to increase passenger in order to extend more bus
			int next_total_seat_of_bus_chosen = 0; 
			try {
				//Find out number of total passengers in DB again
				number_of_passenger=0;
	            for(int p=0;p<all_booker1.size();p++){
		            number_of_passenger+=all_booker1.get(p).getNumber_booking();
		            System.out.println(all_booker1.get(p).getNumber_booking());
		        }
	            
				//Final Bus Chosen the correct bus because people always accept even 1
	            list_bus_choosen=custom_imp.choose_correct_bus(all_bus,pick_source,pick_destin,total_seat_of_bus_chosen,total_seat_of_all_bus);   //total_seat_of_bus_chosen==number_of_passenger
	            
	            if(list_bus_choosen.size()>0){
	            	List<Map<String, Object>> sort_bus =new ArrayList<Map<String, Object>>(list_bus_choosen.get(0));
			        Collections.sort(sort_bus, new Comparator<Map<String,Object>> () {
				         public int compare(Map<String, Object> m1, Map<String, Object> m2) {
				             return (Integer.valueOf((String) m2.get("number_of_seat"))).compareTo(Integer.valueOf((String) m1.get("number_of_seat"))); //descending order
				         }
				     });
			        list_bus_choosen=new ArrayList<List<Map<String,Object>>>();
			        list_bus_choosen.add(sort_bus);
			        System.out.println("list_bus_choosen: "+list_bus_choosen);
			        System.out.println("all_booker1 size: "+all_booker1.size());
			        List<Integer> tem_pass_assign=new ArrayList<Integer>();
			        next_total_seat_of_bus_chosen=0;  //for recursive purpose
			        
		            for(int i=0;i<list_bus_choosen.get(0).size();i++){
		            	System.out.println("Number of toal seat: "+list_bus_choosen.get(0).get(i).get("number_of_seat"));
		            	List<Booking_Master> user_each_bus=new ArrayList<Booking_Master>();
		            	next_total_seat_of_bus_chosen+=Integer.valueOf((String) list_bus_choosen.get(0).get(i).get("number_of_seat"));
		            	int total_pass_each_sch=0;
		        		for(int j=0;j<all_booker1.size()&&total_pass_each_sch<Integer.valueOf((String) list_bus_choosen.get(0).get(i).get("number_of_seat"));j++){
		        			Boolean status_assign=true;
		        			for(int k=0;k<user_sch_assign.size();k++){
		        				System.out.println(user_sch_assign.get(k).getId()+"  &&  "+all_booker1.get(j).getId());
		        				if(user_sch_assign.get(k).getId()==all_booker1.get(j).getId()){
		        					status_assign=false;
		        				}
		        			}
		        			System.out.println(status_assign);
		        			if(status_assign){
		        				if(total_pass_each_sch+all_booker1.get(j).getNumber_booking()<Integer.valueOf((String) list_bus_choosen.get(0).get(i).get("number_of_seat"))){
		        					user_sch_assign.add(all_booker1.get(j)); 
		        					user_each_bus.add(all_booker1.get(j));
		        					tem_pass_assign.add(j);
		        					total_pass_each_sch+=all_booker1.get(j).getNumber_booking();
		        				}else if(total_pass_each_sch+all_booker1.get(j).getNumber_booking()==Integer.valueOf((String) list_bus_choosen.get(0).get(i).get("number_of_seat"))){
		        					user_sch_assign.add(all_booker1.get(j)); 
		        					tem_pass_assign.add(j);
		        					user_each_bus.add(all_booker1.get(j)); 
		        					total_pass_each_sch+=all_booker1.get(j).getNumber_booking();
		        					break; // Bus Already Full, No more assign
		        				}     				
		        			}
		        		}
		        		
		        		if(current_pass_assign&&total_pass_each_sch+cb.getNumber_of_seat()<=Integer.valueOf((String) list_bus_choosen.get(0).get(i).get("number_of_seat"))){
		    				//Assign New Passenger here
		    				Booking_Master new_booker=new Booking_Master();
		    				new_booker.setSource_id(pick_source.getId());
		    				new_booker.setFrom_id(pick_source.getLocation_id());
		    				new_booker.setDestination_id(pick_destin.getId());
		    				new_booker.setTo_id(pick_destin.getLocation_id());
		    				System.out.println("POPO");
		    				System.out.println(cb.getDate());
		    				System.out.println(cb.getTime());
		    				new_booker.setDept_time(java.sql.Time.valueOf(cb.getTime()));
		    				System.out.println(cb.getDate());
		    				new_booker.setDept_date(java.sql.Date.valueOf(cb.getDate()));
		    				new_booker.setCreated_at(current_timestamp);
		    				new_booker.setUpdated_at(current_timestamp);
		    				new_booker.setUser_id(user.getAuthentic());
		    				new_booker.setNumber_booking(cb.getNumber_of_seat());
		    				new_booker.setNotification("Booked");
		    				new_booker.setCode(Custom_Imp.getBookingSequence());
		    				session.save(new_booker);
		    				
		    				
		    				current_pass_assign=false;
		    				total_pass_each_sch+=cb.getNumber_of_seat();
		    				user_each_bus.add(new_booker);
		    				System.out.println("current_pass_assign: "+current_pass_assign);
		    				System.out.println("============= New Booker Assign =============");
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
	public List<List<Map<String,Object>>> choose_correct_bus(List<Map<String,Object>> all_bus, Pickup_Location_Master pick_source, Pickup_Location_Master pick_destin, int number_of_passenger, int total_seat_of_all_bus){ 
		System.out.println("choose_correct_bus");
		List<List<Map<String,Object>>> list_bus_choosen =new ArrayList<List<Map<String,Object>>>(); 
		total_bus = all_bus.size();											
  	    number_of_bus=all_bus.size();
  	    // Reset old data to empty
  	    total_choosen_bus_list=new ArrayList<Integer>();
  	    list =new ArrayList<List<Map<String,Object>>>();     
		try {	  
            
		         //=========3. Start The Process of choosing the correct total bus
		         for(int i=1;i<=total_bus;i++){
		             printCombination(all_bus, all_bus.size(), i,number_of_passenger, total_seat_of_all_bus);
		         }
		         //=========4. After choosing the correct total bus
		         Collections.sort(total_choosen_bus_list);							
		         for(int i=0;i<list.size();i++){
		            int sum_each_bus=0;
		            for(int j=0;j<list.get(i).size();j++){
		                sum_each_bus+=Integer.valueOf((String) list.get(i).get(j).get("number_of_seat"));
		            }
		            if(sum_each_bus==total_choosen_bus_list.get(0)){ 
		               list_bus_choosen.add(list.get(i));
		               break;
		            }
		         } 
	
        } catch (RuntimeException e) {
        	e.printStackTrace();
        } 
		System.out.println("List :      "+list.size());
		return list_bus_choosen;
	}
	public List<Booking_Master> get_all_booker(Session session,int from_id, int to_id, String time, String date){  
		System.out.println("get_all_booker");
		List<Booking_Master> all_booker1=new ArrayList<Booking_Master>();    
		try {
            all_booker1 = session.createQuery("from Booking_Master where from_id=? and to_id=? and dept_time=? and dept_date=? order by number_booking desc")
            		.setParameter(0,from_id).setParameter(1, to_id).setTime(2, java.sql.Time.valueOf(time)).setDate(3,java.sql.Date.valueOf(date)).list();

        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		return all_booker1;
	}
	
	public List<Map<String,Object>> get_all_bus(Session session){
		System.out.println("get_all_bus");
		List<Bus_Master> query_all_bus=new ArrayList<Bus_Master>();
		List<Map<String,Object>> all_bus =new ArrayList<Map<String,Object>>();
		try {
            query_all_bus = session.createQuery("from Bus_Master where enabled=? order by number_of_seat asc").setBoolean(0, true).list();
            
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
	
    static void combinationUtil(List<Map<String,Object>> all_bus, List<Map<String,Object>> data, int start,
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
            	if(bus_choosen.size()<number_of_bus){
            		number_of_bus=bus_choosen.size();
            		list.add(bus_choosen);
                	total_choosen_bus_list.add(total_current_bus_seat);
            	} else if(bus_choosen.size()==total_bus){
            		list.add(bus_choosen);
                	total_choosen_bus_list.add(total_current_bus_seat);
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
        	
            combinationUtil(all_bus, data, i+1, end, index+1, r,all_p, all_bus_seat);
        }
    }

    static void printCombination(List<Map<String,Object>> all_bus, int n, int r,int all_p,int all_bus_seat)
    {
        // A temporary array to store all combination one by one
        ///int data[]=new int[r];
    	List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();

        // Print all combination using temprary array 'data[]'
        combinationUtil(all_bus, data, 0, n-1, 0, r,all_p,all_bus_seat);
    }
  //======================== combination for choosing bus till here ============================

	public String customer_request_booking(Customer_Booking cb){
		IdUser user= new IdUser();
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();       
        Booking_Request_Master book = new Booking_Request_Master();
		try {
            trns1 = session.beginTransaction();
            Pickup_Location_Master pick_source=new Pickup_Location_Master();
          	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, cb.getSource()).list().get(0);
          	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
          	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, cb.getDestination()).list().get(0);
            
          	
            book.setCreated_at(current_timestamp);
            book.setUpdated_at(current_timestamp);
            book.setProvided_time(cb.getTime()+":00");
            book.setSource_id(cb.getSource());
            book.setFrom_id(pick_source.getLocation_id());
            book.setDestination_id(cb.getDestination());
            book.setTo_id(pick_destin.getLocation_id());
            book.setDept_date(java.sql.Date.valueOf(cb.getDate()));
            book.setDept_time(java.sql.Time.valueOf(cb.getTime()+":00"));
            book.setNumber_of_booking(cb.getNumber_of_seat());
            book.setUser_id(user.getAuthentic());
            book.setEnabled(true);
            book.setStatus("Pending");
            session.save(book);
            trns1.commit();
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		finally {
            session.flush();
            session.close();
        }
		return "success";
	}
	public String request_book_now(int id){
		Custom_Dao cus=new Custom_Imp();
		Transaction trns1 = null;
		 Customer_Booking cb=new Customer_Booking();
        Session session = HibernateUtil.getSessionFactory().openSession();     
        String book;
		try {
            trns1 = session.beginTransaction();
            Booking_Request_Master br= (Booking_Request_Master) session.createQuery("from Booking_Request_Master where id=?").setParameter(0, id).list().get(0);
           
            cb.setDate(br.getDept_date().toString().subSequence(0, 10).toString());
            cb.setTime(br.getProvided_time());
            cb.setSource(br.getSource_id());
            cb.setDestination(br.getDestination_id());
            cb.setNumber_of_seat(br.getNumber_of_booking());
            
            book=cus.customer_booking(cb);
            if(book.equals("success")){
            	Query query = session.createQuery("update Booking_Request_Master set enabled='false' where id = :id");
            	query.setParameter("id", id);
            	int result = query.executeUpdate();
            	trns1.commit();
            }
            
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
        String book;
		try {
            trns1 = session.beginTransaction();
            Query query = session.createQuery("update Booking_Master set notification='Cancelled'" +
    				" where id = :id");
            query.setParameter("id", id);
            int result = query.executeUpdate();
            trns1.commit();  
            if(result>0){
            	return "success";
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
	}
	
	public static void main(String args[]) throws ParseException{

//		Transaction trns1 = null;
//        Session session = HibernateUtil.getSessionFactory().openSession();   
//        List<Location_Master> locat = new ArrayList<Location_Master>();
//		Map<String,Map<String, List<Pickup_Location_Master>>> pickup=new HashMap<String,Map<String, List<Pickup_Location_Master>>>();
//		Map<String, List<Pickup_Location_Master>> list= new HashMap<String, List<Pickup_Location_Master>>();
//		Pickup_Location_Master pick= new Pickup_Location_Master();
//		try {
//            trns1 = session.beginTransaction();
//            Query query = session.createQuery("update Booking_Master set notification='Cancelled'" +
//    				" where id = :id");
//            query.setParameter("id", 43);
//            int result = query.executeUpdate();
//            trns1.commit(); 
//        } catch (RuntimeException e) {
//        	e.printStackTrace();
//        }finally{
//            session.flush();
//            session.close();
//        }      
		Timestamp current=new Timestamp(System.currentTimeMillis());
		int year=current.getYear();
		int month=current.getMonth()+1;
		int day=current.getDate();
		int h=current.getHours();
		int m=current.getMinutes();
		int s=current.getSeconds();
		System.out.println(year+'-'+month+"-"+day+" "+h+":"+m+":"+s);
	}

}
