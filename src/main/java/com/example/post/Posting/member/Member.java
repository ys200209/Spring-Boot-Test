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
    Long seq; // 멤버 PK
    int age; // 멤버 나이
    String name; // 멤버 이름
    Long team_id;

    /*@ManyToOne
    @JoinColumn(name = "team")
    Team team; // 멤버가 소속된 Team 테이블의 키
*/
    @Builder
    public Member(String name, int age, Long team_id) {
        this.name = name;
        this.age = age;
        this.team_id = team_id;
    }
}