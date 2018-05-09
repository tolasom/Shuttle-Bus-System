package com.DaoClasses;

import getInfoLogin.IdUser;

import java.sql.Array;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.EntityClasses.Bus_Master;
import com.EntityClasses.Location_Master;
import com.EntityClasses.Pickup_Location_Master;
import com.EntityClasses.Schedule_Master;
import com.HibernateUtil.HibernateUtil;
import com.ModelClasses.Customer_Booking;

public class Student_Imp implements Student_Dao{
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
	
	public String TomorrowDateTime(){
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tmrDateTimeString = sdf.format(dt);
        System.out.println(tmrDateTimeString);
		return tmrDateTimeString;
	}
	public static String getScheduleSequence(){ 
  	  List<Schedule_Master> schedules = new ArrayList<Schedule_Master>(); 
  	  schedules = new Student_Imp().getAllSchedules(); 
  	  int code; 
  	  String scode= "000001"; 
  	  for(Schedule_Master s : schedules) 
  	   System.out.println(s.getId()); 
  	  if(schedules.size()>0){ 
  	   code = 1000000+(int)schedules.size()+1; 
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
  	  bookings = new Student_Imp().getAllBookings(); 
  	  int code; 
  	  String scode= "000001"; 
  	  for(Booking_Master s : bookings) 
  	   System.out.println(s.getId()); 
  	  if(bookings.size()>0){ 
  	   code = 1000000+(int)bookings.size()+1; 
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
  	            String queryString = "from Booking_Maste"; 
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
	//======================== combination for choosing bus till ============================
	List<List<Map<String,Object>>> list =new ArrayList<List<Map<String,Object>>>();
	List<Integer> total_choosen_bus_list=new ArrayList<Integer>();
	int number_of_bus=0;		// temporary use (value will always change)
	int total_bus=0;			// permanent use (value will never change)
	List<List<Map<String,Object>>> list_bus_choosen =new ArrayList<List<Map<String,Object>>>();	
	public void createSchedule() throws ParseException{
		System.out.println("  createSchedule() ");
		Student_Dao stu=new Student_Imp();
		Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession(); 
        try {
        	trns = session.beginTransaction();
        	Student_Imp s=new Student_Imp();
    		String tmr_dt=s.TomorrowDateTime();
    		String tmr_date=tmr_dt.split(" ")[0];
    		String tmr_time=tmr_dt.split(" ")[1];
//        	>  Query time
    		List<Time> list_time=stu.getAvailableTime(tmr_dt, session);
//        	>  Query from, Query to -----> permutation of from and to --> list of round
    		System.out.println(list_time);
    		List<int[]> list_round= stu.getListRound(session);  
    		System.out.println(list_round);
//        	-----> Loop time and round ---> Assign with schedule function base on list>0
    		for(int i=0;i<list_time.size();i++){
    			for(int j=0; j<list_round.size();j++){
    				List<Booking_Master> booked=session.createQuery("from Booking_Master where description='student' and dept_date=:date and dept_time=:time and to_id=:to and from_id=:from")
    						.setDate("date",java.sql.Date.valueOf(tmr_date))
    						.setTime("time", java.sql.Time.valueOf(list_time.get(i).toString()))
    						.setParameter("to", list_round.get(j)[0])
    						.setParameter("from", list_round.get(j)[1]).list();
    				
    				if(booked.size()>0){
    					System.out.println(booked.get(0).getDept_time());
        				System.out.println(booked.get(0).getDept_date());
        				System.out.println(booked.get(0).getSchedule_id());
        				System.out.println();
    					Customer_Booking cb=new Customer_Booking();
        				cb.setDate(tmr_date);
        				cb.setTime(list_time.get(i).toString());
        				cb.setNumber_of_seat(1);
        				cb.setSource(list_round.get(j)[0]);
        				cb.setDestination(list_round.get(j)[1]);
        				Student_Imp booking=stu.student_schedule(session, cb);
        				System.out.println("PPLLLL");
        				System.out.println(booking.total_bus);
    				}
    				
    			}
    		}
        	//trns.commit();
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	trns.rollback();
        }finally {
            session.flush();
            session.close();
        } 
	}
	
	public List <Schedule_Master> get_schedules(Session session,Customer_Booking cb){ 
	  	  List <Schedule_Master> schedules  = new ArrayList<Schedule_Master>(); 

	  	        try { 
	  	        	schedules=session.createQuery("from Schedule_Master where dept_date=:date and dept_time=:time and to_id=:to and from_id=:from")
							.setDate("date",java.sql.Date.valueOf(cb.getDate()))
							.setTime("time", java.sql.Time.valueOf(cb.getTime()))
							.setParameter("to", cb.getDestination())
							.setParameter("from", cb.getSource()).list();
	  	        } catch (RuntimeException e) { 
	  	            e.printStackTrace(); 
	  	            return schedules; 
	  	        } 
	  	        return schedules; 
	  	   
	  	 }
	public List<Time> getAvailableTime(String tmr_dt, Session session){
		List<Time> list_time=new ArrayList<Time>();   
        try {
        	Student_Imp s=new Student_Imp();
    		String tmr_date=tmr_dt.split(" ")[0];
    		String tmr_time=tmr_dt.split(" ")[1];
    		List<Time> list_time1=new ArrayList<Time>();   
    		List<Time> list_time2=new ArrayList<Time>();   
    		list_time1 = session.createQuery("select dept_time from Booking_Master where dept_date=? "
        			+ "and description='student' and schedule_id='0' group by dept_date, "
        			+ "dept_time, from_id, to_id ").setDate(0, java.sql.Date.valueOf(s.DateNow())).list();
    		
        	list_time2 = session.createQuery("select dept_time from Booking_Master where dept_date=? "
        			+ "and description='student' and schedule_id='0' group by dept_date, "
        			+ "dept_time, from_id, to_id ").setDate(0, java.sql.Date.valueOf(tmr_date)).list();
            for(int i=0;i<list_time1.size();i++){
            	Time time=list_time1.get(i);
            	System.out.println(time.after(java.sql.Time.valueOf(tmr_time)));
            	if(time.after(java.sql.Time.valueOf(tmr_time))){
            		list_time.add(time);
            	}
            }
            for(int i=0;i<list_time2.size();i++){
            	Time time=list_time2.get(i);
            	System.out.println(time.before(java.sql.Time.valueOf(tmr_time)));
            	if(time.before(java.sql.Time.valueOf(tmr_time))){
            		list_time.add(time);
            	}
            }
            
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }  
		return list_time;
		
	}
	public List<int[]> getListRound(Session session){
		List<int[]> list_round=new ArrayList<int[]>();  
        try {
        	List<Location_Master> locat = session.createQuery("from Location_Master where enabled=?").setBoolean(0, true).list();
        	for(int i=0;i<locat.size();i++){
        		for(int j=0;j<locat.size();j++){
        			if(locat.get(i).getId()!=locat.get(j).getId()){
        				int[] li={locat.get(i).getId(),locat.get(j).getId()};
        				list_round.add(li);
        			}
        		}
        	}
            
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }  
		return list_round;
		
	}
	// Get all tomorrow schedule of student from Booking Master group by date, time, source and destination
	public void getAllTomorrowSchedule(Session session, String tmr_dt){		
		String tmr_date=tmr_dt.split(" ")[0];
		List<Booking_Master> all_booker1=new ArrayList<Booking_Master>();    
		try {
            all_booker1 = session.createQuery("from Booking_Master where dept_date=? group by dept_time, from, to").setDate("dept_date", java.sql.Date.valueOf(tmr_date)).list();

        } catch (RuntimeException e) {
        	e.printStackTrace();
        }    
		return;
	}
	
	public Student_Imp student_schedule(Session session, Customer_Booking cb) throws ParseException{	
		Student_Dao stu=new Student_Imp();
		Student_Imp booking=new Student_Imp();
        booking.list=new ArrayList<List<Map<String,Object>>>();
        booking.total_choosen_bus_list=new ArrayList<Integer>();
        booking.list_bus_choosen=new ArrayList<List<Map<String,Object>>>();
        booking.number_of_bus=0;
        booking.total_bus=0; 
        List<Schedule_Master> schedule=new ArrayList<Schedule_Master>();
        List<Booking_Master> list_passenger=new ArrayList<Booking_Master>();
        List<Booking_Master> list_stu=new ArrayList<Booking_Master>();
        List<Booking_Master> list_customer=new ArrayList<Booking_Master>();
        
        
		int total_seat_of_all_bus=0;
	        int passenger_seat=0;
	        List<Map<String,Object>> all_bus =new ArrayList<Map<String,Object>>();  
	        
	        Student_Dao student_Imp=new Student_Imp();
	        Student_Imp c=new Student_Imp();
	        
			try {
				
				list_passenger=stu.get_all_booker(session, cb.getSource(), cb.getDestination()
						, cb.getTime(), cb.getDate());
	          	for(int i=0;i<list_passenger.size();i++){
	          		passenger_seat+=list_passenger.get(i).getNumber_booking();
	          		if(list_passenger.get(i).getDescription().equals("customer")){
	          			list_customer.add(list_passenger.get(i));
	          		}
	          		else{
	          			list_stu.add(list_passenger.get(i));
	          		}
	          	}
				
	          	schedule=stu.get_schedules(session, cb);
	          	int existing_bus_seat=0;
	          	for(int i=0;i<schedule.size();i++){
	          		existing_bus_seat+=schedule.get(i).getNumber_booking()+schedule.get(i).getRemaining_seat();
	          	}
	          	
	          	//True----> Assign to existing bus/schedule
	          	if(passenger_seat<=existing_bus_seat){
	          		//Assign student with existing schedule
	          		stu.asign_to_existing_schedule(session, list_stu, schedule);
	          	}//Else re-create schedule
	          	else{
	          	//query list of all available bus (result must be in order number of seat for small to big )
	                all_bus= student_Imp.get_all_bus(session,cb,cb.getSource(),cb.getDestination());
    	        
	    	        //if have bus or have no bus
	                if(all_bus.size()>0){
	                	//5. Fins total seat of all bus
	                	for(int i=0;i<all_bus.size();i++){
	       	             	total_seat_of_all_bus+=Integer.valueOf((String) all_bus.get(i).get("number_of_seat")); 
	                	}
	                	//check whether people is over all available bus seat or not
	                	if(passenger_seat<=total_seat_of_all_bus){
	                		//6. create new schedule
	                    	Map<Object, List<Booking_Master>> sch_with_users=student_Imp.create_schedule(booking,session
	                    			,all_bus,list_passenger,total_seat_of_all_bus,passenger_seat); 			// 6. Set/Reset New Schedule 
	                    	System.out.println("PPPPPPPPPPPPPPPPPPPPP");
	                    	System.out.println(sch_with_users);
//	                    	if(sch_with_users.size()==0){
//	                    		return "over_bus_available";
//	                    	}else{
//	                    		//7.Delete Schedule
//	                        	int delete=delete_Schedule(session,cb.getSource(),cb.getDestination(),cb.getTime(), cb.getDate());	
//	                        	for(int h=0;h<sch_with_users.size();h++){
//	                        		int num_booking=0;
//	                        		int num_customer=0;
//                            		int number_stu=0;
//	                        		Schedule_Master sch=new Schedule_Master();
//	                        		sch.setBus_id(Integer.valueOf((String) booking.list_bus_choosen.get(0).get(h).get("id")));
//	                        		sch.setSource_id(cb.getSource());
//	                        		sch.setDestination_id(cb.getDestination());
//	                        		sch.setFrom_id(cb.getSource());
//	                        		sch.setTo_id(cb.getDestination());
//	                        		sch.setDept_date(java.sql.Date.valueOf(cb.getDate()));
//	                        		sch.setDept_time(java.sql.Time.valueOf(cb.getTime()));
//	                        		sch.setCreated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
//	                        		sch.setUpdated_at(java.sql.Timestamp.valueOf(c.DateTimeNow()));
//	                        		sch.setCode(Student_Imp.getScheduleSequence());
//	                        		session.save(sch);
//	                        		for(int y=0;y<sch_with_users.get(h).size();y++){
//	                        			num_booking+=sch_with_users.get(h).get(y).getNumber_booking();
//	                        			if(sch_with_users.get(h).get(y).getDescription().equals("customer")){
//											num_customer+=sch_with_users.get(h).get(y).getNumber_booking();
//										}else       //student
//											{
//											number_stu+=sch_with_users.get(h).get(y).getNumber_booking();
//										}
//	                        			Query query = session.createQuery("update Booking_Master set schedule_id = :sch_id, qr= :qr" +
//	                            				" where id = :id");
//	    			                    query.setParameter("sch_id", sch.getId());
//	    			                    query.setParameter("qr", cb.getSource()+""+cb.getDate()+""+cb.getDate()+""+cb.getTime()+""+sch_with_users.get(h).get(y).getId());
//	    			                    query.setParameter("id", sch_with_users.get(h).get(y).getId());
//	    			                    int result = query.executeUpdate();
//	                    			}
//	                        		
//	                        		sch.setNumber_booking(num_booking);
//	                        		sch.setRemaining_seat(Integer.valueOf((String) booking.list_bus_choosen.get(0).get(h).get("number_of_seat"))-num_booking);
//	                        		sch.setNumber_customer(num_customer);
//	                        		sch.setNumber_staff(0);
//	                        		sch.setNumber_student(number_stu);
//	                    			for(int y=0;y<sch_with_users.get(h).size();y++){
//	                    				System.out.print(sch_with_users.get(h).get(y).getId()+" ");
//	                    			}
//	                    			System.out.println(" ");
//	                    			for(int y=0;y<sch_with_users.get(h).size();y++){
//	                    				System.out.print(sch_with_users.get(h).get(y).getNumber_booking()+" ");
//	                    			}
//	                    			System.out.println(" ");
//	                    		}
//	                    	}
	                	}
	                	
	                }else{
	    	      	  System.out.println("No Bus available!!!");
	    	      	  //return "no_bus_available";
	    	        }
	          	}
	          		
	        } catch (RuntimeException e) {
	        	e.printStackTrace();
	        	//return "error";
	        }           
			//return "success";
			return booking;
		}
		public void asign_to_existing_schedule(Session session,List<Booking_Master> list_stu,List<Schedule_Master> list_schedule){
			int index=0;
			try{
				outerloop:
				for(int i=0;i<list_schedule.size();i++){
						for(int j=0;j<list_schedule.get(i).getRemaining_seat();j++){
							if(index<=list_stu.size()){
								Query query = session.createQuery("update Booking_Master set schedule_id = :sch_id, qr= :qr" +
				        				" where id = :id");
				                query.setParameter("sch_id", list_schedule.get(i).getId());
				                query.setParameter("qr", list_stu.get(index).getFrom_id()+""+list_stu.get(index).getTo_id()+""+list_stu.get(index).getDept_date()+""+list_stu.get(index).getDept_time()+""+list_stu.get(index).getId());
				                query.setParameter("id", list_stu.get(index).getId());
				                int result = query.executeUpdate();
				                index++;
							}
							else{
								break outerloop;
							}
						}
				}
			} catch (RuntimeException e) {
	        	e.printStackTrace();
	        }
		}
		public Map<Object, List<Booking_Master>> create_schedule(Student_Imp booking,Session session, List<Map<String,Object>> all_bus
				, List<Booking_Master> all_booker1, int total_seat_of_all_bus,int number_of_passenger){	
			Boolean recursive=true;
			List<Booking_Master> user_sch_assign=new ArrayList<Booking_Master>();
	        Map<Object,List<Booking_Master>> sch_with_users=new HashMap<Object,List<Booking_Master>>();
	        Student_Dao student_Imp=new Student_Imp();  
	        Student_Imp c=new Student_Imp();
	        
	        //Recursive to choose correct bus
			while(recursive){
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
			            System.out.println(all_booker1.get(p).getNumber_booking());
			        }
		            
					//Final Bus Chosen the correct bus because people always accept even 1
		            booking.list_bus_choosen=student_Imp.choose_correct_bus(booking,all_bus,total_seat_of_bus_chosen,total_seat_of_all_bus);   //total_seat_of_bus_chosen==number_of_passenger
		            
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
			        		sch_with_users.put(i, user_each_bus);
			        	}

				        if(tem_pass_assign.size()==all_booker1.size()&&(!current_pass_assign)){	
			            	break;
			    		}else if((tem_pass_assign.size()<all_booker1.size()||current_pass_assign)&&last_bus_choosing){
			    			number_of_passenger=next_total_seat_of_bus_chosen+1;
			    			if(total_seat_of_all_bus==next_total_seat_of_bus_chosen){
			    				last_bus_choosing=false;
			    			}
			    		}else{
			    			sch_with_users=new HashMap<Object,List<Booking_Master>>();
			    			break;
			    		}
		            }else{
		    			sch_with_users=new HashMap<Object,List<Booking_Master>>();
		    			break;
		            }
			        
		        } catch (RuntimeException e) {
		        	e.printStackTrace();
		        }  
			}
			  
