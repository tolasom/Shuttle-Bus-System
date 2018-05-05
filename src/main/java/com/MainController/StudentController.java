package com.MainController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.DaoClasses.Student_Dao;
import com.DaoClasses.Student_Imp;
import com.EntityClasses.Booking_Master;
import com.EntityClasses.Location_Master;
import com.HibernateUtil.HibernateUtil;

@Controller
public class StudentController {
	//@Scheduled(cron="*/5 * * * * *")
	public void updateEmployeeInventory(){
      System.out.println("Started cron job 1");
      //createSchedule(){}
	}
	@RequestMapping(value="/date_time")
	public String DateTime(){
		Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTimeString = sdf.format(d);
        System.out.println(currentDateTimeString);
        return currentDateTimeString;
	}
	public static void main(String args[]){
		
		
//		List<int[]> list_round=new ArrayList<int[]>();
//		int[] a={1,2};
//		list_round.add(a);
//		System.out.println(list_round.get(0)[1]);
		
		
		Student_Imp s=new Student_Imp();
		String tmr_dt=s.TomorrowDateTime();
		String tmr_date=tmr_dt.split(" ")[0];
		List<Booking_Master> all_booker1=new ArrayList<Booking_Master>();    
		Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession(); 
        try {
        	trns = session.beginTransaction();
            all_booker1 = session.createQuery("select dept_time from Booking_Master where dept_date=? and description='student' and schedule_id='0' group by dept_date, dept_time, from_id, to_id ").setDate(0, java.sql.Date.valueOf(tmr_date)).list();
            System.out.println(all_booker1);
        	//List<Location_Master> locat = session.createQuery("from Location_Master where enabled=?").setBoolean(0, true).list();
            System.out.println(all_booker1);
        } catch (RuntimeException e) {
        	e.printStackTrace();
        }  
		
//		List<int[]> list_round=new ArrayList<int[]>();
//		List<Booking_Master> list_time=new ArrayList<Booking_Master>();    
//		Transaction trns = null;
//        Session session = HibernateUtil.getSessionFactory().openSession(); 
//        try {
//        	trns = session.beginTransaction();
//        	List<Location_Master> locat = session.createQuery("from Location_Master where enabled=?").setBoolean(0, true).list();
//        	for(int i=0;i<locat.size();i++){
//        		for(int j=0;j<locat.size();j++){
//        			if(locat.get(i).getId()!=locat.get(j).getId()){
//        				int[] li={locat.get(i).getId(),locat.get(j).getId()};
//        				list_round.add(li);
//        			}
//        		}
//        	}
//            System.out.println(list_round.size());
//            System.out.print(list_round.get(0)[0]);
//            System.out.println(list_round.get(0)[1]);
//            System.out.print(list_round.get(1)[0]);
//            System.out.println(list_round.get(1)[1]);
//            System.out.print(list_round.get(2)[0]);
//            System.out.println(list_round.get(2)[1]);
//            System.out.print(list_round.get(3)[0]);
//            System.out.println(list_round.get(3)[1]);
//            System.out.print(list_round.get(4)[0]);
//            System.out.println(list_round.get(4)[1]);
//            System.out.print(list_round.get(5)[0]);
//            System.out.println(list_round.get(5)[1]);
//            
//        } catch (RuntimeException e) {
//        	e.printStackTrace();
//        }  

//		
//		Student_Dao stu=new Student_Imp();
//		Transaction trns = null;
//        Session session = HibernateUtil.getSessionFactory().openSession(); 
//        try {
//        	trns = session.beginTransaction();
//        	Student_Imp s=new Student_Imp();
//    		String tmr_dt=s.TomorrowDateTime();
////        	>  Query time
//    		List<Booking_Master> list_time=stu.getAvailableTime(tmr_dt, session);
////        	>  Query from, Query to -----> permutation of from and to --> list of round
//    		List<int[]> list_round= stu.getListRound(session);    		
////        	-----> Loop time and round ---> Assign with schedule function base on list>0
//    		List<Map<String,Object>> list_time_round=new ArrayList<Map<String,Object>>();
//    		for(int i=0;i<list_time.size();i++){
//    			for(int j=0; j<list_round.size();j++){
//    				Map<String, Object> map=new HashMap<String, Object>();
//    				map.put("time", list_time.get(i));
//    				map.put("from_id", list_round.get(j)[0]);
//    				map.put("to_id", list_round.get(j)[1]);
//    				list_time_round.add(map);
//    			}
//    		}
//    		System.out.println(list_time_round.size());
//        } catch (RuntimeException e) {
//        	e.printStackTrace();
//        	trns.rollback();
//        }finally {
//            session.flush();
//            session.close();
//        } 
//		
//		
	}
	
}
