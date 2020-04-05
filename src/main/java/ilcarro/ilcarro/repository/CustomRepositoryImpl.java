package ilcarro.ilcarro.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ilcarro.ilcarro.dto.Comment;
import ilcarro.ilcarro.dto.carDto.CarResponseDto;
import ilcarro.ilcarro.entities.UserMongo;

public class CustomRepositoryImpl implements CustomRepository {
	MongoTemplate mongoTemplate;

	@Autowired
	public CustomRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<UserMongo> getThreePopularCars() {
		Query query = new Query();
		query.addCriteria(Criteria.where("ownCars.bookedPeriod").gte(10));
		query.with(Sort.by(Sort.Order.desc("ownCars.bookedPeriod"))).limit(3);
		return mongoTemplate.find(query, UserMongo.class);
	}

	@Override
	public List<Comment> getLatestComments() {
		Query query = new Query();
		query.addCriteria(Criteria.where("comments.firstName").is("nir"));
		// query.with(Sort.by(Sort.Order.desc("firstName"))).limit(6);
		return mongoTemplate.find(query, Comment.class);
	}

//	@Override
//	public List<UserMongo> searchCarAgainstBookedPeriod(String city, String startDate, String endDate, double minAmount,
//			double maxAmount, boolean ascending, int itemOnPage, int currentPage) throws ParseException {
//
//		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
//
//		Query query = new Query();
//
//		Criteria dateCriteria = new Criteria().andOperator(
//				Criteria.where("ownCars.bookedPeriod.startDate").gte(sdf.parse(startDate)),
//				Criteria.where("ownCars.bookedPeriod.endDate").lte(sdf.parse(endDate)));
//
//		Criteria amountCriteria = Criteria.where("bookedPeriod.amount").lt(maxAmount).gt(minAmount);
//		query.addCriteria(Criteria.where("ownCars").elemMatch(new Criteria().orOperator(dateCriteria, amountCriteria,
//				Criteria.where("pickUpPlace.placeId").is(city))));
//		query.fields().include("firstName").include("secondName").include("registrationDate").include("ownCars.$");
//
//		final Pageable pageableRequest = PageRequest.of(currentPage - 1, itemOnPage);
//		query.with(pageableRequest);
//
//		return mongoTemplate.find(query, UserMongo.class);
//
//	}

	@Override
	public List<CarResponseDto> searchCarAgainstBookedPeriod(String city, String startDate, String endDate,
			double minAmount, double maxAmount, boolean ascending, int itemOnPage, int currentPage)
			throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

		UnwindOperation unwindOperation = Aggregation.unwind("ownCars");

		Criteria dateCriteria = new Criteria().andOperator(
				Criteria.where("ownCars.bookedPeriod.startDate").gte(sdf.parse(startDate)),
				Criteria.where("ownCars.bookedPeriod.endDate").lte(sdf.parse(endDate)));

		Criteria amountCriteria = Criteria.where("bookedPeriod.amount").lt(maxAmount).gt(minAmount);

		MatchOperation matchOperation = Aggregation.match(new Criteria()
				.orOperator(Criteria.where("ownCars.pickUpPlace.placeId").is(city), dateCriteria, amountCriteria));

		ProjectionOperation projectOperation = Aggregation
				.project("$ownCars.serialNumber", "$ownCars.make", "$ownCars.model", "$ownCars.year", "$ownCars.engine",
						"$ownCars.fuel", "$ownCars.gear", "$ownCars.wheelsDrive", "$ownCars.doors", "$ownCars.seats",
						"$ownCars.fuelConsumption", "$ownCars.features", "$ownCars.carClass", "$ownCars.pricePerDay",
						"$ownCars.distance", "$ownCars.about", "$ownCars.pickUpPlace", "$ownCars.imageUrl",
						"$ownCars.bookedPeriod")
				.and("firstName").as("owner.firstName").and("secondName").as("owner.secondName").and("registrationDate")
				.as("owner.registrationDate");

		return mongoTemplate.aggregate(Aggregation.newAggregation(unwindOperation, matchOperation, projectOperation),
				UserMongo.class, CarResponseDto.class).getMappedResults();

	}
}
