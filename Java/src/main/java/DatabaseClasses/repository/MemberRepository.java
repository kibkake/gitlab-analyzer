package main.java.DatabaseClasses.repository;

import main.java.DatabaseClasses.model.data.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface MemberRepository extends MongoRepository<Member, Integer> {

    List<Member> findMemberBymemberName(String id);

}


