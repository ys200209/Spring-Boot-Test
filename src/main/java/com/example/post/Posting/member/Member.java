package com.example.post.Posting.member;

import com.example.post.Posting.team.Team;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    Long seq; // PK

    @Column(nullable = false)
    String name; // 이름

    @Column(nullable = false)
    int age; // 나이

    @ManyToOne
    @JoinColumn(name = "team")
    Team team; // 자기가 속한 팀

    @Builder
    public Member(String name, int age, Team team) {
        this.name = name;
        this.age = age;
        this.team = team;
    }
}