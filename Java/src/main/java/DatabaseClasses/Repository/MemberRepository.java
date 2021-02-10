package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.Data.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface MemberRepository extends MongoRepository<Member, Integer> {

    List<Member> findMemberBymemberName(String id);

}


