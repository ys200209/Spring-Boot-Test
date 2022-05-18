package com.example.post.Posting.team;

import com.example.post.Posting.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // 모든 필드에 대해 게터 생성
@NoArgsConstructor // 기본 생성자를 생성한다.
public class TeamRequestDto {

    String name;

    @Builder
    public TeamRequestDto(String name) {
        this.name = name;
    }

    public Team toEntity() {
        return Team.builder()
                .name(name)
                .build();
    }

}