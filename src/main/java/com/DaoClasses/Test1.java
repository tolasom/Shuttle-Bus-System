package com.DaoClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.EntityClasses.Bus_Master;
import com.EntityClasses.Schedule_Master;
import com.HibernateUtil.HibernateUtil;

public class Test1 {
	public static void main(String[] args) throws ParseException {

//		String time1 = "23:30:00";
//		String time2 = "6:30:00";
// 
//		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//		Date date1 = format.parse(time1);
//		Date date2 = format.parse(time2);
//		long difference = date2.getTime() - date1.getTime();
//		long duration =difference/(1000*60*60);
//		System.out.println(duration);
		
		List<Integer> ints = new ArrayList<Integer>();
		ints.add(1);
		ints.add(2);
		System.out.println(ints.toString());
		
		
		
//		List<Schedule_Master> sch=new ArrayList<Schedule_Master>();
//		List<Map<String,Object>> all_bus =new ArrayList<Map<String,Object>>();
//		System.out.println(all_bus); 
//		List<Integer> ava_bus=new ArrayList<Integer>();
//		List<Integer> una_bus=new ArrayList<Integer>();
//		Session session = HibernateUtil.getSessionFactory().openSession();     
//		try {
//			
//			sch=session.createQuery("from Schedule_Master where dept_date=:date and to_id=:to and from_id=:from")
//					.setDate("date",java.sql.Date.valueOf("2018-03-31"))
//					.setParameter("to", 2)
//					.setParameter("from", 1).list();
//			
//			for(int i=0;i<sch.size();i++){
//				if(time_same_date(sch.get(i).getDept_time().toString(),"3:00:00",8)){
//					ava_bus.add(sch.get(i).getBus_id());
//				}else{
//					una_bus.add(sch.get(i).getBus_id());
//				}
//			}
//			Map<String, Object> map=new HashMap<String , Object>();
//			map.put("unavailable_bus", una_bus);
//			Map<String, Object> ava=new HashMap<String , Object>();
//			map.put("available_bus", ava_bus);
//			//all_bus.add(ava);
//			all_bus.add(map);
//	         System.out.println(all_bus); 
//	         List<Integer> a= (List<Integer>) all_bus.get(0).get("unavailable_bus");
//	         System.out.println(a.size());
//	         List<Bus_Master> query_all_bus=new ArrayList<Bus_Master>();
//	         query_all_bus = session.createQuery("from Bus_Master where enabled=? order by number_of_seat asc").setBoolean(0, true).list();  
//	         if(query_all_bus.size()>0){            
//		              for(int i=0;i<query_all_bus.size();i++){	
//		                  Map<String,Object> map1 =new HashMap<String,Object>();				
//		                  map1.put("bus_model", query_all_bus.get(i).getModel());
//		                  map1.put("number_of_seat", String.valueOf(query_all_bus.get(i).getNumber_of_seat()));
//		                  map1.put("id", String.valueOf(query_all_bus.get(i).getId()));
//		                  all_bus.add(map1);
//		              } 
//	        }
//	         System.out.println(all_bus);
//        } catch (RuntimeException e) {
//        	e.printStackTrace();
//        }   

	}
	public static Boolean time_same_date(String user_time, String time,long time_dura) throws ParseException{
		 
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = format.parse(user_time);
		Date date2 = format.parse(time);
		long difference = date2.getTime() - date1.getTime();
		long duration =difference/(1000*60*60);
		if(duration>=time_dura||duration<=-time_dura){
			return true;
		}else{
			return false;
		}
	}
}
