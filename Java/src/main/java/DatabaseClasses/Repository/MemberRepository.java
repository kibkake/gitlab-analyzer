package main.java.DatabaseClasses.Repository;

import main.java.DatabaseClasses.Model.Data.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

// This memberRepository is not configured with documents & cloud.
// If we don't make a separate collection, should be removed at the next commit.
public interface MemberRepository extends MongoRepository<Member, String> {

    List<Member> findMemberBymemberName(String memberName);

}


