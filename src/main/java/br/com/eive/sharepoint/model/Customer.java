package br.com.eive.sharepoint.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String notes;
	private String dbInfo;
	private Boolean usesVpn;
	private Boolean usesNewIntegration;
	private String tier;
	private String segment;
	private String responsible;
	@OneToMany(mappedBy = "customer")
	private List<Contact> contact = new ArrayList<>();

	public Customer() {

	}

	public Customer(String name, String notes, String dbInfo, Boolean usesVpn, Boolean usesNewIntegration, String tier,
			String segment, String responsible) {
		this.name = name;
		this.notes = notes;
		this.dbInfo = dbInfo;
		this.usesVpn = usesVpn;
		this.usesNewIntegration = usesNewIntegration;
		this.tier = tier;
		this.segment = segment;
		this.responsible = responsible;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(id, other.id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public List<Contact> getContact() {
		return contact;
	}

	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}

}
