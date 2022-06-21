package com.example.post.Posting.member;

import com.example.post.Posting.team.Team;
import com.example.post.Posting.team.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// @DataJpaTest : ???
public class MemberApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    /*
    Gson 객체를 통해서도 Json -> Java Object 또는 Java Object -> Json으로 변환할 수 있다.
    ex) mvc.perform(
    ~~~
    .content(new Gson().toJson(user))
    )
    */

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    Team team1 = Team.builder().name("테스트 팀1").build();
    Team team2 = Team.builder().name("테스트 팀2").build();

    @Test
    @DisplayName("멤버를 저장하기")
    public void 저장하기() throws Exception {

        teamRepository.save(team1); // "테스트 팀1" 이름을 가진 팀 저장

        // "테스트 팀1" 팀에 소속된 "홍길동" 멤버 생성
        MemberRequestDto member1 = MemberRequestDto.builder()
                .age(20)
                .name("홍길동")
                .team(team1)
                .build();

        System.out.println("team1 : " + team1);

        // 멤버 저장
        mvc.perform(post("/member") // POST : "/member" 요청을 보냄 (멤버 생성)
                .contentType(MediaType.APPLICATION_JSON) // 전송 형식 (application/json)
                .content(objectMapper.writeValueAsString(member1))) // 전송할 데이터
                .andExpect(status().isOk()) // 요청에 대해 성공적인 응답(Status Code : 200)이 오면 테스트 통과
                .andDo(print()); // 보낸 Request와 받은 Response 결과를 출력함
    }

    @Test
    @DisplayName("멤버 조회하기")
    public void 조회하기() throws Exception {

        // 멤버 조회
        mvc.perform(get("/member/1")) // GET : "/member/1" 요청을 보냄 (멤버 조회)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.name").value("홍길동"))
                .andExpect(jsonPath("$.team_name").value("테스트 팀1"));

    }

    @Test
    @DisplayName("멤버 변경하기")
    public void 변경하기() throws Exception {

        teamRepository.save(team2); // "테스트 팀2" 이름을 가진 팀 저장

        // 변경할 객체 생성
        MemberRequestDto member = MemberRequestDto.builder()
                .age(30)
                .name("홍길동")
                .team(team2)
                .build();

        // Update 요청
        mvc.perform(put("/member/1") // PUT : "/member/1" 요청을 보냄 (멤버 변경)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(member)))
                .andDo(print())
                .andExpect(status().isOk());

        // 변경 검증
        mvc.perform(get("/member/1")) // GET : "/member/1" 요청을 보냄 (멤버 조회)
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.name").value("홍길동"))
                .andExpect(jsonPath("$.team_name").value("테스트 팀2"));

    }

    @Test
    @DisplayName("멤버 삭제하기")
    public void 삭제하기() throws Exception {

        mvc.perform(delete("/member/1")) // DELETE : "/member/1" 요청을 보냄 (멤버 삭제)
                .andDo(print())
                .andExpect(status().isOk());

    }

}