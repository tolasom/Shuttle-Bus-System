package com.client_mail;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.EntityClasses.Booking_Master;
import com.EntityClasses.User_Info;
import com.HibernateUtil.HibernateUtil;
import com.ModelClasses.Mail;
 

 
public class App {
 
    public static void main(String args[]) {
		Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
     	try {
     		trns = session.beginTransaction();
            String hql ="from Booking_Master where id='2'";
	        Query query =  session.createQuery(hql);
	        System.out.println(query.list().size());
	        if(query.list().size()>0){
	        	Booking_Master book = (Booking_Master) query.list().get(0);
		        System.out.println(book.getUser_id());
	
		        String hql1 ="from User_Info where id='"+book.getUser_id()+"'";
		        Query query1 =  session.createQuery(hql1);
		        if(query1.list().size()>0){
		        	User_Info user = (User_Info) query1.list().get(0);
		        	Mail mail = new Mail();
			        mail.setMailFrom("sopheakdy23@gmail.com");
			        mail.setMailTo("maimom61@gmail.com");
			        mail.setMailSubject("Congratulation, You have booked successfully");
			        mail.setFile_name("qr_code_template.txt");
			 
			        Map < String, Object > model = new HashMap < String, Object > ();
			        model.put("name", user.getName());
			        model.put("location", "Pune");
			        model.put("qr", book.getQr());
			        mail.setModel(model);
			 
			        AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
			        MailService mailService = (MailService) context.getBean("mailService");
			        mailService.sendEmail(mail);
			        context.close();
		        }  
	        }
	        trns.commit();
	
     	}catch (RuntimeException e){
	       System.out.println(e);
	     }finally {
	            session.flush();
	            session.close();
	     }
    }
    public String Key(int mount){
   		 SecureRandom random = new SecureRandom();
   		    String key;
   		  
   		    key=  new BigInteger(mount*5, random).toString(32);
   		   
   		return key;
   	}

}
