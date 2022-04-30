package br.com.eive.sharepoint.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.eive.sharepoint.core.BaseEntity;

@Entity
public class Product extends BaseEntity {

	@Column(nullable = false, length = 255)
	private String name;

	@Column(length = 4000)
	private String notes;

	private LocalDateTime acquisitionDate;

	@ManyToOne(optional = false)
	private Customer customer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(LocalDateTime acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
