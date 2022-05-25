package com.example.post.Posting.team;

import com.example.post.Posting.member.Member;
import com.example.post.Posting.member.MemberRepository;
import com.example.post.Posting.member.MemberRequestDto;
import com.example.post.Posting.member.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamApiControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    TeamRepository teamRepository;

    @Test
    @DisplayName("엔티티 수정 시 mappedBy 옵션 차이")
    // mappedBy 속성이 없을 때 : 팀을 두 개 만들어두고 team1에서 team2로 변경할 때
    // member.setTeam("team2")로 하든, members.get(~).setTeam("team2")로 하든 똑같다. 라고 함.
    public void save() throws Exception {
        Team team1 = Team.builder().name("테스트 팀1").build();
        Team team2 = Team.builder().name("테스트 팀2").build();

        teamRepository.save(team1);
        teamRepository.save(team2);

        Member member = Member.builder()
                .age(30)
                .name("홍길동")
                .team(team1)
                .build();

        memberRepository.save(member);
        // repository로 저장하면 엔티티의 seq가 생기지만
        // MockMvc로 POST 요청을 전송하면 seq가 안생김.

        mvc.perform(get("/member/" + member.getSeq()))
                .andExpect(jsonPath("$.team_name").value("테스트 팀1"));

        memberService.update(member.getSeq(), MemberRequestDto.builder()
                .age(30)
                .name("홍길동")
                .team(team2)
                .build());

        mvc.perform(get("/member/" + member.getSeq()))
                .andExpect(jsonPath("$.team_name").value("테스트 팀2"));
    }

}