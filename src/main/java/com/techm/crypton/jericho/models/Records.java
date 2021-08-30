package com.techm.crypton.jericho.models;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "records")
public class Records {
	@Id
	private UUID id;
	private short year;
	private String period;
	private String entity;
	private int numberOfItems;
	private String createdBy;
	private Date createdOn;
	
	public Records(short year, String period,String entity,int numberOfItems) {
		this.id = UUID.randomUUID();
		this.year = year;
		this.period = period;
		this.entity = entity;
		this.numberOfItems = numberOfItems;
	}
	
	
	public Records(short year, String period, String entity, int numberOfItems, String createdBy) {
		this.id = UUID.randomUUID();
		this.year = year;
		this.period = period;
		this.entity = entity;
		this.numberOfItems = numberOfItems;
		this.createdBy = createdBy;
		this.createdOn = new Date();
	}

	public Records(UUID id, short year, String period, String entity, int numberOfItems, String createdBy,
			Date createdOn) {
		this.id = id;
		this.year = year;
		this.period = period;
		this.entity = entity;
		this.numberOfItems = numberOfItems;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}


	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public UUID getId() {
		return id;
	}
	public Records() {
		// TODO Auto-generated constructor stub
	}
	public short getYear() {
		return year;
	}
	public void setYear(short year) {
		this.year = year;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public int getNumberOfItems() {
		return numberOfItems;
	}
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}	
}
