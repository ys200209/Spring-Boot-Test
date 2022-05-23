package com.example.post.Posting.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long seq) {
        Member member = memberRepository.findById(seq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return new MemberResponseDto(member);
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long seq, MemberRequestDto requestDto) {
        Member member = memberRepository.findById(seq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        member.update(requestDto);
        return seq;
    }

    @Transactional
    public void delete(long seq) {
        memberRepository.deleteById(seq);
    }

}