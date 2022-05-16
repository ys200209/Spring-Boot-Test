package com.example.post.Posting.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
// @NoArgsConstructor

@Getter
@Builder
public class UserRequestDto {

    private String email;
    private String password;
    private String name;

    /*
    @Builder
    public UserRequestDto(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    */

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }

}