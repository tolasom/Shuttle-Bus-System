package getInfoLogin;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.EntityClasses.User_Info;
import com.HibernateUtil.HibernateUtil;


public class IdUser {

	public int getAuthentic(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String full_email= userDetail.getUsername().toString();
		System.out.println("Full Email: "+full_email);
		String email = full_email.replace("--google","");
		System.out.println("email: "+email);
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
		List<User_Info> users = new ArrayList<User_Info>();
	
		try {
            trns1 = session.beginTransaction();
            System.out.println("KK: "+userDetail.getUsername());
            users = session.createQuery("from User_Info where email=? and enabled=?").setString(0, email).setBoolean(1, true).list();
            
           System.out.println(users.size());
        } catch (RuntimeException e) {
        	
        }finally{
			session.flush();
			session.close();
		}
		return users.get(0).getId();
}
	public static void main(String args[]){
		Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
		List<User_Info> users = new ArrayList<User_Info>();
	
		try {
            trns1 = session.beginTransaction();
            users = session.createQuery("from User_Info where name=? and enabled=?").setString(0, "SS").setBoolean(1, true).list();
            
           System.out.println(users.size());
        } catch (RuntimeException e) {
        	
        }                   
	}
}
