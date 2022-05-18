package com.example.post.Posting.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long save(MemberRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity()).getSeq();
    }

    public MemberResponseDto findById(Long seq) {
        Member member = memberRepository.findById(seq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return new MemberResponseDto(member);
    }

}
