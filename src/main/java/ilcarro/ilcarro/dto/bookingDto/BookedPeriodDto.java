package ilcarro.ilcarro.dto.bookingDto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import ilcarro.ilcarro.dto.Renter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookedPeriodDto {
	private Long order_id;
	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean paid;
	private Float amount;
	private LocalDate bookingDate;
	private Renter personWhoBooked;

	public BookedPeriodDto(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public BookedPeriodDto(long order_id, LocalDate startDate, LocalDate endDate, boolean paid, float amount,
			LocalDate bookingDate) {
		this.order_id = order_id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.paid = paid;
		this.amount = amount;
		this.bookingDate = bookingDate;
	}

	public BookedPeriodDto(long order_id, LocalDate startDate, LocalDate endDate, boolean paid, float amount,
			LocalDate bookingDate, Renter personWhoBooked) {
		this.order_id = order_id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.paid = paid;
		this.amount = amount;
		this.bookingDate = bookingDate;
		this.personWhoBooked = personWhoBooked;
	}

	public BookedPeriodDto() {
	}

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Renter getPersonWhoBooked() {
		return personWhoBooked;
	}

	public void setPersonWhoBooked(Renter personWhoBooked) {
		this.personWhoBooked = personWhoBooked;
	}

}
