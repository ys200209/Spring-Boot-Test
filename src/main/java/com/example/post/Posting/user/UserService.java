package com.example.post.Posting.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JdbcUserRepository jdbcUserRepository;

    public int join(UserRequestDto request) {
        return jdbcUserRepository.join(request);
    }

    public UserDto findByEmail(String email) {
        return jdbcUserRepository.findByEmail(email);
    }

    public List<UserDto> findAll() {
        return jdbcUserRepository.findAll();
    }

    public int update(String email, UserRequestDto request) {
        return jdbcUserRepository.update(email, request);
    }

    public int delete(String email) {
        return jdbcUserRepository.delete(email);
    }

}