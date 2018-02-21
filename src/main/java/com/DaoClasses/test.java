package com.DaoClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
		
		System.out.println(getScheduleSequence());
		
	}
	
	
	public static String getScheduleSequence(){
		List<Schedule_Master> schedules = new ArrayList<Schedule_Master>();
		schedules = new userDaoImpl().getAllHistoricalSchedules();
		int code;
		String scode= "000001";
		if(schedules.size()>0){
			code = 1000000+schedules.get(schedules.size()-1).getId()+1;
			scode = Integer.toString(code);
			scode = scode.substring(1);
			return scode;
		}
		else 
			return scode;
		
	}
	
	
	
	
	
}






