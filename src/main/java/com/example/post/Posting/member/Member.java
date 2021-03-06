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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seq; // 멤버 PK
    int age; // 멤버 나이
    String name; // 멤버 이름

    @ManyToOne// (cascade = CascadeType.ALL) // save 시에만 : cascade = CascadeType.PERSIST
    // Exception :: object references an unsaved transient instance - save the transient instance before flushing
    @JoinColumn(name = "team") // 유무 차이 : 단방향 관계 매핑일 때는 있으나 없으나 관계 ERD가 동일함. 양방향 매핑관계에서 참조 테이블측에서 현재 테이블에 접근하기 위해 정의하는 것??
    Team team; // 멤버가 소속된 Team 테이블의 키 // 어떻게 이것이 가능한 것인지 ORM

    @Builder
    public Member(String name, int age, Team team) {
        this.name = name;
        this.age = age;
        this.team = team;
        team.getMembers().add(this); // 이렇게 하면 따로 Team에서 Member에 대한 add() 메서드를 쓰지 않아도 됨
    }/**/

    public void update(MemberRequestDto requestDto) {
        this.age = requestDto.age;
        this.name = requestDto.name;
        this.team = requestDto.team;
    }

    // @Lob Annotation
}