package br.com.eive.sharepoint.core.error;

public class ErrorFormDto {
	
	private String field;
	private String message;
	
	public ErrorFormDto(String campo, String erro) {
		this.field = campo;
		this.message = erro;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}
	
	

}
