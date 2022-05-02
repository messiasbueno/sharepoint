package br.com.eive.sharepoint.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import br.com.eive.sharepoint.type.Tier;

public class CustomerForm {
	@NotNull
	@NotEmpty
	@Length(min = 3, max = 255)
	private String name;

	@Length(max = 4000)
	private String notes;

	@Length(max = 4000)
	private String dbInfo;

	@NotNull
	private Boolean usesVpn;

	private Boolean usesNewIntegration;

	@NotNull
	@Pattern(regexp = "^[ABC]$", message = "deve corresponder a A, B ou C")
	private String tier;

	@Length(max = 36)
	private String segment;

	@NotNull
	@NotEmpty
	@Length(min = 3, max = 80)
	private String responsible;

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

	public Tier getTier() {
		return Tier.valueOf(tier);
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
