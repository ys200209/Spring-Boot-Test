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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seq; // 팀 PK

    @Column(nullable = false)
    String name; // 팀명

    @OneToMany
    List<Member> members = new ArrayList<>(); // 팀에 소속된 Member들 (NPE를 방지하기 위해 미리 객체 초기화)
    // 일대다(OneToMany) 관계 매핑에서 단방향만 존재할 시(Member 테이블에서는 Team 필드 자체가 없을 때)
    // Member, Team 테이블 말고도 team_members 라는 테이블이 생성되며
    // 이 테이블은 members List 필드의 각 인덱스를 PK로 가지고 team.seq를 FK로 가지는 리스트용 테이블이 따로 생성됨.
    // (매우 잘못된 문제이며, 이에 대해서 일대다 단방향 매핑의 문제점을 많이 찾아볼 수 있음)

    /*@Builder
    public Team(String name, Member member) {
        this.name = name;
        this.members.add(member); // 이게 맞냐?
    }*/

    @Builder
    public Team(Long seq, String name) {
        this.seq = seq;
        this.name = name;
    }
}