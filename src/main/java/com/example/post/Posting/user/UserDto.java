package com.example.post.Posting.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // 게터 생성
@NoArgsConstructor // 기본 생성자
public class UserDto {

    private String email;
    private String name;

    @Builder
    public UserDto(String email, String name) {
        this.email = email;
        this.name = name;
    }

}
