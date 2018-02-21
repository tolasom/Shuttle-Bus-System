package com.DaoClasses;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.crypto.SecretKey;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;



























































//import org.springframework.stereotype.Service;
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
import com.ModelClasses.Schedule_Model;
import com.client_mail.ApplicationConfig;
//import com.ServiceClasses.usersService;
//import com.client_mail.ApplicationConfig;
//import com.client_mail.MailService;
import com.client_mail.MailService;




@Repository
public class userDaoImpl implements usersDao{
	
	Encryption encrypt= new Encryption();
	Decryption decrypt= new Decryption();
	
	
	
	//===================For SS========================================  
    
	public String Key(int mount){
		 SecureRandom random = new SecureRandom();
		    String key;
		  
		    key=  new BigInteger(mount*5, random).toString(32);
		   
		return key;
	}
	
    
    
  //=================================================================    

	
	
	
	public User_Info findByUserName(String username) {
    	Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
		List<User_Info> users = new ArrayList<User_Info>();
	
		try {
            trns1 = session.beginTransaction();
            users = session.createQuery("from User_Info where email=?").setParameter(0, username).list();
            		//setParameter(0, username).list();
            
            if (users.size() > 0) {
    			return users.get(0);
    		} else {
    			return null;
    		}
        } catch (RuntimeException e) {
        	
        }                         
		return null;
	}



