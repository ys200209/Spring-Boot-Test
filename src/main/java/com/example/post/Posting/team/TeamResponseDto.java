package com.example.post.Posting.team;

import com.example.post.Posting.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter // 모든 필드에 대해 게터 생성
@NoArgsConstructor // 기본 생성자를 생성한다.
public class TeamResponseDto {

    Long seq;
    int age; // 멤버 나이
    String name; // 멤버 이름

    public TeamResponseDto(Member member) {
        this.seq = member.getSeq();
        this.age = member.getAge();
        this.name = member.getName();
    }

}
