package com.ModelClasses;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Schedule_Model {
	private int id;
	private int bus_id;
	private int driver_id;
	private int destination_id;
	private int source_id;
	private String dept_date;
	private String dept_time;
	private String description;
	private String code;
	private int number_booking;
	private int remaining_seat;
	private int number_customer;
	private int number_staff;
	private int number_student;
	private String created_at;
	private String updated_at;
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBus_id() {
		return bus_id;
	}
	public void setBus_id(int bus_id) {
		this.bus_id = bus_id;
	}
	public int getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}
	public int getDestination_id() {
		return destination_id;
	}
	public void setDestination_id(int destination_id) {
		this.destination_id = destination_id;
	}
	public int getSource_id() {
		return source_id;
	}
	public void setSource_id(int source_id) {
		this.source_id = source_id;
	}
	public String getDept_date() {
		return dept_date;
	}
	public void setDept_date(String dept_date) {
		this.dept_date = dept_date;
	}
	public String getDept_time() {
		return dept_time;
	}
	public void setDept_time(String dept_time) {
		this.dept_time = dept_time;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getNumber_booking() {
		return number_booking;
	}
	public void setNumber_booking(int number_booking) {
		this.number_booking = number_booking;
	}
	public int getRemaining_seat() {
		return remaining_seat;
	}
	public void setRemaining_seat(int remaining_seat) {
		this.remaining_seat = remaining_seat;
	}
	public int getNumber_customer() {
		return number_customer;
	}
	public void setNumber_customer(int number_customer) {
		this.number_customer = number_customer;
	}
	public int getNumber_staff() {
		return number_staff;
	}
	public void setNumber_staff(int number_staff) {
		this.number_staff = number_staff;
	}
	public int getNumber_student() {
		return number_student;
	}
	public void setNumber_student(int number_student) {
		this.number_student = number_student;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	
	
}
