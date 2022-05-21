package com.example.post.Posting.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping
    public Long save(@RequestBody MemberRequestDto requestDto) {
        System.out.println("requestDto : " + requestDto);
        System.out.println("team : " + requestDto.team);
        return memberService.save(requestDto);
    }

    @GetMapping("/{seq}")
    public MemberResponseDto findById(@PathVariable("seq") Long seq) {
        return memberService.findById(seq);
    }

    @GetMapping
    public List<MemberResponseDto> findAll() {
        return memberService.findAll();
    }


}