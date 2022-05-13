package com.example.post.Posting.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter // 모든 필드에 대해 게터 생성
@NoArgsConstructor // 기본 생성자 생성
public class User {

    private String email;
    private String password;
    private String name;
    private LocalDateTime createAt; // 아이디 생성일자

    @Builder
    public User(String email, String password, String name, LocalDateTime createAt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.createAt = createAt;
    }

}