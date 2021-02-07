package DatabaseClasses.controller;

import DatabaseClasses.model.Member;
import DatabaseClasses.model.Projects;
import DatabaseClasses.repository.MemberRepository;
import DatabaseClasses.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("git/")
public class MemberController {

    @Autowired
    private static MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("projects/{projectId}/{memberList.memberName}")
    @Query(fields="{'scoreOnMergeRequest' :1, 'scoreCommit' :1 }")
    public List<Member> getMemberStatsOnCode(@RequestParam String memberName) {
        return memberRepository.findMemberByMemberNameIs(memberName);
    }

    @GetMapping("projects/{projectId}/{memberList.memberName}")
    @Query(fields= "{'numWordsOnNotesForMR':1, 'memberList.numWordsOnNotesForIssue' :1}")
    public List<Member> getMemberStatsOnNote(@RequestParam String memberName) {
        return memberRepository.findMemberByMemberNameIs(memberName);
    }
}

