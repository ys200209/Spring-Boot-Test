package com.example.post.Posting.team;

import com.example.post.Posting.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Team {

    @Id
    Long seq; // 팀 PK

    @Column(nullable = false)
    String name; // 팀명

    @OneToMany(mappedBy = "team")
    List<Member> members = new ArrayList<>(); // 팀에 소속된 Member들 (NPE를 방지하기 위해 미리 객체 초기화)

    @Builder
    public Team(String name, Member member) {
        this.name = name;
        this.members.add(member); // 이게 맞냐?
    }
}