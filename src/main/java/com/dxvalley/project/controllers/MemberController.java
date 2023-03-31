package com.dxvalley.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxvalley.project.models.CreateResponse;
import com.dxvalley.project.models.Member;
import com.dxvalley.project.services.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {
    @Autowired
    private final MemberService memberService;

    @GetMapping("/getMembers")
    List<Member> getMembers() {
        return this.memberService.getMembers();
    }

    @GetMapping("/{memberId}")
    Member getMember(@PathVariable Long memberId) {
        return memberService.getMemberById(memberId);
    }

    @PostMapping("/add")
    public ResponseEntity<CreateResponse> addMember(@RequestBody Member member) {
        memberService.addMember(member);
        CreateResponse response = new CreateResponse("success", "member created successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/edit/{memberId}")
    Member editMember(@RequestBody Member tempMember, @PathVariable Long memberId) {
        Member member = this.memberService.getMemberById(memberId);
        member.setFullName(tempMember.getFullName());
        member.setGender(tempMember.getGender());
        member.setIsFounder(tempMember.getIsFounder());
        member.setDateCreated(tempMember.getDateCreated());
        member.setAddress(tempMember.getAddress());
        member.setUnion(tempMember.getUnion());
        member.setPrCooperative(tempMember.getPrCooperative());

        return memberService.editMember(member);
    }

    // @DeleteMapping("/delete/{sectorId}")
    // void deleteSector(@PathVariable Long sectorId) {
    // sectorService.deleteSector(sectorId);
    // }

}