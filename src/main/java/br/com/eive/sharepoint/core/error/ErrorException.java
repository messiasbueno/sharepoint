package br.com.eive.sharepoint.core.error;

public class ErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1702958699456776924L;
	
	private String field;

    public ErrorException() {
    }
    
    public ErrorException(String field, String message) {
        super(message);
        this.field = field;
    }
    
    public String getField () {
    	return this.field;
    }

}
