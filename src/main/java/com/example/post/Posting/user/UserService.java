package com.example.post.Posting.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JdbcUserRepository jdbcUserRepository;
    private final UserRepository userRepository;

    public Long save(UserRequestDto request) {
        return userRepository.save(request.toEntity()).getSeq();
    }

    public UserResponseDto findByEmail(Long seq) {
        User user = userRepository.findById(seq)
                .orElseThrow(() -> new IllegalArgumentException(seq + " 회원은 존재하지 않습니다."));

        return  new UserResponseDto(user); // 엔티티를 UserResponseDto 형태로 변환하여 전달
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream() // 스트림 변환
                .map(UserResponseDto::new) // 리스트의 User 객체 -> UserResponseDto 객체로 변환
                .collect(Collectors.toList()); // Collection의 List형으로 형변환
    }

    public int update(String email, UserRequestDto request) {
        // userRepository.

        return -1;
    }

    public int delete(String email) {
        return jdbcUserRepository.delete(email);
    }

}