			return sch_with_users;
		}
		public List<List<Map<String,Object>>> choose_correct_bus(Student_Imp booking
				,List<Map<String,Object>> all_bus, int number_of_passenger, int total_seat_of_all_bus){ 
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
	            all_booker1 = session.createQuery("from Booking_Master where from_id=? and to_id=? and dept_time=? and dept_date=? order by number_booking desc")
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
			Student_Dao student_Imp=new Student_Imp();
			try {
				same_date_route=student_Imp.same_date_same_route(session, cb, from, to);
				List<Integer> unava1= (List<Integer>) same_date_route.get(0).get("unavailable_bus");
				same_date_diff_route=student_Imp.same_date_differ_route(session, cb, from, to);
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
		
	    static void combinationUtil(Student_Imp booking,List<Map<String,Object>> all_bus, List<Map<String,Object>> data, int start,
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
//	        	map.put("Bus_Seat", all_bus.get(i).get("Bus_Seat"));
//	        	map.put("Bus_Model", all_bus.get(i).get("Bus_Model"));
	        	 map.put("bus_model", all_bus.get(i).get("bus_model"));
	             map.put("number_of_seat", all_bus.get(i).get("number_of_seat"));
	             map.put("id", all_bus.get(i).get("id"));
	        	data.add(index,map);
	        	
	            combinationUtil(booking,all_bus, data, i+1, end, index+1, r,all_p, all_bus_seat);
	        }
	    }

	    static void printCombination(Student_Imp booking,List<Map<String,Object>> all_bus, int n, int r,int all_p,int all_bus_seat)
	    {
	        // A temporary array to store all combination one by one
	        ///int data[]=new int[r];
	    	List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();

	        // Print all combination using temprary array 'data[]'
	        combinationUtil(booking,all_bus, data, 0, n-1, 0, r,all_p,all_bus_seat);
	    }
	  //======================== combination for choosing bus till here ============================
	    
	    public static void main(String[] args) throws ParseException {
	    	Student_Imp c =new Student_Imp();
	    	System.out.println("KK");
	        c.createSchedule();
	        System.out.println("1");
//	    	Transaction trns = null;
//	        Session session = HibernateUtil.getSessionFactory().openSession(); 
//	        try {
//	        	trns = session.beginTransaction();
//	        	Student_Imp s=new Student_Imp();
//	    		String tmr_dt=s.TomorrowDateTime();
//	    		String tmr_date=tmr_dt.split(" ")[0];
//	    		String tmr_time=tmr_dt.split(" ")[1];
//	    		System.out.println(tmr_date);
//	    		List<Time> list_time=new ArrayList<Time>();   
//	    		List<Time> list_time1=new ArrayList<Time>();   
//	    		List<Time> list_time2=new ArrayList<Time>();   
//	    		list_time1 = session.createQuery("select dept_time from Booking_Master where dept_date=? "
//	        			+ "and description='student' and schedule_id='0' group by dept_date, "
//	        			+ "dept_time, from_id, to_id ").setDate(0, java.sql.Date.valueOf(s.DateNow())).list();
//	    		
//	        	list_time2 = session.createQuery("select dept_time from Booking_Master where dept_date=? "
//	        			+ "and description='student' and schedule_id='0' group by dept_date, "
//	        			+ "dept_time, from_id, to_id ").setDate(0, java.sql.Date.valueOf(tmr_date)).list();
//	            System.out.println(list_time1);
//	            System.out.println(list_time2);
//	            for(int i=0;i<list_time1.size();i++){
//	            	Time time=list_time1.get(i);
//	            	System.out.println(time.after(java.sql.Time.valueOf(tmr_time)));
//	            	if(time.after(java.sql.Time.valueOf(tmr_time))){
//	            		list_time.add(time);
//	            	}
//	            }
//	            System.out.println();
//	            for(int i=0;i<list_time2.size();i++){
//	            	Time time=list_time2.get(i);
//	            	System.out.println(time.before(java.sql.Time.valueOf(tmr_time)));
//	            	if(time.before(java.sql.Time.valueOf(tmr_time))){
//	            		list_time.add(time);
//	            	}
//	            }
//	            System.out.println(list_time);
//	        } catch (RuntimeException e) {
//	        	e.printStackTrace();
//	        } 
	    }
}
