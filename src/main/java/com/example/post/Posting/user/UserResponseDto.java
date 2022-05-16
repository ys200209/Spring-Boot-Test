package com.example.post.Posting.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // 게터 생성
public class UserResponseDto {

    private Long seq;
    private String email;
    private String name;

    public UserResponseDto(User user) {
        this.seq = user.getSeq();
        this.email = user.getEmail();
        this.name = user.getName();
    }

}
