package com.EntityClasses;

import java.sql.Timestamp;
import java.util.Date;

public class Batch_Master {
		private int id;
		private String name;
		private Date date_of_leaving;
		private Date date_of_returning;
		private Date deadline_booking;
		private Timestamp created_at;
		private Timestamp updated_at;
		private boolean enabled;
		
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Date getDate_of_leaving() {
			return date_of_leaving;
		}
		public void setDate_of_leaving(Date date_of_leaving) {
			this.date_of_leaving = date_of_leaving;
		}
		public Date getDate_of_returning() {
			return date_of_returning;
		}
		public void setDate_of_returning(Date date_of_returning) {
			this.date_of_returning = date_of_returning;
		}
		public Date getDeadline_booking() {
			return deadline_booking;
		}
		public void setDeadline_booking(Date deadline_booking) {
			this.deadline_booking = deadline_booking;
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
		public boolean isEnabled() {
			return enabled;
		}
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		
		
}
