package ilcarro.ilcarro.exceptions;

public class ErrorMessage {
	private String statusCode;
	private String statusDescription;

	public ErrorMessage() {
	}

	public ErrorMessage(String statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusDescription = statusMessage;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

}