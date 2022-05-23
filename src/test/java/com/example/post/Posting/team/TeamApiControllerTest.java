package com.example.post.Posting.team;

import com.example.post.Posting.member.Member;
import com.example.post.Posting.member.MemberRepository;
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
    TeamRepository teamRepository;

    @Test
    @DisplayName("mappedBy 옵션을 주지 않고 members 리스트에 멤버 담아서 저장해보기")
    public void save() throws Exception {
        Team team = Team.builder()
                .name("테스트 팀2")
                .build();

        /*mvc.perform(post("/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(team)))
                .andExpect(status().isOk())
                .andDo(print());*/

        teamRepository.save(team);

        Member member = Member.builder()
                .age(30)
                .name("김향기")
                .team(team)
                .build();

        /*mvc.perform(post("/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(member)));*/

        memberRepository.save(member);
        // repository로 저장하면 엔티티의 seq가 생기지만
        // MockMvc로 POST 요청을 전송하면 seq가 안생김.

        ResultActions result = mvc.perform(get("/member/" + member.getSeq()));

        result
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("김향기"))
                .andExpect(jsonPath("$.age").value("30"));

        Member member2 = Member.builder()
                .age(50)
                .name("김향기2")
                .team(team)
                .build();

        team.addMember(member2);

        System.out.println("team Size : " + team.members.size());

    }

}
