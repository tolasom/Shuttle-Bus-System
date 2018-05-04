package com.DaoClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.EntityClasses.*;
import com.HibernateUtil.HibernateUtil;
import javax.transaction.Transaction;
import com.ModelClasses.Student_Booking;
import getInfoLogin.IdUser;
import org.hibernate.Query;
import org.hibernate.Session;
public class StudentDaoImpl implements StudentDao{
     IdUser id = new IdUser();

     public Map<String,Object> student_info(){
        Map<String,Object> student = new HashMap<String,Object>();
         Transaction trns1 = null;
         Session session = HibernateUtil.getSessionFactory().openSession();
         try {
             IdUser UserId = new IdUser();

             User_Info user = (User_Info) session.load(User_Info.class,UserId.getAuthentic());

             student.put("username",user.getUsername());
             student.put("email",user.getEmail());
             student.put("phone",user.getPhone_number());
             student.put("batch",user.getBatch_id());
             student.put("ticket",user.getNumber_ticket());
             student.put("profile",user.getProfile());

         } catch (RuntimeException e) {
             e.printStackTrace();
         }
         finally {
             session.flush();
             session.close();
         }

        return student;
    }
    public List<Map<String,Object>> location_info(){
         List<Map<String,Object>> map_location = new ArrayList<Map<String, java.lang.Object>>();
         Transaction trns1 = null;
         Session session = HibernateUtil.getSessionFactory().openSession();
         try {
              String hql = "from Location_Master";
              Query query = session.createQuery(hql);
              List<Location_Master> list_location = query.list();

              for(Location_Master location :list_location){
                 Map<String,Object> map = new HashMap<String, Object>();
                 map.put("location_name",location.getName());
                 map.put("location_id",location.getId());
                 map_location.add(map);
              }

         } catch (RuntimeException e) {
             e.printStackTrace();
         }
         finally {
             session.flush();
             session.close();
         }
         return map_location;
    }

    public Map<String,Object> student_booking(Student_Booking book_data){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Map<String,Object> map = new HashMap<String, Object>();

        try {


            Booking_Master booking_master = new Booking_Master();
            booking_master.setUser_id(id.getAuthentic());
            booking_master.setDestination_id(book_data.getDestination());
            booking_master.setSource_id(book_data.getSource());
            booking_master.setDept_date(java.sql.Date.valueOf(book_data.getDeparture_date()));
            session.save(booking_master);
            if(book_data.getChoice()==2){
                Booking_Master booking_return = new Booking_Master();
                booking_return.setUser_id(id.getAuthentic());
                booking_return.setDestination_id(book_data.getSource());
                booking_return.setSource_id(book_data.getDestination());
                booking_return.setDept_date(java.sql.Date.valueOf(book_data.getReturn_date()));
                session.save(booking_return);
            }
            session.beginTransaction().commit();
            map.put("status",true);

        } catch (RuntimeException e) {
            e.printStackTrace();
            session.beginTransaction().rollback();
        }
        finally {
            session.flush();
            session.close();
        }
        return map;
    }

    public List<Object> list_booking_date(){
        List<Object> list_date_booking = new ArrayList<Object>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<Booking_Master> list_booking_master = new ArrayList<Booking_Master>();
            String hql = "From Booking_Master where dept_date >= current_date() and user_id = "+id.getAuthentic();
            Query query = session.createQuery(hql);
            list_booking_master = query.list();
            for(Booking_Master booking_master : list_booking_master){

                list_date_booking.add(booking_master.getDept_date());
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            session.flush();
            session.close();
        }
        return list_date_booking;
    }
    public List<Map<String,Object>> getHistory(){
         List<Map<String,Object>> list_history = new ArrayList<Map<String,Object>>();
         List<Booking_Master> list_booking = new ArrayList<Booking_Master>();
         Session session = HibernateUtil.getSessionFactory().openSession();
         try {
             String hql = "From Booking_Master where user_id="+id.getAuthentic();
             Query query = session.createQuery(hql);
             query.setMaxResults(10);
              list_booking= query.list();
             for(Booking_Master booking_master : list_booking){
                 Map<String,Object> map = new HashMap<String, Object>();
                 map.put("destination_id",booking_master.getDestination_id());
                 map.put("source_id",booking_master.getSource_id());
                 map.put("departure_date",booking_master.getDept_date());
                 if(booking_master.getSchedule_id() !=0 ){
                     Schedule_Master schedule_master =
                             (Schedule_Master) session.load(Schedule_Master.class,booking_master.getSchedule_id());
                     if(schedule_master.getDriver_id()!=0){
                         User_Info user_info =
                                 (User_Info) session.load(User_Info.class,schedule_master.getDriver_id());
                         map.put("driver",user_info.getName());
                     }
                     if(schedule_master.getBus_id()!=0){
                         Bus_Master bus_master =
                                 (Bus_Master) session.load(Bus_Master.class,schedule_master.getBus_id());
                         map.put("bus_model",bus_master.getModel());
                         map.put("plate_number",bus_master.getPlate_number());
                         map.put("total_seats",bus_master.getNumber_of_seat());
                     }
                     map.put("schedule",true);
                 }
                 list_history.add(map);

             }
         }
         catch (RuntimeException e) {
            e.printStackTrace();
         }
         finally {
            session.flush();
            session.close();
         }

         return list_history;
    }



}

