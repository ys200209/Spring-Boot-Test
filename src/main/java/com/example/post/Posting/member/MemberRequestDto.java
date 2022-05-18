package com.example.post.Posting.member;

import com.example.post.Posting.team.Team;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // 모든 필드에 대해 게터 생성
@NoArgsConstructor // 기본 생성자를 생성한다.
public class MemberRequestDto {

    int age; // 멤버 나이
    String name; // 멤버 이름
    Team team;

    @Builder
    public MemberRequestDto(int age, String name, Team team) {
        this.age = age;
        this.name = name;
        this.team = team;
    }

    public Member toEntity() {
        return Member.builder()
                .age(age)
                .name(name)
                //.team(team)
                .build();
    }

}