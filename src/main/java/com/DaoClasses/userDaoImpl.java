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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.EntityClasses.Bus_Master;
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
            String queryString = "FROM Bus_Master where plate_number=:number";
            Query query = session.createQuery(queryString);
            query.setString("number",bus.getPlate_number());
            buses=(List<Bus_Master>)query.list();
         
    			if(buses.size()>0)
    				return 0;
    		Timestamp created_at = new Timestamp(System.currentTimeMillis());
        	bus.setCreated_at(created_at);
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
	
	public int updateBus(Bus_Master bus) {
		System.out.println("++++ "+bus.getPlate_number());
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
            String queryString = "FROM Bus_Master where plate_number=:number";
            Query query = session.createQuery(queryString);
            query.setString("number",bus.getPlate_number());
            Bus_Master updatedBus  = (Bus_Master) query.uniqueResult();
            Timestamp updated_at = new Timestamp(System.currentTimeMillis());
        	bus.setUpdated_at(updated_at);
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


	public List<Bus_Master> getAllBuses(){
		List<Bus_Master> p= new ArrayList<Bus_Master>();
        Transaction trns16 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns16 = session.beginTransaction();
            p = session.createQuery("from Bus_Master").list();
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



	
	
}






