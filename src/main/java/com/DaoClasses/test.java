package com.DaoClasses;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

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
import com.EntityClasses.Booking_Request_Master;
import com.EntityClasses.Bus_Master;
import com.EntityClasses.Dept_Time_Master;
import com.EntityClasses.Location_Master;
import com.EntityClasses.Pickup_Location_Master;
import com.EntityClasses.Schedule_Master;
import com.EntityClasses.UserRole;
import com.EntityClasses.User_Info;
import com.HibernateUtil.HibernateUtil;
import com.ModelClasses.IdentifyTypeUser;
import com.ModelClasses.Mail;
import com.ModelClasses.Project_Model;
import com.ModelClasses.Reset_Password;
import com.ModelClasses.Schedule_Model;
import com.ModelClasses.UserModel;
import com.client_mail.ApplicationConfig;
//import com.ServiceClasses.usersService;
//import com.client_mail.ApplicationConfig;
//import com.client_mail.MailService;
import com.client_mail.MailService;




public class test {
	
	public static void main(String args[]) throws ParseException
	{
		Schedule_Master schedule = new userDaoImpl().getScheduleById(10);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = null;
		Date d2 = null;
		d1 = format.parse(dtf.format(now));
		d1.setHours(0);
		d1.setMinutes(0);
		d1.setSeconds(0);
		d2 = format.parse(schedule.getDept_date().toString());
        System.out.println(d1);
        System.out.println(d2);
        long diff = d2.getTime() - d1.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);

		System.out.print(diffDays + " days, ");
//        System.out.println(schedule.getDept_date().compareTo(date));
//        Days diffInDays = Days.daysBetween(dateOfBirth, currentDate);
//        if (schedule.getDept_date().compareTo(date)>1)
//        {
//        	System.out.println("Cannot show");
//        }
//        else
//        System.out.println("Can show");
	}
}


