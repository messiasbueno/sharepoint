package br.com.eive.sharepoint.controller.form;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ProductForm {
    @NotNull
	@NotEmpty
	@Length(min = 3, max = 255)
	private String name;

	@Length(max = 4000)
	private String notes;

	private LocalDateTime acquisitionDate;

	private Long customerId;

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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
}
