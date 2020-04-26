package ilcarro.ilcarro.exceptions;

import org.springframework.http.HttpStatus;


public class IlcarroException extends Exception {
 
	private static final long serialVersionUID = -8535087843916335399L;
	private Integer errorCode;
	private String errorMessage;
	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

	public IlcarroException() {
	};

	public IlcarroException(Integer code, String errorMessage) {
		super(errorMessage);
		this.errorCode = code;
		this.errorMessage = errorMessage;
	}

	public IlcarroException(Integer code, String errorMessage, HttpStatus httpStatus) {
		super(errorMessage);
		this.errorCode = code;
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

	public Integer getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

//	public OneLoadException(Constants.Errors error, HttpStatus httpStatus) {
//		this(error.getCode(), error.getDescription(), httpStatus);
//	}
}