package com.DaoClasses;


import java.util.List;
import com.EntityClasses.Bus_Master;
import com.EntityClasses.User_Info;


public interface usersDao {
	public User_Info findByUserName(String name);
	public int saveBus(Bus_Master bus);
	public List<Bus_Master> getAllBuses();
	public Bus_Master getBusById (int id);
	public int updateBus(Bus_Master bus);
	
	}
