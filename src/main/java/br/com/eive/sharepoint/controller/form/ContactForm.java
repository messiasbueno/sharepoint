package br.com.eive.sharepoint.controller.form;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

public class ContactForm {
	@NotNull
	@NotEmpty
	@Length(min = 3, max = 255)
	private String name;

	@Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+).(.[a-z]{2,3})$|^$")
	private String email;
	
	@Pattern(regexp = "\\(?\\d{2}\\)?\\s?9?\\d{4}[\\-\\s]?\\d{4}|^$")
	private String phone;

	@Pattern(regexp = "\\(?\\d{2}\\)?\\s?9?\\d{4}[\\-\\s]?\\d{4}|^$")
	private String cellphone;

	@NotNull
	private Boolean main;

	@Positive
	@NotNull
	private Long customerId;

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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

}