	public int saveBus(Bus_Master bus) {
		List <Bus_Master> buses  = new ArrayList<Bus_Master>();
    	Transaction trns7 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns7 = session.beginTransaction();
            String queryString = "FROM Bus_Master where plate_number=:number and enabled=:status";
            Query query = session.createQuery(queryString);
            query.setString("number",bus.getPlate_number());
            query.setBoolean("status", true);
            buses=(List<Bus_Master>)query.list();
         
    			if(buses.size()>0)
    				return 0;
    		Timestamp created_at = new Timestamp(System.currentTimeMillis());
        	bus.setCreated_at(created_at);
        	bus.setEnabled(true);
            session.save(bus);  
            session.getTransaction().commit();
        } catch (RuntimeException e) {
        	if (trns7 != null) {
                trns7.rollback();
            }
            e.printStackTrace();
            return 5;
        } finally {
            session.flush();
            session.close();
        }
		return 1;
	}
	
	
	public List<User_Info> getAllUsers() {
      	List<User_Info> users= new ArrayList<User_Info>();
   		Transaction trns25 = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			List<Map<String,Object>> list_map = new ArrayList<Map<String,Object>>();
			trns25  = session.beginTransaction();
			String queryString  = "from UserRole where role=:role";
 		 	Query query = session.createQuery(queryString);
 		 	query.setString("role", "ROLE_ADMIN");
 		 	List<UserRole> roles = query.list();
 		 	for(int i=0;i<roles.size();i++)
 		 	{
 		 		User_Info user1 = new User_Info();
 		 		User_Info user = roles.get(i).getUser_info();
 		 		user1.setId(user.getId());
 		 		user1.setName(user.getName());
 		 		users.add(user1);
 		 	}
 		 	
		}
		catch(RuntimeException e)
		{
			e.printStackTrace();			
		}
		finally{
			session.flush();
			session.close();
		}
        return users;
    } 
	
	
	
	public int updateBus(Bus_Master bus) {
		int count = 0;
		List <Bus_Master> buses  = new ArrayList<Bus_Master>();
    	Transaction trns7 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns7 = session.beginTransaction();
            buses = getAllBuses();
            for(Bus_Master b:buses)
            {
            	if(bus.getId()!=b.getId())
            			{
            				if (b.getPlate_number().equals(bus.getPlate_number()))
            					count++;
            			}
            }
            if(count>=1)
            	return 0;
            String queryString = "FROM Bus_Master where id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",bus.getId());
            Bus_Master updatedBus  = (Bus_Master) query.uniqueResult();
            Timestamp updated_at = new Timestamp(System.currentTimeMillis());
            updatedBus.setDescription(bus.getDescription());
            updatedBus.setModel(bus.getModel());
            updatedBus.setPlate_number(bus.getPlate_number());
        	updatedBus.setUpdated_at(updated_at);
        	updatedBus.setNumber_of_seat(bus.getNumber_of_seat());
            session.update(updatedBus);  
            session.getTransaction().commit();
        } catch (RuntimeException e) {
        	if (trns7 != null) {
                trns7.rollback();
            }
            e.printStackTrace();
            return 5;
        } finally {
            session.flush();
            session.close();
        }
		return 1;
		}
	
	public List <Pickup_Location_Master> getPickUpLocationByName (String name){
		List <Pickup_Location_Master> p_locations  = new ArrayList<Pickup_Location_Master>();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 = session.beginTransaction();
            String queryString = "from Pickup_Location_Master where name=:name";
            Query query = session.createQuery(queryString);
            query.setString("name",name);
            p_locations=(List<Pickup_Location_Master>)query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return p_locations;
        } finally {
            session.flush();
            session.close();
        }
        return p_locations;
		
	}
	
	
	public List <Location_Master> getLocationByName (String name){
		List <Location_Master> locations  = new ArrayList<Location_Master>();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 = session.beginTransaction();
            String queryString = "from Location_Master where name=:name";
            Query query = session.createQuery(queryString);
            query.setString("name",name);
            locations=(List<Location_Master>)query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return locations;
        } finally {
            session.flush();
            session.close();
        }
        return locations;
		
	}
	
	
	
	
	
	public int updateLocation(Location_Master location) {
		int count = 0;
		List <Location_Master> locations  = new ArrayList<Location_Master>();
		List <Pickup_Location_Master> p_locations  = new ArrayList<Pickup_Location_Master>();
    	Transaction trns7 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns7 = session.beginTransaction();
            locations = getAllLocations();
            p_locations = getAllPickUpLocations();
            for(Location_Master l:locations)
            {
            	if(location.getId()!=l.getId())
            			{
            				if (l.getName().equals(location.getName()))
            					count++;
            			}
            }
            p_locations = getPickUpLocationByName(location.getName());
            if(count>=1||p_locations.size()>0)
            	return 0;
            String queryString = "FROM Location_Master where id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",location.getId());
            Location_Master updatedLocation  = (Location_Master) query.uniqueResult();
            Timestamp updated_at = new Timestamp(System.currentTimeMillis());
            updatedLocation.setName(location.getName());
            updatedLocation.setUpdated_at(updated_at);
            session.update(updatedLocation);  
            session.getTransaction().commit();
        } catch (RuntimeException e) {
        	if (trns7 != null) {
                trns7.rollback();
            }
            e.printStackTrace();
            return 5;
        } finally {
            session.flush();
            session.close();
        }
		return 1;
		}
	
	
	
	public int updatePickUpLocation(Pickup_Location_Master p_location){
		int count = 0;
		List <Location_Master> locations  = new ArrayList<Location_Master>();
		List <Pickup_Location_Master> p_locations  = new ArrayList<Pickup_Location_Master>();
    	Transaction trns7 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns7 = session.beginTransaction();
            locations = getAllLocations();
            p_locations = getAllPickUpLocations();
            for(Pickup_Location_Master pl:p_locations)
            {
            	if(p_location.getId()!=pl.getId())
            			{
            				if (pl.getName().equals(p_location.getName()))
            					count++;
            			}
            }
            locations = getLocationByName(p_location.getName());
            if(count>=1||locations.size()>0)
            	return 0;
            String queryString = "FROM Pickup_Location_Master where id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",p_location.getId());
            Pickup_Location_Master updatedPLocation  = (Pickup_Location_Master) query.uniqueResult();
            Timestamp updated_at = new Timestamp(System.currentTimeMillis());
            updatedPLocation.setName(p_location.getName());
            updatedPLocation.setUpdated_at(updated_at);
            session.update(updatedPLocation);  
            session.getTransaction().commit();
        } catch (RuntimeException e) {
        	if (trns7 != null) {
                trns7.rollback();
            }
            e.printStackTrace();
            return 5;
        } finally {
            session.flush();
            session.close();
        }
		return 1;
		
	}
	
	
	


	public List<Bus_Master> getAllBuses(){
		List<Bus_Master> p= new ArrayList<Bus_Master>();
        Transaction trns16 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns16 = session.beginTransaction();
            Query query = session.createQuery("from Bus_Master where enabled =:status");
            query.setBoolean("status", true);
            p = query.list();
            } catch (RuntimeException e) {
            e.printStackTrace();
            return p;
        } finally {
            session.flush();
            session.close();
        }
        return p;
		
	}
	public List<Location_Master> getAllLocations(){
		List<Location_Master> p= new ArrayList<Location_Master>();
        Transaction trns16 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns16 = session.beginTransaction();
            Query query = session.createQuery("from Location_Master where enabled =:status");
            query.setBoolean("status", true);
            p = query.list();
            } catch (RuntimeException e) {
            e.printStackTrace();
            return p;
        } finally {
            session.flush();
            session.close();
        }
        return p;
		
	}
	public List<Pickup_Location_Master> getAllPickUpLocations(){
		List<Pickup_Location_Master> p= new ArrayList<Pickup_Location_Master>();
        Transaction trns16 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns16 = session.beginTransaction();
            Query query = session.createQuery("from Pickup_Location_Master where enabled =:status");
            query.setBoolean("status", true);
            p = query.list();
            } catch (RuntimeException e) {
            e.printStackTrace();
            return p;
        } finally {
            session.flush();
            session.close();
        }
        return p;
		
	}
	public Bus_Master getBusById (int id){
		Bus_Master bus= new Bus_Master();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 = session.beginTransaction();
            String queryString = "from Bus_Master where id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",id);
            bus=(Bus_Master)query.uniqueResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return bus;
        } finally {
            session.flush();
            session.close();
        }
        return bus;
		
	}
	
	
	public Booking_Master getBookingById (int id){
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
	
	
	public List<Booking_Master> getBookingByScheduleId (int id){
		List<Booking_Master> booking= new ArrayList<Booking_Master>();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 = session.beginTransaction();
            String queryString = "from Booking_Master where schedule_id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",id);
            booking=(List<Booking_Master>)query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return booking;
        } finally {
            session.flush();
            session.close();
        }
        return booking;
		
	}
	
	
	public Schedule_Master getScheduleById (int id){
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
	
	
	public Location_Master getLocationById (int id){
		Location_Master location= new Location_Master();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 = session.beginTransaction();
            String queryString = "from Location_Master where id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",id);
            location=(Location_Master)query.uniqueResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return location;
        } finally {
            session.flush();
            session.close();
        }
        return location;
		
	}
	
	
	
	
	public Pickup_Location_Master getPickUpLocationById (int id){
		Pickup_Location_Master p_location= new Pickup_Location_Master();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 = session.beginTransaction();
            String queryString = "from Pickup_Location_Master where id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",id);
            p_location=(Pickup_Location_Master)query.uniqueResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return p_location;
        } finally {
            session.flush();
            session.close();
        }
        return p_location;
		
	}
	
	
	
	public int deleteBus(int id){
		Transaction trns21 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Bus_Master bus = new Bus_Master();
        try {
            trns21 = session.beginTransaction();
            String queryString = "from Bus_Master where id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",id);
            bus=(Bus_Master)query.uniqueResult();
            bus.setEnabled(false);
            session.update(bus);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.flush();
            session.close();
        }
		return 1;
	}
	public int deleteLocation(int id){
		Transaction trns21 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Location_Master location = new Location_Master();
        try {
            trns21 = session.beginTransaction();
            String queryString = "from Location_Master where id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",id);
            location=(Location_Master)query.uniqueResult();
            location.setEnabled(false);
            session.update(location);
            deletePickUpLocationByLocatinId(id);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.flush();
            session.close();
        }
		return 1;
	}
	public void deletePickUpLocationByLocatinId(int id){
		Transaction trns24 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
           	trns24 = session.beginTransaction();
            String queryString = "update Pickup_Location_Master p set p.enabled=:status where p.location_id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",id);
            query.setBoolean("status", false);
            query.executeUpdate();
            session.getTransaction().commit();
         
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
		
	}
	
	
	
	public int deletePickUpLocation(int id){
		Transaction trns21 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Pickup_Location_Master p_location = new Pickup_Location_Master();
        try {
            trns21 = session.beginTransaction();
            String queryString = "from Pickup_Location_Master where id=:id";
            Query query = session.createQuery(queryString);
            query.setInteger("id",id);
            p_location=(Pickup_Location_Master)query.uniqueResult();
            p_location.setEnabled(false);
            session.update(p_location);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.flush();
            session.close();
        }
		return 1;
		
	}
	
	
	public int saveLocation(Location_Master location){
		List <Location_Master> locations  = new ArrayList<Location_Master>();
		List <Pickup_Location_Master> p_locations  = new ArrayList<Pickup_Location_Master>();
    	Transaction trns7 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns7 = session.beginTransaction();
            String queryString = "FROM Location_Master where name=:name and enabled=:status";
            String queryString2 = "FROM Pickup_Location_Master where name=:name and enabled=:status";
            Query query = session.createQuery(queryString);
            query.setString("name",location.getName());
            query.setBoolean("status",true);
            Query query2 = session.createQuery(queryString2);
            query2.setString("name",location.getName());
            query2.setBoolean("status",true);
            locations=(List<Location_Master>)query.list();
            p_locations=(List<Pickup_Location_Master>)query2.list();
            
    			if(locations.size()>0 || p_locations.size()>0)
    				return 0;
    		Timestamp created_at = new Timestamp(System.currentTimeMillis());
        	location.setCreated_at(created_at);
        	location.setEnabled(true);
            session.save(location);  
            session.getTransaction().commit();
        } catch (RuntimeException e) {
        	if (trns7 != null) {
                trns7.rollback();
            }
            e.printStackTrace();
            return 5;
        } finally {
            session.flush();
            session.close();
        }
		return 1;
	}
	public int saveSchedule(Schedule_Model schedule){
		List <Schedule_Master> schedules  = new ArrayList<Schedule_Master>();
		Schedule_Master s = new Schedule_Master();
    	Transaction trns7 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns7 = session.beginTransaction();
            String queryString = "FROM Schedule_Master where code=:code";
            Query query = session.createQuery(queryString);
            query.setString("code",schedule.getCode());
            schedules=(List<Schedule_Master>)query.list();
            if(schedules.size()>0)
    				return 0;
    		Timestamp created_at = new Timestamp(System.currentTimeMillis());
    		s.setBus_id(schedule.getBus_id());
    		s.setCode(schedule.getCode());
    		s.setCreated_at(created_at);
    		s.setDept_date();
    		s.setDept_time(schedule.getDept_time());
    		s.setDestination_id(schedule.getDestination_id());
    		s.setDriver_id(schedule.getDriver_id());
    		s.setNumber_booking(schedule.getNumber_booking());
    		s.setNumber_customer(schedule.getNumber_customer());
    		s.setNumber_staff(schedule.getNumber_staff());
    		s.setNumber_student(schedule.getNumber_student());
    		s.setRemaining_seat(schedule.getRemaining_seat());
    		s.setSource_id(schedule.getSource_id());
            session.save(schedule);  
            session.getTransaction().commit();
        } catch (RuntimeException e) {
        	if (trns7 != null) {
                trns7.rollback();
            }
            e.printStackTrace();
            return 5;
        } finally {
            session.flush();
            session.close();
        }
		return 1;
	}
	
	public int savePickUpLocation(Pickup_Location_Master p_location){
		List <Pickup_Location_Master> p_locations  = new ArrayList<Pickup_Location_Master>();
		List <Location_Master> locations  = new ArrayList<Location_Master>();
    	Transaction trns7 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns7 = session.beginTransaction();
            String queryString = "FROM Pickup_Location_Master where name=:name and enabled=:status";
            String queryString2 = "FROM Location_Master where name=:name and enabled=:status";
            Query query = session.createQuery(queryString);
            query.setString("name",p_location.getName());
            query.setBoolean("status",true);
            Query query2 = session.createQuery(queryString2);
            query2.setString("name",p_location.getName());
            query2.setBoolean("status",true);
            p_locations=(List<Pickup_Location_Master>)query.list();
            locations=(List<Location_Master>)query2.list();
         
    			if(locations.size()>0 || p_locations.size()>0)
    				return 0;
    		Timestamp created_at = new Timestamp(System.currentTimeMillis());
        	p_location.setCreated_at(created_at);
        	p_location.setEnabled(true);
            session.save(p_location);  
            session.getTransaction().commit();
        } catch (RuntimeException e) {
        	if (trns7 != null) {
                trns7.rollback();
            }
            e.printStackTrace();
            return 5;
        } finally {
            session.flush();
            session.close();
        }
		return 1;
		
	}
	public List <Booking_Master> getAllCurrentBookings(){
		List <Booking_Master> bookings  = new ArrayList<Booking_Master>();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 =  session.beginTransaction();
            String queryString = "from Booking_Master where dept_date>=:localDate";
            Query query = session.createQuery(queryString);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            System.out.println(dtf.format(localDate));
            query.setDate("localDate", date);
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
	
	
	public List <Schedule_Master> getAllCurrentSchedules(){
		List <Schedule_Master> schedules  = new ArrayList<Schedule_Master>();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 =  session.beginTransaction();
            String queryString = "from Schedule_Master where dept_date>=:localDate";
            Query query = session.createQuery(queryString);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            System.out.println(dtf.format(localDate));
            query.setDate("localDate", date);
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
	
	public List <Schedule_Master> getAllHistoricalSchedules(){
		List <Schedule_Master> schedules  = new ArrayList<Schedule_Master>();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 =  session.beginTransaction();
            String queryString = "from Schedule_Master where dept_date<:localDate";
            Query query = session.createQuery(queryString);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            System.out.println(dtf.format(localDate));
            query.setDate("localDate", date);
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
	
	
	
	public List <Booking_Master> getAllHistoricalBookings(){
		List <Booking_Master> bookings  = new ArrayList<Booking_Master>();
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 =  session.beginTransaction();
            String queryString = "from Booking_Master where dept_date<:localDate";
            Query query = session.createQuery(queryString);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            System.out.println(dtf.format(localDate));
            query.setDate("localDate", date);
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
	
	
	
	public List <Schedule_Master> getAllSchedulesByMonth(String month, String year) throws ParseException{
		List <Schedule_Master> schedules  = new ArrayList<Schedule_Master>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 =  session.beginTransaction();
            String queryString = "from Schedule_Master s where s.dept_date between :date1 and :date2";
            Query query = session.createQuery(queryString);
            Date date1;
    		Date date2;
            String first = "01/"+month+"/"+year;
            date1 = formatter.parse(first);
            String last = getLastDateOfMonth(date1)+"/"+month+"/"+year;
       		date2 = formatter.parse(last);
       		System.out.println(date1+"   "+date2);
       		
      		query.setDate("date1", date1);
            query.setDate("date2", date2);
            
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
	
	
	
	public List <Schedule_Master> schedule_list(String date, String month, String year) throws ParseException{
		List <Schedule_Master> schedules  = new ArrayList<Schedule_Master>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Transaction trns19 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns19 =  session.beginTransaction();
            String queryString = "from Schedule_Master s where s.dept_date=:date1";
            Query query = session.createQuery(queryString);
            Date date1;
            String first = date+"/"+month+"/"+year;
            date1 = formatter.parse(first);
      		query.setDate("date1", date1);
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
	
	
	
	
	public String getLastDateOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return Integer.toString(cal.getTime().getDate());
    }
	

	
	
}






