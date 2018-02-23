package com.MainController;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.DaoClasses.Custom_Dao;
import com.DaoClasses.Custom_Imp;
import com.EntityClasses.Pickup_Location_Master;
import com.ModelClasses.Customer_Booking;


@Controller
public class CustomerController {
	Custom_Dao customer=new Custom_Imp();
	
	//=========================User Information================================
	@RequestMapping(value="/user_info", method=RequestMethod.GET)
		public @ResponseBody Map<Object, String> user_info() {
		Map<Object, String> map = customer.user_info();
		return map;
	}
	//=========================check_booking_request Information================================
		@RequestMapping(value="/check_booking_request", method=RequestMethod.GET)
			public @ResponseBody Map<Object, String> check_booking_request() {
			Map<Object, String> map = customer.user_info();
			return map;
		}
	//=========================Location Information================================
		@RequestMapping(value="/location_data", method=RequestMethod.GET)
			public @ResponseBody Map<String, Map<String, List<Pickup_Location_Master>>> location1() {
			Map<String, Map<String, List<Pickup_Location_Master>>> map = customer.location();
			return map;
		}	
	//=========================Departure Time Information================================
		@RequestMapping(value="/departure_time_info", method=RequestMethod.GET)
			public @ResponseBody List<Map<Object, String>> departure_time_info() {
			List<Map<Object, String>> list = customer.departure_time_info();
			return list;
		}		
	//=========================Check Location Information================================
	@RequestMapping(value="/check_location", method=RequestMethod.GET)
		public @ResponseBody Map<String, Map<String, List<Pickup_Location_Master>>> check_location1(int id) {
		Map<String, Map<String, List<Pickup_Location_Master>>> map = customer.check_location(id);
		return map;
	}	
	//=========================Customer Booking Information================================
	@RequestMapping(value="/customer_booking", method=RequestMethod.GET)
	public @ResponseBody String customer_booking(Customer_Booking cb) {
			String ret = customer.customer_booking(cb);
			return ret;
		}	
	//=========================Customer Request Booking Information================================
		@RequestMapping(value="/customer_request_booking", method=RequestMethod.GET)
		public @ResponseBody String customer_request_booking(Customer_Booking cb) {
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
			public @ResponseBody List<Map<Object, String>> customer_booking_history() {
			List<Map<Object, String>> map = customer.cusomer_booking_history();
			return map;
		}	
	//======================== Get Booking Request Information================================
		@RequestMapping(value="/get_request_booking", method=RequestMethod.GET)
		public @ResponseBody List<Map<Object, String>> get_request_booking() {
		List<Map<Object, String>> map = customer.get_request_booking();
		return map;
	}	
}
