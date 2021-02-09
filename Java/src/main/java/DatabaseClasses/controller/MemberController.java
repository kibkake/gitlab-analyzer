package main.java.DatabaseClasses.controller;

import main.java.DatabaseClasses.model.data.Member;
import main.java.DatabaseClasses.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController("memberController")
//@RequestMapping("git/")
public class MemberController {

    @Autowired
    private static MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("projects/{projectId}/{memberList.memberName}")
    @Query(fields="{'scoreOnMergeRequest' :1, 'scoreCommit' :1 }")
    public List<Member> getMemberStatsOnCode(@RequestParam String memberName) {
        return memberRepository.findMemberBymemberName(memberName);
    }

    /*@GetMapping("projects/{projectId}/{memberList.memberName}")
    @Query(fields= "{'numWordsOnNotesForMR':1, 'memberList.numWordsOnNotesForIssue' :1}")
    public List<Member> getMemberStatsOnNote(@RequestParam String memberName) {
        return memberRepository.findMemberByMemberName(memberName);
    }*/
}

