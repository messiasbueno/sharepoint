package br.com.eive.sharepoint.controller.dto;

import java.time.LocalDateTime;

public class ProductDto {
	private Long id;
	private String name;
	private String notes;
	private LocalDateTime acquisitionDate;

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

	public LocalDateTime getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(LocalDateTime acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}
}
