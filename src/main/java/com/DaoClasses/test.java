package com.DaoClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.EntityClasses.Schedule_Master;


public class test {
	
	public static void main(String args[])
	{
		List<Map<String,Object>> all_bus =new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> new_bus =new ArrayList<Map<String,Object>>();
        List<Schedule_Master> schedules=new ArrayList<Schedule_Master>();
        
        Schedule_Master s1 = new Schedule_Master();
        s1.setBus_id(1);
        schedules.add(s1);
        
        Map<String,Object> bus_1 = new HashMap<String,Object>();
        Map<String,Object> bus_2 = new HashMap<String,Object>();
        Map<String,Object> bus_3 = new HashMap<String,Object>();
        
        bus_1.put("bus_model", "Ssamyong");
        bus_1.put("number_of_seat", 11);
        bus_1.put("id", 1);
        
        bus_2.put("bus_model", "Hyundai");
        bus_2.put("number_of_seat", 24);
        bus_2.put("id", 2);
        
        bus_3.put("bus_model", "S3");
        bus_3.put("number_of_seat", 10);
        bus_3.put("id", 3);
        
        all_bus.add(bus_1);
        all_bus.add(bus_2);
        all_bus.add(bus_3);
        
        for(int i=0;i<all_bus.size();i++){
        	Boolean check=true;
        	for(int j=0;j<schedules.size();j++){
        		if(all_bus.get(i).get("id").equals(schedules.get(j).getId())){
        			check=false;
        			break;
        		}
        	}
        	if(check){
        		new_bus.add(all_bus.get(i));
        	}
        }
        
        System.out.println("JJJJJ "+new_bus.size());

        
        
        
	}
}


