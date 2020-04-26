package ilcarro.ilcarro.dto.reservationDto;

public class ConfirmReservationRequestDto {

	private Long orderId;
	private String confirmationCode;

	public ConfirmReservationRequestDto() {
		// TODO Auto-generated constructor stub
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

}
