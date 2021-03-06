package br.com.eive.sharepoint.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.eive.sharepoint.core.BaseEntity;

@Entity
public class Contact extends BaseEntity {

	@Column(nullable = false, length = 255)
	private String name;

	private String email;

	private String phone;

	private String cellphone;

	private Boolean main;

	@ManyToOne(optional = false)
	private Customer customer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public Boolean getMain() {
		return main;
	}

	public void setMain(Boolean main) {
		this.main = main;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
