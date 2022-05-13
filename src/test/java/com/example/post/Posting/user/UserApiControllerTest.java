package com.example.post.Posting.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
@WebMvcTest // @Controller, @RestController가 설정된 클래스들을 찾아 메모리에 생성한다.
@ExtendWith(SpringExtension.class)
*/
@SpringBootTest // @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc // 컨트롤러뿐만 아니라 @Service, @Repository가 분은 객체들도 모두 메모리에 올린다 <-> @WebMvcTest : Controller만 올린다.
public class UserApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public MockMvc mvc;

    @Test
    @DisplayName("회원가입")
    public void join() throws Exception {
        /*UserRequestDto requestDto = UserRequestDto.builder()
                .email("test@test.com")
                .password("test")
                .name("테스트")
                .build();*/

        String requestDto =
                "{" +
                        "\"email\": \"test@test.com\", " +
                        "\"password\": \"test\", " +
                        "\"name\": \"테스트\"" +
                "}";

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                //.content(objectMapper.writeValueAsString(requestDto)))
                .content(requestDto))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("특정 회원 조회")
    public void findById() throws Exception {
        String testEmail = "test@test.com";

        mvc.perform(get("/users/" + testEmail))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("모든 회원 조회")
    public void findAll() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원정보 변경")
    public void update() throws Exception {
        String testEmail = "test@test.com";

        UserRequestDto requestDto = UserRequestDto.builder()
                .email("test@test.com")
                .password("변경된 비밀번호입니다")
                .name("변경된 이름입니다.")
                .build();

        mvc.perform(put("/users/" + testEmail)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원정보 삭제")
    public void deleteUser() throws Exception {
        String testEmail = "test@test.com";

        mvc.perform(delete("/users/" + testEmail))
                .andExpect(status().isOk())
                .andDo(print());
    }

}