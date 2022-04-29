package br.com.eive.sharepoint.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.eive.sharepoint.model.Contact;

public class ContactDto {
	private String name;
	private String email;
	private String phone;
	private String cellphone;
	private Boolean main;

	public ContactDto() {
		
	}

	public ContactDto(Contact contact) {
		this.name = contact.getName();
		this.email = contact.getEmail();
		this.phone = contact.getPhone();
		this.cellphone = contact.getCellphone();
		this.main = contact.getMain();
	}

	public List<ContactDto> convet(List<Contact> contact) {
		return contact.stream().map(ContactDto::new).collect(Collectors.toList());

	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public Boolean getMain() {
		return main;
	}
}
