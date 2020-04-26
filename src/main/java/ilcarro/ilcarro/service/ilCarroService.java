package ilcarro.ilcarro.service;

import java.util.List;

import org.springframework.data.domain.Page;

import ilcarro.ilcarro.api.ilCarroReturnCode;
import ilcarro.ilcarro.dto.Comment;
import ilcarro.ilcarro.dto.bookingDto.BookedPeriodDto;
import ilcarro.ilcarro.dto.carDto.CarRequestDto;
import ilcarro.ilcarro.dto.carDto.CarResponseDto;
import ilcarro.ilcarro.dto.carDto.CarResponseOwnerDto;
import ilcarro.ilcarro.dto.commentDto.CommentRequestDto;
import ilcarro.ilcarro.dto.reservationDto.ConfirmReservationRequestDto;
import ilcarro.ilcarro.dto.reservationDto.ReservationRequestDto;
import ilcarro.ilcarro.dto.reservationDto.ReservationResponseDto;
import ilcarro.ilcarro.dto.userDto.UserRequestDto;
import ilcarro.ilcarro.dto.userDto.UserResponseDto;
import ilcarro.ilcarro.exceptions.IlcarroException;

public interface ilCarroService {
	UserResponseDto registerUser(UserRequestDto userRequestDto);

	UserResponseDto updateUser(String email, UserRequestDto userRequestDto);

	ilCarroReturnCode deleteUser(String email);

	CarResponseOwnerDto addCar(CarRequestDto carRequestDto, String email);

	Comment addComment(String serialNumber, String email, CommentRequestDto commentRequestDto);

	CarResponseDto getOwnerCarById(String email, String serialNumber);

	List<CarResponseDto> getOwnerCarsById(String email);

	CarResponseOwnerDto updateCar(String ownerEmail, CarRequestDto carRequestDto);

	List<BookedPeriodDto> getOwnerCarBookedPeriods(String email, String serialNumber);

	CarResponseOwnerDto getCar(String serialNumber);

	UserResponseDto getUser(String email);

	void deleteCar(String email, String serialNumber);

	List<Comment> getComments();

	Comment getComment();

	ReservationResponseDto makeReservation(String email, String serialNumber,
			ReservationRequestDto reservationRequestDto);

	void confirmReservation(ConfirmReservationRequestDto requestDto);

	List<UserResponseDto> getThreePopularCars();

	List<Comment> comments();

	List<BookedPeriodDto> findBestCars();

	List<CarResponseOwnerDto> findByBookedPeriod(String city, String startDate, String endDate, String minAmount,
			String maxAmount, String ascending, String itemOnPage, String currentPage);

	Page<CarResponseDto> searchCarAgainstBookedPeriod(String city, String startDate, String endDate, double minAmount,
			double maxAmount, boolean ascending, int itemOnPage, int currentPage) throws IlcarroException;

	List<CarResponseDto> searchCarsByCoordinates(float latitude, float longitude, float radius, int itemOnPage,
			int currentPage) throws IlcarroException;

	List<CarResponseDto> searchCarsbyFilter(String make, String model, String year, String engine, String fuel,
			String gear, String wheelDrive, int itemOnPage, int currentPage, Boolean ascending) throws IlcarroException;

}
