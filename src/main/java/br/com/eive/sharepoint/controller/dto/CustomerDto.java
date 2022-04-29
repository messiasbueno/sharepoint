package br.com.eive.sharepoint.controller.dto;

import org.springframework.stereotype.Component;

@Component
public class CustomerDto {
	private Long id;
	private String name;
	private String notes;
	private String dbInfo;
	private Boolean usesVpn;
	private Boolean usesNewIntegration;
	private String tier;
	private String segment;
	private String responsible;
	private ContactDto contact;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getDbInfo() {
		return dbInfo;
	}

	public void setDbInfo(String dbInfo) {
		this.dbInfo = dbInfo;
	}

	public Boolean getUsesVpn() {
		return usesVpn;
	}

	public void setUsesVpn(Boolean usesVpn) {
		this.usesVpn = usesVpn;
	}

	public Boolean getUsesNewIntegration() {
		return usesNewIntegration;
	}

	public void setUsesNewIntegration(Boolean usesNewIntegration) {
		this.usesNewIntegration = usesNewIntegration;
	}

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public ContactDto getContact() {
		return contact;
	}

	public void setContact(ContactDto contact) {
		this.contact = contact;
	}

}
