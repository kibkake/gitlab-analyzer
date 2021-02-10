//package main.java.DatabaseClasses.Repository;
//
//import main.java.DatabaseClasses.Model.Projects;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///*
// This interface connects Spring app to MongoDB.
//
// By this interface you can use the default methods of mongoDB,
// save(), findOne(), findById(), findAll(), count(), delete(), deleteById()…
// without implementing these methods, in addition to our own defined methods
// */
//@Repository
//public interface ProjectRepository extends MongoRepository <Projects, Integer> {
//    List<Projects> findByIdContaining(int id);
//    // More functions can be added more here, or a new implementation class could be added
//}
