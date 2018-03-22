package com.DaoClasses;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

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
		User_Info customer = new User_Info();
		customer =  getCustomerById(3);
		System.out.println(customer.getEmail()+"  "+customer.getUsername());
	      
	}
	
		public static String Key(int mount){
			 SecureRandom random = new SecureRandom();
			    String key;
			  
			    key=  new BigInteger(mount*5, random).toString(32);
			   
			return key;
		}	
		
		public static User_Info getCustomerById(int id) {
			User_Info customer = new User_Info();
	   		Transaction trns25 = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			try{
				List<Map<String,Object>> list_map = new ArrayList<Map<String,Object>>();
				trns25  = session.beginTransaction();
				String queryString  = "from User_Info where id=:id";
	 		 	Query query = session.createQuery(queryString);
	 		 	query.setInteger("id", id);
	 		 	customer = (User_Info) query.uniqueResult();
	 		 	
			}
			catch(RuntimeException e)
			{
				e.printStackTrace();			
			}
			finally{
				session.flush();
				session.close();
			}
	        return customer;
	    } 
	
}
	
	
	
	






