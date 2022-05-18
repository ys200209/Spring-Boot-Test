package com.example.post.Posting.team;

import com.example.post.Posting.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Long save(TeamRequestDto requestDto) {
        return teamRepository.save(requestDto.toEntity()).getSeq();
    }

    public TeamResponseDto findById(Long seq) {
        Team team = teamRepository.findById(seq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));

        return new TeamResponseDto(team);
    }

}
