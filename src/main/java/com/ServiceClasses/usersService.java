package com.ServiceClasses;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.EntityClasses.Batch_Master;
import com.EntityClasses.Bus_Master;
import com.EntityClasses.User_Info;


public interface usersService {
	
	public int saveBus(Bus_Master bus);
	public List<Bus_Master> getAllBuses();
	public Bus_Master getBusById (int id);
	public int updateBus(Bus_Master bus);
}
