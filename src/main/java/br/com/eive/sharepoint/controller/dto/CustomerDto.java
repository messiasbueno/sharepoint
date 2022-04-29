package br.com.eive.sharepoint.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.eive.sharepoint.model.Customer;

public class CustomerDto {
	private String name;
	private String notes;
	private String dbInfo;
	private Boolean usesVpn;
	private Boolean usesNewIntegration;
	private String tier;
	private String segment;
	private String responsible;

	public CustomerDto(Customer customer) {
		this.name = customer.getName();
		this.notes = customer.getNotes();
		this.dbInfo = customer.getDbInfo();
		this.usesVpn = customer.getUsesVpn();
		this.usesNewIntegration = customer.getUsesNewIntegration();
		this.tier = customer.getTier();
		this.segment = customer.getSegment();
		this.responsible = customer.getResponsible();
	}

	public List<CustomerDto> convet(List<Customer> customer) {
		return customer.stream().map(CustomerDto::new).collect(Collectors.toList());

	}

	public String getName() {
		return name;
	}

	public String getNotes() {
		return notes;
	}

	public String getDbInfo() {
		return dbInfo;
	}

	public Boolean getUsesVpn() {
		return usesVpn;
	}

	public Boolean getUsesNewIntegration() {
		return usesNewIntegration;
	}

	public String getTier() {
		return tier;
	}

	public String getSegment() {
		return segment;
	}

	public String getResponsible() {
		return responsible;
	}

}
