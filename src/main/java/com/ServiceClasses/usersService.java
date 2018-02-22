package com.ServiceClasses;

import java.util.List;
import com.EntityClasses.Bus_Master;


public interface usersService {
	
	public int saveBus(Bus_Master bus);
	public List<Bus_Master> getAllBuses();
	public Bus_Master getBusById (int id);
	public int updateBus(Bus_Master bus);
}
