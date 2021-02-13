package main.java.DatabaseClasses.repository;

import main.java.DatabaseClasses.Model.User;
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

    User retrieveUserInfo(String username);



}
