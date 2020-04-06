package ilcarro.ilcarro.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.jsontype.impl.AsExistingPropertyTypeSerializer;

import ilcarro.ilcarro.api.ilCarroReturnCode;
import ilcarro.ilcarro.constant.IlCarroConstant;
import ilcarro.ilcarro.dto.Comment;
import ilcarro.ilcarro.dto.bookingDto.BookedPeriodDto;
import ilcarro.ilcarro.dto.carDto.CarRequestDto;
import ilcarro.ilcarro.dto.carDto.CarResponseDto;
import ilcarro.ilcarro.dto.carDto.CarResponseOwnerDto;
import ilcarro.ilcarro.dto.commentDto.CommentRequestDto;
import ilcarro.ilcarro.dto.reservationDto.ReservationRequestDto;
import ilcarro.ilcarro.dto.reservationDto.ReservationResponseDto;
import ilcarro.ilcarro.dto.userDto.UserRequestDto;
import ilcarro.ilcarro.dto.userDto.UserResponseDto;
import ilcarro.ilcarro.entities.ResponseModel;
import ilcarro.ilcarro.exceptions.ErrorMessage;
import ilcarro.ilcarro.exceptions.IlcarroException;
import ilcarro.ilcarro.service.ilCarroService;

@RestController
@RequestMapping("/api")
public class ilCarroController {

	private static final Logger log = LoggerFactory.getLogger(ilCarroController.class);

	@Autowired
	private ilCarroService ilCarroService;

