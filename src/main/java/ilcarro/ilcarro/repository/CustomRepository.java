package ilcarro.ilcarro.repository;

import java.text.ParseException;
import java.util.List;

import ilcarro.ilcarro.dto.Comment;
import ilcarro.ilcarro.entities.CarMongo;
import ilcarro.ilcarro.entities.UserMongo;

public interface CustomRepository {
	List<UserMongo> getThreePopularCars();

//    @Query(fields = "{'comments.postDate': 1, _id: 0}")
//    List<UserMongo> findByCommentsPostDate();
	List<Comment> getLatestComments();

	List<UserMongo> searchCarAgainstBookedPeriod(String city, String startDate, String endDate, double minAmount,
			double maxAmount, boolean ascending, int itemOnPage, int currentPage) throws ParseException;
}
