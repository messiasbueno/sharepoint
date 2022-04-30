package br.com.eive.sharepoint.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import br.com.eive.sharepoint.core.BaseEntity;
import br.com.eive.sharepoint.type.Tier;

@Entity
public class Customer extends BaseEntity{

	@Column(nullable = false, length = 255)
	private String name;

	@Column(length = 4000)
	private String notes;

	@Column(length = 4000)
	private String dbInfo;

	@Column(nullable = false)
	private Boolean usesVpn = false;

	private Boolean usesNewIntegration = false;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 1)
	private Tier tier;

	@Column(length = 36)
	private String segment;

	@Column(nullable = false, length = 80)
	private String responsible;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "customer")
	private List<Contact> contact = new ArrayList<>();

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "customer")
	private List<Product> product = new ArrayList<>();

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "customer")
	private List<Server> server = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String names) {
		this.name = names;
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

	public Tier getTier() {
		return tier;
	}

	public void setTier(Tier tier) {
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

	public List<Contact> getContact() {
		return contact;
	}

	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	public List<Server> getServer() {
		return server;
	}

	public void setServer(List<Server> server) {
		this.server = server;
	}

}
