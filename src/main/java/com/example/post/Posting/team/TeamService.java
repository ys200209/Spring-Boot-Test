package com.example.post.Posting.team;

import com.example.post.Posting.member.Member;
import com.example.post.Posting.member.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public Long save(TeamRequestDto requestDto) {
        return teamRepository.save(requestDto.toEntity()).getSeq();
    }

    @Transactional(readOnly = true)
    public List<TeamResponseDto> findById(Long seq) {
        Team team = teamRepository.findById(seq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));

        return team.members.stream()
                .map(TeamResponseDto::new)
                .collect(Collectors.toList());
    }

}