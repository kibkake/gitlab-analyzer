package DatabaseClasses.repository;

import DatabaseClasses.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserDataRepository extends MongoRepository<UserData, Integer>{
    List<UserData> findByIdContaining(int id);
    List<UserData> findByName(String name);
}
