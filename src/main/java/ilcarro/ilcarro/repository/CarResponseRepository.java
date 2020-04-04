package ilcarro.ilcarro.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ilcarro.ilcarro.entities.UserMongo2;

@Repository
public interface CarResponseRepository extends MongoRepository<UserMongo2, String> {

}
