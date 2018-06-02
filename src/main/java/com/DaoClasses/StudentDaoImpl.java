package com.DaoClasses;

import java.text.SimpleDateFormat;
import java.util.*;

import com.EntityClasses.*;
import com.HibernateUtil.HibernateUtil;
import javax.transaction.Transaction;

import com.ModelClasses.ID_Class;
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


    public List<Integer> listBookedDate(){
        List<Integer> list = new ArrayList<Integer>();
        Transaction trns1 = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "From Batch_Master";
            Query query = session.createQuery(hql);
            List<Batch_Master> batch_masterList= query.list();
            for( Batch_Master batch_master : batch_masterList){
                list.add(batch_master.getDate_of_leaving());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            session.flush();
            session.close();
        }

        return list;
    }


    public List<Map<String,Object>> location_info(){
         List<Map<String,Object>> map_location = new ArrayList<Map<String, java.lang.Object>>();
         Transaction trns1 = null;
         Session session = HibernateUtil.getSessionFactory().openSession();
         try {
              String hql = "from Location_Master where enabled='true' and forstudent='true'";
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
        Custom_Imp custom_dao = new Custom_Imp();
        Location_Master location_master = new Location_Master();
        try {
            Booking_Master booking_master = new Booking_Master();
            booking_master.setUser_id(id.getAuthentic());
            booking_master.setFrom_id(book_data.getDestination());
            booking_master.setTo_id(book_data.getSource());
            booking_master.setDept_date(java.sql.Date.valueOf(book_data.getDeparture_date()));
            booking_master.setDescription("student");
            booking_master.setNotification("Booked");
            booking_master.setNumber_booking(1);
            booking_master.setAdult(1);
            booking_master.setChild(0);
            location_master = (Location_Master) session.load(Location_Master.class,book_data.getDestination());
            booking_master.setDept_time(location_master.getDept_time());
            session.save(booking_master);
            booking_master.setCode(Custom_Imp.getBookingSequence(booking_master.getId()));
            session.update(booking_master);

            if(book_data.getChoice()==2){
                Booking_Master booking_return = new Booking_Master();
                booking_return.setUser_id(id.getAuthentic());
                booking_return.setDestination_id(book_data.getSource());
                booking_return.setSource_id(book_data.getDestination());
                booking_return.setDept_date(java.sql.Date.valueOf(book_data.getReturn_date()));
                booking_return.setDescription("student");
                booking_return.setNotification("Booked");
                booking_return.setChild(0);
                booking_return.setAdult(0);
                booking_return.setNumber_booking(1);
                location_master = (Location_Master) session.load(Location_Master.class,book_data.getSource());
                booking_return.setDept_time(location_master.getDept_time());
                session.save(booking_return);
                booking_return.setCode(Custom_Imp.getBookingSequence(booking_return.getId()));
                session.update(booking_return);
            }

            User_Info user_info = (User_Info) session.load(User_Info.class,id.getAuthentic());
            user_info.setNumber_ticket(user_info.getNumber_ticket()-book_data.getChoice());

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
             String hql = "From Booking_Master where notification ='Booked' and user_id="+id.getAuthentic();
             Query query = session.createQuery(hql);
             query.setMaxResults(10);
              list_booking= query.list();
             for(Booking_Master booking_master : list_booking){
                 Map<String,Object> map = new HashMap<String, Object>();
                 Location_Master location_master = new Location_Master();
                 map.put("destination_id",booking_master.getFrom_id());
                 map.put("source_id",booking_master.getTo_id());
                 map.put("departure_date",booking_master.getDept_date());
                 map.put("child",booking_master.getChild());
                 map.put("adult",booking_master.getAdult());
                 map.put("number_of_seats",booking_master.getNumber_booking());
                 map.put("schedule_id",booking_master.getId());
                 map.put("status",booking_master.getNotification());
                 location_master = (Location_Master)
                         session.load(Location_Master.class,booking_master.getFrom_id());
                 map.put("destination_name",location_master.getName());
                 location_master = (Location_Master) session.load(Location_Master.class,booking_master.getTo_id());
                 map.put("source_name",location_master.getName());
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
                     map.put("code",booking_master.getCode());
                 }
                 if(booking_master.getQr()!=null){
                     map.put("qr_code",booking_master.getQr());
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

    public List<Map<String,Object>> getCustomerHistory(){
        List<Map<String,Object>> list_customer_history = new ArrayList<Map<String,Object>>();
        List<Booking_Master> list_booking = new ArrayList<Booking_Master>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {

        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            session.flush();
            session.close();
        }

        return list_customer_history;
    }


    public static List<Map<String,Object>> generateRequest(List<Booking_Request_Master> booking_request_masterList){
        List<Map<String,Object>> list_customer_request = new ArrayList<Map<String,Object>>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
           for(Booking_Request_Master booking_request_master : booking_request_masterList){
               Map<String,Object> map = new HashMap<String, Object>();
               Location_Master location_master;
               Pickup_Location_Master pickup_location_master ;
               map.put("destination_id",booking_request_master.getDestination_id());
               map.put("source_id",booking_request_master.getSource_id());
               map.put("departure_date",booking_request_master.getDept_date());
               map.put("child",booking_request_master.getChild());
               map.put("adult",booking_request_master.getAdult());
               map.put("number_of_seats",booking_request_master.getNumber_of_booking());
               map.put("departure_time",booking_request_master.getDept_time());
               map.put("id",booking_request_master.getId());
               map.put("status",booking_request_master.getStatus());
               map.put("provided_time",booking_request_master.getProvided_time());
               location_master = (Location_Master) session.load(Location_Master.class,booking_request_master.getFrom_id());
               if(location_master.getId() !=0){
                   map.put("source_name",location_master.getName());
               }else {
                   map.put("source_name","deleted");
               }
               location_master = (Location_Master) session.load(Location_Master.class,booking_request_master.getTo_id());
               if(location_master.getId() != 0){
                   map.put("destination_name",location_master.getName());
               }else {
                   map.put("destination_name","deleted");
               }


               pickup_location_master = (Pickup_Location_Master)
                       session.load(Pickup_Location_Master.class,booking_request_master.getSource_id());
               map.put("pickup_location",pickup_location_master.getName());
               pickup_location_master = (Pickup_Location_Master)
                       session.load(Pickup_Location_Master.class,booking_request_master.getDestination_id());
               map.put("drop_off_location",pickup_location_master.getName());
               list_customer_request.add(map);
           }
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            session.flush();
            session.close();
        }

        return list_customer_request;
    }
    public static List<Map<String,Object>> generateHistory(List<Booking_Master> booking_masterList){

        List<Map<String,Object>> list_customer_history = new ArrayList<Map<String,Object>>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            for(Booking_Master booking_master : booking_masterList){
                Map<String,Object> map = new HashMap<String, Object>();
                Location_Master location_master;
                Pickup_Location_Master pickup_location_master ;
                map.put("destination_id",booking_master.getDestination_id());
                map.put("source_id",booking_master.getSource_id());
                map.put("departure_date",booking_master.getDept_date());
                map.put("child",booking_master.getChild());
                map.put("adult",booking_master.getAdult());
                map.put("number_of_seats",booking_master.getNumber_booking());
                map.put("departure_time",booking_master.getDept_time());
                map.put("id",booking_master.getId());
                map.put("status",booking_master.getNotification());
                map.put("code",booking_master.getCode());


                location_master = (Location_Master) session.load(Location_Master.class,booking_master.getFrom_id());
                if(location_master.getId() !=0){
                    map.put("source_name",location_master.getName());
                }else {
                    map.put("source_name","deleted");
                }

                location_master = (Location_Master) session.load(Location_Master.class,booking_master.getTo_id());
                if(location_master.getId() != 0){
                    map.put("destination_name",location_master.getName());
                }else {
                    map.put("destination_name","deleted");
                }


                pickup_location_master = (Pickup_Location_Master)
                session.load(Pickup_Location_Master.class,booking_master.getSource_id());
                map.put("pickup_location",pickup_location_master.getName());
                pickup_location_master = (Pickup_Location_Master)
                        session.load(Pickup_Location_Master.class,booking_master.getDestination_id());
                map.put("drop_off_location",pickup_location_master.getName());

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
                if(booking_master.getQr()!=null){
                    map.put("qr_code",booking_master.getQr());
                }

                list_customer_history.add(map);
            }

        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            session.flush();
            session.close();
        }

        return list_customer_history;
    }

    public Map<String,Object> customerHistory(){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            String current = "From Booking_Master where dept_date >= current_date() and user_id="+id.getAuthentic();
            String history = "From Booking_Master where dept_date < current_date() and user_id="+id.getAuthentic();
            String request = "From Booking_Request_Master where " +
                    "dept_date >= current_date() and enabled='true' and user_id="+id.getAuthentic();
            Query query = session.createQuery(current);
            Query query1 = session.createQuery(history);
            Query query2 = session.createQuery(request);
            int maxHistory = 10 - query.list().size();
            if(maxHistory<0) maxHistory =0;
            query1.setMaxResults(maxHistory);
            List<Booking_Master> bookingMasterList = query.list();
            List<Booking_Master> bookingHistory = query1.list();
            List<Booking_Request_Master> booking_request_masterList = query2.list();

            map.put("current",generateHistory(bookingMasterList));
            map.put("history",generateHistory(bookingHistory));
            map.put("request",generateRequest(booking_request_masterList));
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            session.flush();
            session.close();
        }

        return map;
    }
    public Map<String,Object> cancel_ticket(ID_Class id_class){
        System.out.println("id: "+id_class.getId());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            Booking_Master booking =(Booking_Master) session.load(Booking_Master.class,id_class.getId());
            booking.setNotification("Cancelled");
            session.update(booking);
            User_Info user_info=(User_Info) session.load(User_Info.class,booking.getUser_id());
            user_info.setNumber_ticket(user_info.getNumber_ticket()+1);
            session.update(user_info);
            session.beginTransaction().commit();
            map.put("status",true);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        finally {
            session.flush();
            session.close();
        }

        return map;
    }
    public static void main(String arg[]){


    }

}

