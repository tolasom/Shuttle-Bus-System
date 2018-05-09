package com.DaoClasses;

import com.EntityClasses.Booking_Master;
import com.ModelClasses.Student_Booking;

import java.util.List;
import java.util.Map;

public interface StudentDao {
    public Map<String,Object> student_info();
    public List<Map<String,Object>> location_info();
    public Map<String,Object> student_booking(Student_Booking book_data);
    public List<Object> list_booking_date();
    public List<Map<String,Object>>getHistory();
    public Map<String,Object> customerHistory();
}
