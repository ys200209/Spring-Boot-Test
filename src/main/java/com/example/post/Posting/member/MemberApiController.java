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
        return memberService.save(requestDto);
    }

    @GetMapping("/{seq}")
    public MemberResponseDto findById(@PathVariable("seq") Long seq) {
        System.out.println("\nmemberService의 hashCode ? : " + memberService + "\n");
        return memberService.findById(seq);
    }

    @GetMapping
    public List<MemberResponseDto> findAll() {
        return memberService.findAll();
    }

    @PutMapping("/{seq}")
    public Long update(@PathVariable("seq") Long seq, @RequestBody MemberRequestDto requestDto) {
        return memberService.update(seq, requestDto);
    }

    @DeleteMapping("/{seq}")
    public void delete(@PathVariable("seq") Long seq) {
        memberService.delete(seq);
    }

}