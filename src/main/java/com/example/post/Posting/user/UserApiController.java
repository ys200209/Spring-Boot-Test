package com.example.post.Posting.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 각 메서드가 반환하는 객체를 Json 형식으로 반환함
@RequiredArgsConstructor // final 키워드가 붙은 멤버에게 자동으로 의존성을 주입시킴
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService; // 의존 주입

    @PostMapping // 회원가입
    public int join(@RequestBody UserRequestDto request) {
        // @RequestBody : Json 형식의 문자열을 해당 자바 객체(UserRequestDto)로 변환한다.
        int result = userService.join(request);
        return result;
    }
    @GetMapping("/{email}") // 특정 회원 조회
    public UserDto findById(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }

    @GetMapping // 모든 회원 조회
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @PutMapping("/{email}") // 회원정보 수정
    public int update(@PathVariable("email") String email, @RequestBody UserRequestDto request) {
        return userService.update(email, request);
    }

    @DeleteMapping("/{email}") // 회원 삭제
    public int delete(@PathVariable("email") String email) {
        return userService.delete(email);
    }

}