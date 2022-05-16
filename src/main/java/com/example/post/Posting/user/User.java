package com.example.post.Posting.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter // 모든 필드에 대해 게터 생성
@NoArgsConstructor // 기본 생성자 생성
@Table(name = "User")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createAt; // 아이디 생성일자

    @Builder
    public User(Long seq, String email, String password, String name, LocalDateTime createAt) {
        this.seq = seq;
        this.email = email;
        this.password = password;
        this.name = name;
        this.createAt = createAt;
    }

}