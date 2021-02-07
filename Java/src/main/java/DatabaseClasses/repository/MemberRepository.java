package DatabaseClasses.repository;

import DatabaseClasses.model.Member;
import DatabaseClasses.model.Projects;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends MongoRepository <Member, Integer> {

    List<Member> findByIdContaining(int memberId);
    List<Member>findMemberByMemberNameIs(String memberName);
}
