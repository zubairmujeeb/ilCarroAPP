package ilcarro.ilcarro.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ilcarro.ilcarro.dto.Comment;
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

	@Override
	public List<UserMongo> searchCarAgainstBookedPeriod(String city, String startDate, String endDate, double minAmount,
			double maxAmount, boolean ascending, int itemOnPage, int currentPage) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

		Query query = new Query();

		Criteria dateCriteria = new Criteria().andOperator(
				Criteria.where("bookedPeriod.startDate").gte(sdf.parse(startDate)),
				Criteria.where("bookedPeriod.endDate").lte(sdf.parse(endDate)));

		Criteria amountCriteria = Criteria.where("bookedPeriod.amount").lt(maxAmount).gt(minAmount);
		query.addCriteria(Criteria.where("ownCars").elemMatch(new Criteria().orOperator(dateCriteria, amountCriteria,
				Criteria.where("pickUpPlace.placeId").is(city))));
		query.fields().include("ownCars");

		final Pageable pageableRequest = PageRequest.of(currentPage - 1, itemOnPage);
		query.with(pageableRequest);

		return mongoTemplate.find(query, UserMongo.class);
	}
}