	private String getEmail(String authHeader) {
		if (authHeader != null) {
			return authHeader;
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	// For my testing, if everything goes well then it should run, remove if not
	// required.
	@GetMapping("/helloword")
	public String helloword() {
		return "helloword";
	}

	@PostMapping("/registration")
	public ResponseModel registerUser(@RequestBody UserRequestDto userRequestDto) {

		ResponseModel response = new ResponseModel();
		List<Object> dataList = new ArrayList<Object>();
		UserResponseDto userResponse = ilCarroService.registerUser(userRequestDto);
		if (userResponse == null) {
			response.setStatus(IlCarroConstant.CONFLICT_CODE);
			dataList.add(userResponse);
			response.setData(dataList);
			return response;
		} else {
			response.setStatus(IlCarroConstant.SUCCESS_CODE);
			dataList.add(userResponse);
			response.setData(dataList);
			return response;
		}

	}

	@PutMapping("/user")
	public UserResponseDto updateUser(Authentication authentication, @RequestBody UserRequestDto userRequestDto) {
		return ilCarroService.updateUser(authentication.getName(), userRequestDto);
	}

	@DeleteMapping("/user/{email}")
	public ilCarroReturnCode deleteUser(@PathVariable("email") String email, Authentication authentication) {
//		System.out.println(authentication.getName());
		if (email.equals(authentication.getName())) {
			ilCarroService.deleteUser(email);
			return ilCarroReturnCode.OK;
		} else {
			return ilCarroReturnCode.UNAUTHORIZED;
		}

	}

	@PostMapping("/car")
	public CarResponseOwnerDto addCar(@RequestBody CarRequestDto carRequestDto, Authentication authentication) {
		return ilCarroService.addCar(carRequestDto, authentication.getName());
	}

	@GetMapping("/user/cars/periods")
	public List<BookedPeriodDto> ownerGetCarBookedPeriods(Authentication authentication,
			@RequestParam("serialNumber") String serialNumber) {
		return ilCarroService.getOwnerCarBookedPeriods(authentication.getName(), serialNumber);
	}

	@GetMapping("/user/cars/")
	public List<CarResponseDto> getOwnerCarsByEmail(Authentication authentication) {
		return ilCarroService.getOwnerCarsById(authentication.getName());
	}

	@GetMapping("/user/cars/car") // owner get car by id
	public CarResponseDto getOwnerCarById(Authentication authentication, @RequestParam String serialNumber) {
		return ilCarroService.getOwnerCarById(authentication.getName(), serialNumber);
	}

	@PutMapping("/car/update")
	public CarResponseOwnerDto updateCar(Authentication authentication, @RequestBody CarRequestDto carRequestDto) {
		return ilCarroService.updateCar(authentication.getName(), carRequestDto);
	}

	@GetMapping("/car/{serialNumber}")
	public CarResponseOwnerDto getCar(@PathVariable String serialNumber) {
		return ilCarroService.getCar(serialNumber);
	}

	@DeleteMapping("/car/delete")
	public void deleteCar(Authentication authentication, @RequestParam String serialNumber) {
		ilCarroService.deleteCar(authentication.getName(), serialNumber);
	}

	@GetMapping("/comments")
	public List<Comment> getComments() {
		return ilCarroService.getComments();
	}

	@PostMapping("/comment/add")
	public Comment addComment(@RequestHeader(value = "email") String authHeader,
			@RequestParam("serialNumber") String serialNumber, @RequestBody CommentRequestDto commentRequestDto) {
		return ilCarroService.addComment(serialNumber, getEmail(authHeader), commentRequestDto);
	}

	@PostMapping("/car/reservation/")
	public ReservationResponseDto makeReservation(@RequestHeader(value = "email") String authHeader,
			@RequestParam("serialNumber") String serialNumber, @RequestBody ReservationRequestDto requestDto) {
		return ilCarroService.makeReservation(getEmail(authHeader), serialNumber, requestDto);

	}

	@GetMapping("/car/best")
	public List<UserResponseDto> getBestCars() {
		return ilCarroService.getThreePopularCars();
	}

	@GetMapping("/car/comments/latest")
	public List<Comment> getLatestComments() {
		return ilCarroService.comments();
	}

	@GetMapping("/car/best/cars/")
	public List<BookedPeriodDto> getBest() {
		return ilCarroService.findBestCars();
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<?> serachCarAgainstBookedPeriod(@RequestParam(name = "city", defaultValue = "") String city,
			@RequestParam(name = "startDate", required = true) String startDate,
			@RequestParam(name = "endDate", required = true) String endDate,
			@RequestParam(name = "minAmount", defaultValue = "0") double minAmount,
			@RequestParam(name = "maxAmount", defaultValue = "0") double maxAmount,
			@RequestParam(name = "ascending", defaultValue = "true") boolean ascending,
			@RequestParam(name = "itemOnPage", defaultValue = "10") int itemOnPage,
			@RequestParam(name = "currentPage", defaultValue = "1") int currentPage) throws IlcarroException {
		ResponseEntity<?> responseEntity;
		try {
			Page<CarResponseDto> dataList = ilCarroService.searchCarAgainstBookedPeriod(city, startDate, endDate,
					minAmount, maxAmount, ascending, itemOnPage, currentPage);
			responseEntity = new ResponseEntity<>(dataList, HttpStatus.OK);
		} catch (IlcarroException e) {
			log.error(e.getMessage(), e);
			responseEntity = new ResponseEntity<>(
					new ErrorMessage(String.valueOf(e.getErrorCode()), e.getErrorMessage()), e.getHttpStatus());
		}
		return responseEntity;

	}

	@RequestMapping(value = "/search/geo", method = RequestMethod.GET)
	public ResponseEntity<?> serachCarbyCoordinates(@RequestParam(name = "latitude", defaultValue = "0") float latitude,
			@RequestParam(name = "longitude") float longitude, @RequestParam(name = "radius") float radius,
			@RequestParam(name = "itemOnPage", defaultValue = "0") int itemOnPage,
			@RequestParam(name = "currentPage", defaultValue = "0") int currentPage) throws IlcarroException {
		ResponseEntity<?> responseEntity;
		try {
			List<CarResponseDto> dataList = ilCarroService.searchCarsByCoordinates(latitude, longitude, radius,
					itemOnPage, currentPage);
			responseEntity = new ResponseEntity<>(dataList, HttpStatus.OK);
		} catch (IlcarroException e) {
			log.error(e.getMessage(), e);
			responseEntity = new ResponseEntity<>(
					new ErrorMessage(String.valueOf(e.getErrorCode()), e.getErrorMessage()), e.getHttpStatus());
		}
		return responseEntity;

	}

	@RequestMapping(value = "/search/filter", method = RequestMethod.GET)
	public ResponseEntity<?> serachCarbyfilters(@RequestParam(name = "make", defaultValue = "0") String make,
			@RequestParam(name = "model") String model, @RequestParam(name = "year") String year,
			@RequestParam(name = "engine", defaultValue = "0") String engine,
			@RequestParam(name = "fuel", defaultValue = "0") String fuel,
			@RequestParam(name = "gear", defaultValue = "0") String gear,
			@RequestParam(name = "wheels_drive", defaultValue = "0") String wheelDrive,
			@RequestParam(name = "itemOnPage", defaultValue = "0") int itemOnPage,
			@RequestParam(name = "currentPage", defaultValue = "0") int currentPage,
			@RequestParam(name = "ascending", defaultValue = "true") Boolean ascending ) throws IlcarroException {
		ResponseEntity<?> responseEntity;
		try {
			List<CarResponseDto>  dataList = ilCarroService.searchCarsbyFilter(make, model, year, engine, fuel, gear,
					wheelDrive, itemOnPage, currentPage, ascending);
			responseEntity = new ResponseEntity<>(dataList, HttpStatus.OK);
		} catch (IlcarroException e) {
			log.error(e.getMessage(), e);
			responseEntity = new ResponseEntity<>(
					new ErrorMessage(String.valueOf(e.getErrorCode()), e.getErrorMessage()), e.getHttpStatus());
		}
		return responseEntity;

	}

}
