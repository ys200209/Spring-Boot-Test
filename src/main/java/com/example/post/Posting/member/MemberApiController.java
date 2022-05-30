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
        // RestController는 클라이언트가 예상하는 HttpStatus()를 리턴해줄 수 없다.
        // 때문에, ResponseEntity로 감싸서 .ok() 메서드를 날려주는 방식으로 status 코드를 날려줄 수 있음.
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