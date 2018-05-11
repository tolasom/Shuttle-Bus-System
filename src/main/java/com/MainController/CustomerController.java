package com.MainController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ModelClasses.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.DaoClasses.Custom_Dao;
import com.DaoClasses.Custom_Imp;
import com.DaoClasses.Request_Booking;
import com.DaoClasses.Request_Booking_Dao;
import com.EntityClasses.Pickup_Location_Master;


@Controller
public class CustomerController {
	Custom_Dao customer=new Custom_Imp();
	Timestamp current_timestamp = new Timestamp(System.currentTimeMillis());
	
	//========================= Sign Up UI================================
	@RequestMapping(value="/sign_up")
	public ModelAndView sign_up() {
		return new ModelAndView("sign_up");
	}
	
	//========================= Sign Up UI================================
		
	//=========================check_booking_request Information================================
	@RequestMapping(value="/today", method=RequestMethod.GET)
	public @ResponseBody String today() {
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String today=sdf.format(cal.getTime());
	    return today;
	}
	//=========================User Information================================
	@RequestMapping(value="/user_info", method=RequestMethod.GET)
		public @ResponseBody Map<String,Object> user_info() {
		Map<String, Object> map = customer.user_info();
		System.out.println("Map User_Info:");
		System.out.println(map);
		return map;
	}
//	//=========================check_booking_request Information================================
//		@RequestMapping(value="/check_booking_request", method=RequestMethod.GET)
//			public @ResponseBody Map<String,Object> check_booking_request() {
//			Map<String, Object> map = customer.user_info();
//			return map;
//		}
	//=========================Location Information================================
		@RequestMapping(value="/location_data", method=RequestMethod.GET)
			public @ResponseBody Map<String, Map<String, List<Pickup_Location_Master>>> location1() {
			Map<String, Map<String, List<Pickup_Location_Master>>> map = customer.location();
			return map;
		}	
	//=========================Departure Time Information================================
		@RequestMapping(value="/departure_time_info", method=RequestMethod.GET)
			public @ResponseBody List<Map<String,Object>> departure_time_info() {
			List<Map<String, Object>> list = customer.departure_time_info();
			return list;
		}		
	//=========================Check Location Information================================
	@RequestMapping(value="/check_location", method=RequestMethod.GET)
		public @ResponseBody Map<String, Map<String, List<Pickup_Location_Master>>> check_location1(int id) {
		Map<String, Map<String, List<Pickup_Location_Master>>> map = customer.check_location(id);
		return map;
	}	
	//========================= Create customer pickup location ================================
	@RequestMapping(value="/create_custom_pickup_location", method=RequestMethod.GET)
	public @ResponseBody Map<String, Map<String, List<Pickup_Location_Master>>> create_custom_pickup_location(New_Pickup_Location np) {
			System.out.println(np.getLocation_id());
			Map<String, Map<String, List<Pickup_Location_Master>>> ret = customer.create_custom_pickup_location(np);
			return ret;
	}
	//========================= Create customer drop-off location ================================
	@RequestMapping(value="/create_custom_dropoff_location", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> create_custom_dropoff_location(New_Pickup_Location np) {
			System.out.println(np.getLocation_id());
			Map<String, Object> ret = customer.create_custom_dropoff_location(np);
			return ret;
	}
	//=========================Customer Booking Information================================

	@RequestMapping(value="/customer_booking", method=RequestMethod.POST)
	public @ResponseBody String customer_booking(@RequestBody Customer_Booking[] cb) throws ParseException {
			System.out.println("KKKKKKKKKKKKKKKKKKKKKKK: "+cb[0].getDate());
			for(int i=0;i<cb.length;i++){
				cb[i].setStatus("book");
			}
			String ret = customer.customer_booking(cb);
			return ret;

		}	
	
	//=========================confirm_phone_number================================
	@RequestMapping(value="/confirm_phone_number", method=RequestMethod.GET)
	public @ResponseBody String confirm_phone_number(UserModel cb) {
		String ret = customer.confirm_phone_number(cb);
		return ret;
	}
	//=========================Request Book Now================================
	@RequestMapping(value="/request_book_now", method=RequestMethod.POST)
	public @ResponseBody String request_book_now(@RequestBody ID_Class id_class) throws ParseException {
		Request_Booking_Dao req=new Request_Booking();
		String ret = req.request_book_now(id_class.getId());
		System.out.println(id_class.getId());
		return ret;
	}
	//=========================Customer Request Booking Information================================
	@RequestMapping(value="/customer_request_booking", method=RequestMethod.POST)
	public @ResponseBody String customer_request_booking(@RequestBody Customer_Booking cb) {
			String ret = customer.customer_request_booking(cb);
			return ret;
	}
	//=========================To get Booking History of User================================
	@RequestMapping(value="/booking_history")
	public ModelAndView booking_history() {
		return new ModelAndView("booking_history");
	}
	//=========================To Request Booking================================
	@RequestMapping(value="/request_booking")
	public ModelAndView request_booking() {
		return new ModelAndView("request_booking");
	}
	//=========================Customer Booking History Information================================
	@RequestMapping(value="/customer_booking_history", method=RequestMethod.GET)
		public @ResponseBody List<Map<String,Object>> customer_booking_history() {
		List<Map<String, Object>> map = customer.cusomer_booking_history();
		return map;
	}	
	//======================== Get Booking Request Information================================
		@RequestMapping(value="/get_request_booking", method=RequestMethod.GET)
		public @ResponseBody List<Map<String,Object>> get_request_booking() {
		List<Map<String, Object>> map = customer.get_request_booking();
        System.out.println(map);
		return map;
	}	
	//======================== Get Booking Request Information base id================================
	@RequestMapping(value="/get_request_booking_id", method=RequestMethod.GET)
		public @ResponseBody List<Map<String,Object>> get_request_booking_id(int id) {
		List<Map<String, Object>> map = customer.get_request_booking_id(id);
		return map;
	}	
	//======================== Get Sch Bus Information base id================================
		@RequestMapping(value="/get_sch_bus_info", method=RequestMethod.GET)
			public @ResponseBody List<Map<String,Object>> get_sch_bus_info(int id) {
			List<Map<String, Object>> map = customer.get_sch_bus_info(id);
			return map;
		}	
		//======================== Get Sch Driver Information base id================================
		@RequestMapping(value="/get_sch_driver_info", method=RequestMethod.GET)
			public @ResponseBody List<Map<String,Object>> get_sch_driver_info(int id) {
			List<Map<String, Object>> map = customer.get_sch_driver_info(id);
			return map;
		}	
	//=========================To Cancel Booking Ticket================================
	@RequestMapping(value="/cancel_booking_ticket", method=RequestMethod.POST)
		public @ResponseBody String cancel_booking_ticket(@RequestBody ID_Class id_delete) {

		String ret = customer.cancel_booking_ticket(id_delete.getId());
		return ret;
	}

	@RequestMapping(value="/customer/**")
	public ModelAndView customer_mobile() {
		return new ModelAndView("customer_mobile");
	}



	//=========================To Cancel Booking Ticket================================
	@RequestMapping(value="/get_qrcode", method=RequestMethod.GET)
		public @ResponseBody List<Map<String, Object>> get_qrcode(int id) {
		List<Map<String, Object>> ret = customer.get_qrcode(id);
		return ret;
	}

	@RequestMapping(value="/date_time")
	public String DateTime(){
		Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTimeString = sdf.format(d);
        System.out.println(currentDateTimeString);
        return currentDateTimeString;
	}
//	@Scheduled(cron="*/5 * * * * *")
//  public void updateEmployeeInventory(){
//      System.out.println("Started cron job 1");
//  }
}
