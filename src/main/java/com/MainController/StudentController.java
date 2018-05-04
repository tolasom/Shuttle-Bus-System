package com.MainController;
import org.springframework.stereotype.Controller;

@Controller
public class StudentController {
	//@Scheduled(cron="*/5 * * * * *")
	public void updateEmployeeInventory(){
      System.out.println("Started cron job 1");
	}
	
}
