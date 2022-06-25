package com.example.post.Posting.user;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest {
    // Controller 단위 테스트
    // https://jiminidaddy.github.io/dev/2021/05/18/dev-spring-%EB%8B%A8%EC%9C%84%ED%85%8C%EC%8A%A4%ED%8A%B8-API/
    /*
    * @WebMvcTest 를 사용하면 Controller만 따로 떼어내서 단위 테스트를 할 수 있다고 하는데 정확히 이 어노테이션이 무슨 의미인지 검색.
    *
    * 의문 : 이렇게 하는게 정말 'Controller 단위 테스트' 가 맞는지?
    * */

    // Service & Repository 단위 테스트
    // https://jiminidaddy.github.io/dev/2021/05/20/dev-spring-%EB%8B%A8%EC%9C%84%ED%85%8C%EC%8A%A4%ED%8A%B8-Repository/
    /*
    * @DataJpaTest : JPA와 관련된 설정만 로드되며 Bean 의존 주입은 로드안됨. (+ 자동으로 @Transactional 어노테이션도 함께 등록됨. 즉, 롤백됨)
    * @ExtendWith(MockitoExtension.class) : Spring 컨테이너를 로드하지 않고 테스트 기능만 제공함. (Spring 없이 순수 단위 테스트만 하고 싶을때 사용.)
    * @ExtendWith(SpringExtension.class) : Spring 컨테이너를 직접 로드하기 때문에 Test 코드에서 @Autowired를 통해 Bean 주입을 할 수 있음. (즉 테스트를 위해 Spring 컨테이너가 필요할 때 사용.)
    *
    * 의문 : 이 방법이 '최선'인가? 다른 Service 및 Repository 단위 테스트를 찾아보자.
    * */

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void 회원가입() {
        // given
        UserRequestDto request = UserRequestDto.builder()
                .email("email@naver.com")
                .password("1234")
                .name("홍길동")
                .build();

        String email = "email@naver.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(request.toEntity());

        // when
        Long result = userService.save(request);

        // then
        assertThat(result).isEqualTo(request.toEntity().getSeq());
    }

    @Test
    void 중복_회원가입() throws Exception {
        // given
        UserRequestDto request1 = UserRequestDto.builder()
                .email("email@naver.com")
                .password("1234")
                .name("홍길동")
                .build();

        UserRequestDto request2 = UserRequestDto.builder()
                .email("email@naver.com")
                .password("5678")
                .name("최길동")
                .build();
        // request1과 request2를 각각 저장해줄 필요가 없다.
        // (단, 선언은 둘 다 해놓고 request2를 저장했을 때 중복 아이디로 나오는 엔티티가 reqeust1이 되도록 검증 로직을 작성하였음)

        // 그냥 중복 회원시 예외만 터트리면 되니까 처음 회원을 넣을 때부터 중복 회원이 존재할 것이라고 Mock 로직을 작성해놓으면 된다(when(~~~))

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(request1.toEntity()));
//        when(userRepository.save(any(User.class))).thenReturn(request1.toEntity());
        // 저장하는 로직도 예외 처리에서는 필요없다. (사실 이 로직은 실행되지 않기 때문에 작성해놓으면 예외터짐. 깔끔한 Stub 코드가 아니라서.)

        // when
        Exception e = Assertions.assertThrows(IllegalStateException.class, () -> {
            userService.save(request2); // 중복 회원 발생.
        });

        // then // 이 검증 로직은 사실 없어도 위의 when에서 예외 체크를 하기 때문에 상관없지만 그냥 적음
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    void 이메일로_회원_찾기() {
        // given
        UserRequestDto request = UserRequestDto.builder()
                .email("email@naver.com")
                .password("1234")
                .name("홍길동")
                .build();

        String email = "email@naver.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(request.toEntity()));
        // 너무 어렵네.
        // 위의 로직은 findByEmail 메서드에 파라미터로 email 문자열 값이 들어가면
        // 이에 해당하는 객체인 request 엔티티가 반환되도록 구현하였음.
        // 계속 findByEmail(any(String.class)) 로 넣기도 하고, 또 저장도 같이 시키려고 하니 문제가 발생하였는데,
        // 자꾸 헷갈리는게 회원을 찾으려고 실제 DB에 넣을 필요가 없다는 것.. (이거 때문에 삽질 많이함)
        // 그냥 원하는 문자열(이메일 값)이 들어가면 그거에 해당하는 반환값만 적어주고 그게 맞는지만 확인하면 되는데 왤케 헷갈리는지..
        // 특정 회원을 얻기 위해서 값을 저장하고 조회할 필요가 전혀 없다. 그냥 조건만 담고 그 객체를 리턴해주면 되는 것.

        // when
        UserResponseDto response = userService.findByEmail(email);

        // then
        assertThat(response.getName()).isEqualTo(request.getName());

    }

    @Test
    void 존재하지_않는_회원조회() {
        // given
        String email = "존재하지 않는 이메일";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.findByEmail(email);
        });

        // then
        assertThat(e.getMessage()).isEqualTo(email + " 회원은 존재하지 않습니다.");

    }

    @Test
    void findAll() {
        // given
        UserRequestDto request1 = UserRequestDto.builder().email("email1@naver.com")
                .password("1234").name("홍길동").build();

        UserRequestDto request2 = UserRequestDto.builder().email("email2@naver.com")
                .password("5678").name("최길동").build();

        UserRequestDto request3 = UserRequestDto.builder().email("email3@naver.com")
                .password("9101112").name("박길동").build();

        List<User> list = new ArrayList<>();
        list.add(request1.toEntity());
        list.add(request2.toEntity());
        list.add(request3.toEntity());

        when(userRepository.findAll()).thenReturn(list);

        // when
        List<UserResponseDto> result = userService.findAll();

        // then
        assertThat(result.size()).isEqualTo(list.size());
    }

    @Test
    void update() {
        // given
        UserRequestDto request1 = UserRequestDto.builder()
                .email("email@naver.com")
                .password("1234")
                .name("홍길동")
                .build();

        UserRequestDto request2 = UserRequestDto.builder()
                .email(request1.getEmail())
                .password(request1.getPassword())
                .name("최길동")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(request2.toEntity());

        // when
        Long result = userService.update(request2);

        // then
        assertThat(result).isEqualTo(request2.toEntity().getSeq());

    }
}