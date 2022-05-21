package com.example.post.Posting.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional // 필?
    public Long save(MemberRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity()).getSeq();
    }

    
    public MemberResponseDto findById(Long seq) {
        Member member = memberRepository.findById(seq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // System.out.println("조회한 멤버의 팀 : " + member.team.getName());

        return new MemberResponseDto(member);
    }

    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

}
