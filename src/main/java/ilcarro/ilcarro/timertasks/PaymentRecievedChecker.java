package ilcarro.ilcarro.timertasks;

import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ilcarro.ilcarro.entities.BookedCarMongo;
import ilcarro.ilcarro.entities.CarMongo;
import ilcarro.ilcarro.entities.OwnerBookedPeriod;
import ilcarro.ilcarro.entities.UserMongo;
import ilcarro.ilcarro.exceptions.errors.UserNotFoundException;
import ilcarro.ilcarro.repository.ilCarroRepository;

public class PaymentRecievedChecker extends TimerTask {

	private static final Logger logger = LoggerFactory.getLogger(PaymentRecievedChecker.class);

	private Long orderId;
	private String serialNumber;
	private String ownerEmail;
	private String renterEmail;
	Map<Long, String> confCodeMap;

	private ilCarroRepository ilCarroRepository;

	public PaymentRecievedChecker(Long orderId, String serialNumber, String ownerEmail, String renterEmail,
			ilCarroRepository ilCarroRepository, Map<Long, String> confCodeMap) {

		this.orderId = orderId;
		this.serialNumber = serialNumber;
		this.ownerEmail = ownerEmail;
		this.renterEmail = renterEmail;
		this.ilCarroRepository = ilCarroRepository;
		this.confCodeMap = confCodeMap;
	}

	@Override
	public void run() {

		UserMongo owner = ilCarroRepository.findById(ownerEmail)
				.orElseThrow(() -> new UserNotFoundException("No User Found"));

		CarMongo car = owner.getOwnCars().stream().filter(c -> c.getSerialNumber().equals(serialNumber)).findAny()
				.orElse(null);

		OwnerBookedPeriod bookedPeriod = car.getBookedPeriod().stream().filter(p -> p.getOrderId() == orderId).findAny()
				.orElse(null);

		UserMongo renter = ilCarroRepository.findById(renterEmail)
				.orElseThrow(() -> new UserNotFoundException("No Renter User Found"));

		if (!bookedPeriod.isPaid()) {

			car.getBookedPeriod().remove(bookedPeriod);
			BookedCarMongo bookedCarMongo = renter.getBookedCars().stream()
					.filter(c -> c.getBookedPeriod().getOrderId() == orderId).findFirst().orElse(null);
			renter.getBookedCars().remove(bookedCarMongo);
			ilCarroRepository.save(owner);
			ilCarroRepository.save(renter);
			confCodeMap.remove(orderId);
		}
	}

}
