package com.example.post.Posting.team;

import com.example.post.Posting.member.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamApiController {

    private final TeamService teamService;

    @PostMapping
    public Long save(@RequestBody TeamRequestDto requestDto) {
        return teamService.save(requestDto);
    }

    @GetMapping("/{seq}")
    public List<TeamResponseDto> findMembers(@PathVariable("seq") Long seq) {
        return teamService.findById(seq);
    }


}
