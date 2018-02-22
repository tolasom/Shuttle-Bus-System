package com.DaoClasses;

import getInfoLogin.IdUser;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
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

public class Custom_Imp implements Custom_Dao{
	IdUser user=new IdUser();
	static Timestamp current_timestamp = new Timestamp(System.currentTimeMillis());
	Date current_date = new Date();
	public static String TimeNow(){
		Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss");
        String currentDateTimeString = sdf.format(d);
        System.out.println(currentDateTimeString);
        return currentDateTimeString;
	}
	public Map<Object, String> user_info(){
		IdUser user= new IdUser();
		System.out.println(user.getAuthentic());
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
		List<User_Info> users = new ArrayList<User_Info>();
		Map<Object, String> map=new HashMap<Object, String>();
		try {
            trns1 = session.beginTransaction();
            users = session.createQuery("from User_Info where id=? and enabled=?").setParameter(0, user.getAuthentic()).setBoolean(1, true).list();
            System.out.println(users.size());
            if (users.size() > 0) {
            	map.put("username", users.get(0).getUsername());
    		}
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
            	pick = session.createQuery("from Pickup_Location_Master where location_id=? and enabled=?").setParameter(0, locat.get(i).getId()).setBoolean(1, true).list();
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
            	pick = session.createQuery("from Pickup_Location_Master where location_id=? and enabled=?").setParameter(0, locat.get(i).getId()).setBoolean(1, true).list();
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
	public List<Map<Object, String>> departure_time_info(){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();      
		List<Dept_Time_Master> dept = new ArrayList<Dept_Time_Master>();	
		List<Map<Object, String>> list =new ArrayList<Map<Object, String>>();
		try {
            trns1 = session.beginTransaction();
            dept = session.createQuery("from Dept_Time_Master where enabled=?").setBoolean(0, true).list();
            System.out.println("KK");
            for(int i=0; i<dept.size();i++){
            	Map<Object, String> map=new HashMap<Object, String>();
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
	public List<Map<Object, String>> cusomer_booking_history(){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();      
		List<Booking_Master> bh = new ArrayList<Booking_Master>();	
		List<Map<Object, String>> list =new ArrayList<Map<Object, String>>();
		try {
            trns1 = session.beginTransaction();
            bh = session.createQuery("from Booking_Master where user_id=? order by created_at asc").setParameter(0, user.getAuthentic()).setMaxResults(10).list();
            System.out.println("KK");
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
            	User_Info driver=new User_Info();
            	driver = (User_Info) session.createQuery("from User_Info where id=?").setParameter(0, sch_ma.getDriver_id()).list().get(0);
            	Map<Object, String> map=new HashMap<Object, String>();
            	map.put("dept_date", bh.get(i).getDept_date().toString());
            	map.put("dept_time", bh.get(i).getDept_time().toString());
            	map.put("scource", source.getName());
            	map.put("destination", destin.getName());
            	map.put("number_of_ticket", String.valueOf(bh.get(i).getNumber_booking()));
            	map.put("bus_model", bus.getModel());
            	map.put("plate_number", bus.getPlate_number());
            	map.put("diver_name", driver.getName());
            	map.put("diver_phone_number", driver.getPhone_number());
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
	public List<Map<Object, String>> get_request_booking(){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();      
		List<Booking_Request_Master> bh = new ArrayList<Booking_Request_Master>();	
		List<Map<Object, String>> list =new ArrayList<Map<Object, String>>();
		try {
            trns1 = session.beginTransaction();
            bh = session.createQuery("from Booking_Request_Master where user_id=? and dept_date>=? and dept_time>? order by created_at asc")
            		.setParameter(0, user.getAuthentic()).setDate(1, new Date()).setTime(2, java.sql.Time.valueOf(TimeNow())).list();
            System.out.println("KK");
            for(int i=0; i<bh.size();i++){
            	Pickup_Location_Master pick_source=new Pickup_Location_Master();
            	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getSource_id()).list().get(0);
            	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
            	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, bh.get(i).getDestination_id()).list().get(0);
            	
            	Location_Master source=new Location_Master();
            	source = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getFrom_id()).list().get(0);
            	Location_Master destin=new Location_Master();
            	destin = (Location_Master) session.createQuery("from Location_Master where id=?").setParameter(0, bh.get(i).getTo_id()).list().get(0);
            	Map<Object, String> map=new HashMap<Object, String>();
            	map.put("id", String.valueOf(bh.get(i).getId()));
            	map.put("dept_date", bh.get(i).getDept_date().toString());
            	map.put("dept_time", bh.get(i).getDept_time().toString());
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
		static List<List<Map<Object, String>>> list =new ArrayList<List<Map<Object, String>>>();
		static List<Integer> total_choosen_bus_list=new ArrayList<Integer>();
		static int number_of_bus=0;
		static int total_bus=0;
		
		
	public String customer_booking(Customer_Booking cb){	
		// Reset old data to empty
		list =new ArrayList<List<Map<Object, String>>>();
		total_choosen_bus_list=new ArrayList<Integer>();
		number_of_bus=0;    // temporary use (value will always change)
		total_bus=0;		// permanent use (value will never change)
		
		int total_seat_of_all_bus=0;
		int number_of_available_bus=0;
		int number_of_passenger=0;
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession(); 
        List<Booking_Master> all_booker1=new ArrayList<Booking_Master>();
        //List<Booking_Master> all_booker2=new ArrayList<Booking_Master>();
        List<Schedule_Master> sch=new ArrayList<Schedule_Master>();
        List<Bus_Master> query_all_bus=new ArrayList<Bus_Master>();
        Booking_Master bm=new Booking_Master();
        List<Map<Object,String>> list_map_all_bus=new ArrayList<Map<Object,String>>();
        List<List<Map<Object, String>>> list_bus_choosen =new ArrayList<List<Map<Object, String>>>(); //Final Bus Chosen
        
		try {
            trns1 = session.beginTransaction();
            
            Pickup_Location_Master pick_source=new Pickup_Location_Master();
          	pick_source = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, cb.getSource()).list().get(0);
          	Pickup_Location_Master pick_destin=new Pickup_Location_Master();
          	pick_destin = (Pickup_Location_Master) session.createQuery("from Pickup_Location_Master where id=?").setParameter(0, cb.getDestination()).list().get(0);
            System.out.println("========================= 1 =============================");
            
            //========1. query list of all available bus (result must be in order number of seat for small to big )===========
            query_all_bus = session.createQuery("from Bus_Master where enabled=? order by number_of_seat asc").setBoolean(0, true).list();
            
            if(query_all_bus.size()>0){       	  
        	  total_bus = query_all_bus.size();											//1&2
        	  number_of_bus=query_all_bus.size();										//3
        	  List<Map<Object, String>> all_bus =new ArrayList<Map<Object, String>>();              
              for(int i=0;i<query_all_bus.size();i++){
            	  total_seat_of_all_bus+=query_all_bus.get(i).getNumber_of_seat();		//4 
                  Map<Object, String> map =new HashMap<Object, String>();				//5
                  map.put("bus_model", query_all_bus.get(i).getModel());
                  map.put("number_of_seat", String.valueOf(query_all_bus.get(i).getNumber_of_seat()));
                  map.put("id", String.valueOf(query_all_bus.get(i).getId()));
                  all_bus.add(map);
            } 
              
             System.out.println("========================= 2 =============================");
             
             
             
             //=========2. Find out number of total passengers in DB ============ 
              all_booker1 = session.createQuery("from Booking_Master where from_id=? and to_id=? and dept_time=? and dept_date=? order by number_booking desc")
                		.setParameter(0,pick_source.getLocation_id()).setParameter(1, pick_destin.getLocation_id()).setTime(2, java.sql.Time.valueOf(cb.getTime())).setDate(3,java.sql.Date.valueOf(cb.getDate())).list();
              System.out.println("all_booker1 size: "+all_booker1.size());
              for(int p=0;p<all_booker1.size();p++){
            	  number_of_passenger+=all_booker1.get(p).getNumber_booking();
              }
              System.out.println(number_of_passenger);
              number_of_passenger+=cb.getNumber_of_seat();
              
              
              
              System.out.println("========================= 3 =============================");
              
              
              
              //=========3. Start The Process of choosing the correct total bus
              for(int i=1;i<=total_bus;i++){
                  printCombination(all_bus, all_bus.size(), i,number_of_passenger, total_seat_of_all_bus);
              }
              
              System.out.println("========================= 4 =============================");
              
              
              
              //=========4. After choosing the correct total bus &&
              Collections.sort(total_choosen_bus_list);							
              
              for(int i=0;i<list.size();i++){
              	int sum_each_bus=0;
              	for(int j=0;j<list.get(i).size();j++){
              		///sum_each_bus+=list.get(i).get(j);
              		sum_each_bus+=Integer.valueOf(list.get(i).get(j).get("number_of_seat"));
              	}

              	if(sum_each_bus==total_choosen_bus_list.get(0)){ 
              		list_bus_choosen.add(list.get(i));
              		break;
              	}
              } 
              
          }else{
        	  System.out.println("No Bus available!!!");
        	  return "no_bus";
          }
          System.out.println(list_bus_choosen);
          
          
          System.out.println("========================= 5 =============================");
          
          
          
          
          if(list_bus_choosen.size()>0){       
        	  
        	 //============== 5. Delete old Schedule =================
        	  //call function to delete schedule
        	  int delete=delete_Schedule(pick_source.getLocation_id(),pick_destin.getLocation_id(),cb.getTime(), cb.getDate());
        	  
        	  System.out.println("========================= 6 =============================");
        	  //============== 6. Set/Reset New Schedule =================
        	  int ib=0; //index of passenger
        	  //user that remove out when number_booing+one more booker seat>each bus seat while assign bus
        	  List<Booking_Master> pass_remove_list=new ArrayList<Booking_Master>();
        	  //the user that already assign Sch_Id & QR-code must be remove
        	  int number_remove_pass=all_booker1.size();
        	  List<List<Integer>> check_seat_each_bus=new ArrayList<List<Integer>>();
        	  List<Integer> list_of_index_remove=new ArrayList<Integer>();
        	  
        	  	  
	          for(int i=0;i<list_bus_choosen.get(0).size();i++){    	  
	        	  	System.out.println("pppppp: "+list_bus_choosen.get(0).get(i).get("number_of_seat"));
	        		int number_booking=0;
	        		int remaining_seat=Integer.valueOf(list_bus_choosen.get(0).get(i).get("number_of_seat"));
	        		int number_customer=0;
	        		Schedule_Master sm=new Schedule_Master();
	        		sm.setBus_id(Integer.valueOf(list_bus_choosen.get(0).get(i).get("id")));
	        		sm.setDestination_id(cb.getDestination());
	        		sm.setSource_id(cb.getSource());
	        		sm.setFrom_id(pick_source.getLocation_id());
	        		sm.setTo_id(pick_destin.getLocation_id());
	        		sm.setDept_date(java.sql.Date.valueOf(cb.getDate()));
	        		sm.setDept_time(java.sql.Time.valueOf(cb.getTime()));
	        		sm.setCreated_at(current_timestamp);
	        		sm.setUpdated_at(current_timestamp);
	        		session.save(sm);
	        		
	        		
	        		//============== 7. Insert & Update Passenger Booking to DB =================
	        		//Query data of user to check number of seat
        			Booking_Master user_booking=new Booking_Master();
	        		if(pass_remove_list.size()!=0){
	        			System.out.println("pass_remove_list>0");
	        			for(int j=0;j<pass_remove_list.size();j++){
	        				user_booking = (Booking_Master) session.createQuery("from Booking_Master where id =?").setParameter(0, pass_remove_list.get(j).getId()).list().get(0);
	        				System.out.println("user_booking: "+user_booking.getId());
	        				if(user_booking.getNumber_booking()+number_booking<=Integer.valueOf(list_bus_choosen.get(0).get(i).get("number_of_seat"))){
		        				Query query = session.createQuery("update Booking_Master set schedule_id =?, qr=?" +
			            				" where id =?");
						        query.setParameter(0, sm.getId());
						        query.setParameter(1, cb.getSource()+cb.getDestination()+cb.getDate()+cb.getTime()+user_booking.getId());  //QR-Code(pickup_source+pickup_destination+date+time+user_id Generation
						        query.setParameter(2, user_booking.getId()); 
						        int res = query.executeUpdate();
						        number_booking+=user_booking.getNumber_booking();
						        remaining_seat-=user_booking.getNumber_booking();
						        number_customer+=user_booking.getNumber_booking();
						        list_of_index_remove.add(j);
		        			}
	        			}
	        			//remove the list of passenger who already assign sch & QR
	        			for(int m=0;m<list_of_index_remove.size();m++){
	        				pass_remove_list.remove(list_of_index_remove.get(m));
	        			}
        			}
	        		
	        		
	        		//Update schedule and QR-code of user that have already booking
	        		//Wrong//for(int k=0;k<list_bus_choosen.get(i).size()&&ib<number_of_passenger&&all_booker1.size()>0;k++){        		
	        		//Condition 
	        		//1. Must be have people booking
	        		//2. k can not over bus_seat of each bus... mean one booking at least book one seat so can not passenger cannot more than one bus seat
	        		//3. number_booking(each_bus) <= bus_choosen.get(i).getNumber_booking();
	        		
	        		System.out.println("Old passenger size: "+all_booker1.size());
	        		System.out.println("Num seat each bus: "+list_bus_choosen.get(0).get(i).get("number_of_seat"));
	        		for(int k=0;all_booker1.size()>0&&k<Integer.valueOf(list_bus_choosen.get(0).get(i).get("number_of_seat"))&&number_remove_pass>0&&number_booking<Integer.valueOf(list_bus_choosen.get(0).get(i).get("number_of_seat"));k++){			        				
	        			System.out.println("IB: "+ib);
	        			System.out.println("Sch Id : "+sm.getId());
	        			user_booking = (Booking_Master) session.createQuery("from Booking_Master where id =?").setParameter(0, all_booker1.get(ib).getId()).list().get(0);
	        			System.out.println("user_booking: "+all_booker1.get(ib).getId());
	        			if(user_booking.getNumber_booking()+number_booking<=Integer.valueOf(list_bus_choosen.get(0).get(i).get("number_of_seat"))){
		        				Query qu = session.createQuery("update Booking_Master set schedule_id =?, qr=?" +
			            				" where id =?");
						        qu.setParameter(0, sm.getId());
						        qu.setParameter(1, cb.getSource()+cb.getDestination()+cb.getDate()+cb.getTime()+user_booking.getId());  //QR-Code Generation
						        qu.setParameter(2, all_booker1.get(ib).getId()); 
						        System.out.println("A: ");
						        int res = qu.executeUpdate();
						        System.out.println("B: ");
						        number_booking+=user_booking.getNumber_booking();
						        remaining_seat-=user_booking.getNumber_booking();
						        number_customer+=user_booking.getNumber_booking(); 
		        		}else{
		        			pass_remove_list.add(all_booker1.get(i));
		        		}
	        			number_remove_pass--;
					    ib++;
	        		}
	        		System.out.println("Finish ");
	        	  // create record of user that are booking
	        		if(cb.getNumber_of_seat()+number_booking<=Integer.valueOf(list_bus_choosen.get(0).get(i).get("number_of_seat"))){
	        			Booking_Master pass=new Booking_Master();
	  	          	  	pass.setSource_id(cb.getSource());
	  	          	  	pass.setDestination_id(cb.getDestination());
	  	          	  	pass.setFrom_id(pick_source.getLocation_id());
	  	          	  	pass.setTo_id(pick_destin.getLocation_id());
	  	          	  	pass.setDept_date(java.sql.Date.valueOf(cb.getDate()));
	  	          	  	pass.setDept_time(java.sql.Time.valueOf(cb.getTime()));
	  	          	  	pass.setNumber_booking(cb.getNumber_of_seat());
	  	          	  	pass.setUser_id(user.getAuthentic());
	  	          	  	pass.setCreated_at(current_timestamp);
	  	          	  	pass.setUpdated_at(current_timestamp);
	  	          	  	pass.setSchedule_id(sm.getId());
	  	          	  	pass.setQr(cb.getSource()+cb.getDestination()+cb.getDate()+cb.getTime()+user.getAuthentic());	          	  
	  	          	  	session.save(pass); 
	  	          	  	number_booking+=cb.getNumber_of_seat();
	  	          	  	remaining_seat-=cb.getNumber_of_seat();
	  	          	  	number_customer+=cb.getNumber_of_seat();
	        		}else{
	        			System.out.println("Can not book");
	        		}
	        	  sm.setNumber_booking(number_booking);
	        	  sm.setNumber_customer(number_customer);
	        	  sm.setRemaining_seat(remaining_seat);
	        	  
	           } 
          }
        System.out.println("  PP 7");
        trns1.commit();
        System.out.println("  PP 5");
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	trns1.rollback();
        }finally {
            session.flush();
            session.close();
        }           
		return "a";
	}
	
	static int delete_Schedule(int from_id,int to_id,String time, String date){

		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();       
		try {
            trns1 = session.beginTransaction();
            Query q = session.createQuery("delete Schedule_Master where from_id=? and to_id=? and dept_time=? and dept_date=?");
        	q.setParameter(0, from_id);
        	q.setParameter(1, to_id);
        	q.setTime(2, java.sql.Time.valueOf(time));
        	q.setDate(3, java.sql.Date.valueOf(date));
        	int result = q.executeUpdate();
            trns1.commit();
            return result;
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	return 0;
        }    
		finally {
            session.flush();
            session.close();
        }
	}
	
    static void combinationUtil(List<Map<Object, String>> all_bus, List<Map<Object, String>> data, int start,
                                int end, int index, int r,int all_p,int all_bus_seat)
    {
        if (index == r)
        {
        	List<Map<Object, String>> bus_choosen=new ArrayList<Map<Object, String>>();
            int total_current_bus_seat=0;
            for (int j=0; j<r; j++){
               /// total_current_bus_seat+=data[j];
               /// bus_choosen.add(data[j]);
            	
            	total_current_bus_seat+=Integer.valueOf(data.get(j).get("number_of_seat"));
            	Map<Object, String> map=new HashMap<Object,String>();
//            	map.put("Bus_Seat", data.get(j).get("Bus_Seat"));
//            	map.put("Bus_Model", data.get(j).get("Bus_Model"));
            	map.put("bus_model", data.get(j).get("bus_model"));
                map.put("number_of_seat", data.get(j).get("number_of_seat"));
                map.put("id", data.get(j).get("id"));
            	bus_choosen.add(map);
            	System.out.print(data.get(j).get("number_of_seat")+"  ");
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
            }
            return;
        }

        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            ///data[index] = Integer.valueOf(all_bus.get(i).get("Bus_Seat"));
        	
        	Map<Object, String> map=new HashMap<Object,String>();
//        	map.put("Bus_Seat", all_bus.get(i).get("Bus_Seat"));
//        	map.put("Bus_Model", all_bus.get(i).get("Bus_Model"));
        	 map.put("bus_model", all_bus.get(i).get("bus_model"));
             map.put("number_of_seat", all_bus.get(i).get("number_of_seat"));
             map.put("id", all_bus.get(i).get("id"));
        	data.add(index,map);
        	
            combinationUtil(all_bus, data, i+1, end, index+1, r,all_p, all_bus_seat);
        }
    }

    static void printCombination(List<Map<Object, String>> all_bus, int n, int r,int all_p,int all_bus_seat)
    {
        // A temporary array to store all combination one by one
        ///int data[]=new int[r];
    	List<Map<Object, String>> data=new ArrayList<Map<Object, String>>();

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
            book.setCreated_at(current_timestamp);
            book.setUpdated_at(current_timestamp);
            book.setSource_id(cb.getSource());
            book.setDestination_id(cb.getDestination());
            book.setDept_date(java.sql.Date.valueOf(cb.getDate()));
            book.setDept_time(java.sql.Time.valueOf(cb.getTime()+":00"));
            book.setNumber_of_booking(cb.getNumber_of_seat());
            book.setUser_id(user.getAuthentic());
            book.setStatus("pending");
            session.save(book);
            trns1.commit();
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		finally {
            session.flush();
            session.close();
        }
		return "a";
	}
	
	
	public static void main(String args[]){
		GregorianCalendar gcalendar = new GregorianCalendar();
		System.out.print("Time: ");
	      System.out.print(gcalendar.get(Calendar.HOUR) + ":");
	      System.out.print(gcalendar.get(Calendar.MINUTE) + ":");
	      System.out.println(gcalendar.get(Calendar.SECOND));

//		Transaction trns1 = null;
//        Session session = HibernateUtil.getSessionFactory().openSession();       
//		try {
//            trns1 = session.beginTransaction();
//        } catch (RuntimeException e) {
//        	e.printStackTrace();
//        }finally{
//            session.flush();
//            session.close();
//        }              
	}
}
