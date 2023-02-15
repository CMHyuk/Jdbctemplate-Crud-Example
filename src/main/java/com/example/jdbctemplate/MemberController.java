package com.example.jdbctemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final JdbcMemberRepository memberRepository;

    @PostMapping("/save")
    public Member saveController(@RequestBody SaveRequest request) {
        return memberRepository.save(request);
    }

    @PatchMapping("/update/{memberId}")
    public void updateController(@PathVariable Long memberId,  @RequestBody UpdateRequest request) {
        memberRepository.update(memberId, request);
    }

    @GetMapping("/find/{memberId}")
    public Optional<Member> findController(@PathVariable Long memberId) {
        return memberRepository.findById(memberId);
    }

    @GetMapping("/findAll")
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
