package main.java.DatabaseClasses.Repository.User;

import main.java.Collections.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Implements basic CRUD and adds use for custom Database functions for use with spring.
 * The MongoRepository implements the basic CRUD and is connected to spring through the config file using the mongo
 * template bean. UserRepositoryCustom follows the spring naming conventions to implement custom database function with
 * the custom interface and its implementation
 */

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

    void createUserAccount(User user);
    void changePassword(User user);
    void changeToken(User user);
    void setToken(User user);

    User findUserByUsername(String username);

    User retrieveUserInfo(String username);



}
