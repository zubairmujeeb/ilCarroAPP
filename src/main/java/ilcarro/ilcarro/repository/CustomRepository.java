package ilcarro.ilcarro.repository;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ilcarro.ilcarro.dto.Comment;
import ilcarro.ilcarro.dto.carDto.CarResponseDto;
import ilcarro.ilcarro.entities.UserMongo;

public interface CustomRepository {
	List<UserMongo> getThreePopularCars();

//    @Query(fields = "{'comments.postDate': 1, _id: 0}")
//    List<UserMongo> findByCommentsPostDate();
	List<Comment> getLatestComments();

	Page<CarResponseDto> searchCarAgainstBookedPeriod(String city, String startDate, String endDate, double minAmount,
			double maxAmount, boolean ascending, Pageable pageable) throws ParseException;
	
	List<UserMongo> searchCarByCoordinates(float latitude, float longitude, float radius, int itemOnPage,
			int currentPage) throws ParseException;
}
