package com.EntityClasses;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Booking_Request_Master {
	private int id;
	private int destination_id;
	private int source_id;
	private int from_id;
	private int to_id;
	private Time dept_time;
	private Date dept_date;
	private int number_of_booking;
	private int child;
	private int adult;
	private String provided_date;
	private String provided_time;
	private String description;
	private String status;
	private int user_id;
	private Boolean enabled;
	private Timestamp created_at;
	private Timestamp updated_at;
	
	public String getProvided_time() {
		return provided_time;
	}
	public void setProvided_time(String provided_time) {
		this.provided_time = provided_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Time getDept_time() {
		return dept_time;
	}
	
	
	public int getFrom_id() {
		return from_id;
	}
	public void setFrom_id(int from_id) {
		this.from_id = from_id;
	}
	public int getTo_id() {
		return to_id;
	}
	public void setTo_id(int to_id) {
		this.to_id = to_id;
	}
	public int getNumber_of_booking() {
		return number_of_booking;
	}
	public void setNumber_of_booking(int number_of_booking) {
		this.number_of_booking = number_of_booking;
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
	public void setDept_time(Time dept_time) {
		this.dept_time = dept_time;
	}
	public Date getDept_date() {
		return dept_date;
	}
	public void setDept_date(Date dept_date) {
		this.dept_date = dept_date;
	}
	public String getProvided_date() {
		return provided_date;
	}
	public void setProvided_date(String provided_date) {
		this.provided_date = provided_date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	public int getChild() {
		return child;
	}
	public void setChild(int child) {
		this.child = child;
	}
	public int getAdult() {
		return adult;
	}
	public void setAdult(int adult) {
		this.adult = adult;
	}
	
}
