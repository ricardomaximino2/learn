package com.example.demo.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonInclude(Include.NON_NULL)
public class Contact extends ResourceSupport{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long contactId;
	private String description;
	private String contact;
	
	public Contact() {}
	
	public Contact(String description, String contact) {
		super();
		this.description = description;
		this.contact = contact;
	}
	public long getContactId() {
		return contactId;
	}
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	@Override
	public String toString() {
		return "Contact [contactId=" + contactId + ", description=" + description + ", contact=" + contact + "]";
	}
}
