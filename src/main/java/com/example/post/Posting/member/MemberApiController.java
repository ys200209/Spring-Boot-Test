package com.example.post.Posting.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping()
    public Long save(@RequestBody MemberRequestDto requestDto) {
        return memberService.save(requestDto);
    }

    @GetMapping("/{seq}")
    public MemberResponseDto findById(@PathVariable("seq") Long seq) {
        return memberService.findById(seq);
    }


}
