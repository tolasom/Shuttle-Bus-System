package com.ServiceClasses;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DaoClasses.usersDao;
import com.EntityClasses.Batch_Master;
import com.EntityClasses.Bus_Master;



@Service
public class usersServiceImpl implements usersService{

	@Autowired
	usersDao usersDao1;

	public int saveBus(Bus_Master bus) {
		return usersDao1.saveBus(bus);
	}
	public List<Bus_Master> getAllBuses(){
		return usersDao1.getAllBuses();
	}
	public Bus_Master getBusById (int id){
		return usersDao1.getBusById(id);
	}
	public int updateBus(Bus_Master bus){
		return usersDao1.updateBus(bus);
	}
	
	
	
}


