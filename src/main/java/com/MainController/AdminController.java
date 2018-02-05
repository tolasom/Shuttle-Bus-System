package com.MainController;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.EntityClasses.Bus_Master;
import com.ServiceClasses.usersService;

@Controller
public class AdminController {
	@Autowired
	usersService usersService1;
//=========================Returns bus management view================================
	@RequestMapping(value="/bus_management", method=RequestMethod.GET)
	public ModelAndView viewProject() {
		return new ModelAndView("bus_management");
	}
//=========================Returns bus update view================================
	@RequestMapping(value="/bus_update", method=RequestMethod.GET)
	public ModelAndView bus_update(@RequestParam(value = "id", required=true, defaultValue = "0") Integer id) {
		Bus_Master bus = usersService1.getBusById(id);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bus", bus);
		ObjectMapper mapper = new ObjectMapper();
		String json="";
		try {
			json = mapper.writeValueAsString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("bus_update","data",json);
	}
	
	
//====================To save bus============================
	@RequestMapping(value="/createBus", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> toSaveProject(Bus_Master bus) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		int check = usersService1.saveBus(bus);
		if(check==0)
		{
			map.put("status","0");
			map.put("message","Plate number already existed!");
		}
		else if(check==1)
		{
			map.put("status","1");
			map.put("message","Bus have just been created successfully");
		}
		else
		{
			map.put("status","5");
			map.put("message","Technical problem occurs");
		}
		return map;
		}
	
//====================To update bus============================
		@RequestMapping(value="/updateBus", method=RequestMethod.GET)
		public @ResponseBody Map<String,Object> toUpdateBus(Bus_Master bus) throws Exception{
			Map<String,Object> map = new HashMap<String,Object>();
			int check = usersService1.updateBus(bus);
			if(check==0)
			{
				map.put("status","0");
				map.put("message","Plate number already existed!");
			}
			else if(check==1)
			{
				map.put("status","1");
				map.put("message","Bus have just been updated successfully");
			}
			else
			{
				map.put("status","5");
				map.put("message","Technical problem occurs");
			}
			return map;
			}
	@RequestMapping(value="/getAllBuses", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getProjectCategoryList(){
				
		 Map<String,Object> map = new HashMap<String,Object>();
	
		   // DaoClasses.userDaoImpl dao = new DaoClasses.userDaoImpl();
			List<Bus_Master> list = usersService1.getAllBuses();
			 		
			if (list != null)
				map.put("buses", list);
			else
				map.put("message","Data not found");			
			
			return map;
	}
}
