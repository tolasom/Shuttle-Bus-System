package com.MainController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.DaoClasses.StudentDao;
import com.DaoClasses.StudentDaoImpl;
import com.ModelClasses.Student_Booking;

@Controller
public class StudentController {
    StudentDao studentDao = new StudentDaoImpl();

    @RequestMapping(value = "/student/**")
    public String customer_home() {
        return "student/home";
    }

    @RequestMapping(value = "/student_info", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> studentInfo() {
        return studentDao.student_info();
    }

    @RequestMapping(value = "/location_info", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> location_Info() {
        return studentDao.location_info();
    }

    @RequestMapping(value = "/student_booking", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> student_booking(@RequestBody Student_Booking book_data) {
        return studentDao.student_booking(book_data);
    }


    @RequestMapping(value = "/list_booking_date", method = RequestMethod.GET)
    @ResponseBody public List<Object> list_booking_date() {
        return studentDao.list_booking_date();
    }


    @RequestMapping(value = "/student_history", method = RequestMethod.GET)
    @ResponseBody public List<Map<String, Object>> getHistory() {
        return studentDao.getHistory();
    }

    @RequestMapping(value = "/customer_history_all", method = RequestMethod.GET)
    @ResponseBody public Map<String,Object> customerHistory(){ return studentDao.customerHistory(); }


    @RequestMapping(value = "/list_enable", method = RequestMethod.GET)
    @ResponseBody public List<Integer> listBookedDate(){ return studentDao.listBookedDate(); }

    //@Scheduled(cron="*/5 * * * * *")
    public void updateEmployeeInventory() {
        System.out.println("Started cron job 1");
    }

}

