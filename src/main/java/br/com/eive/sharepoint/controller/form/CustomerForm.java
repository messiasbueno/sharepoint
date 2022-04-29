package br.com.eive.sharepoint.controller.form;

import br.com.eive.sharepoint.model.Customer;

public class CustomerForm {
	private String name;
	private String notes;
	private String dbInfo;
	private Boolean usesVpn;
	private Boolean usesNewIntegration;
	private String tier;
	private String segment;
	private String responsible;

	public Customer convert() {
		return new Customer(name, notes, dbInfo, usesVpn, usesNewIntegration, tier, segment, responsible);
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
}
