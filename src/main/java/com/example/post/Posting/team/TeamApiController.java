package com.example.post.Posting.team;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamApiController {

    private final TeamService teamService;

    @PostMapping
    public Long save(@RequestBody TeamRequestDto requestDto) {
        return teamService.save(requestDto);
    }

    @GetMapping("{seq}")
    public TeamResponseDto findById(@PathVariable("seq") Long seq) {
        return teamService.findById(seq);
    }

}
