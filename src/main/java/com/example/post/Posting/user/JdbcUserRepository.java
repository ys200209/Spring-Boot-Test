package com.example.post.Posting.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository {

    private final JdbcTemplate jdbcTemplate;

    // 매퍼 생성
    static RowMapper<UserDto> mapper = (rs, rowNum) ->  // (ResultSet rs, int rowNum)
            new UserDto.UserDtoBuilder()
                    .email(rs.getString("email"))
                    .name(rs.getString("name"))
                    .build();


    public UserDto findByEmail(String email) {
        List<UserDto> results = jdbcTemplate.query(
                "SELECT * FROM User WHERE email = ?",
                mapper,
                email
        );
        return results.isEmpty() ? null : results.get(0);
    }

    public List<UserDto> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM User", mapper);
    }

    public int join(UserRequestDto request) {
        return jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                // 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
                PreparedStatement pstmt = con.prepareStatement(
                        "INSERT INTO User (EMAIL, PASSWORD, NAME, CREATE_AT) " +
                                "VALUES (?, ?, ?, ?)");

                // 인덱스 파라미터 값 설정
                pstmt.setString(1, request.getEmail());
                pstmt.setString(2, request.getPassword());
                pstmt.setString(3, request.getName());
                pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                // 생성한 PreparedStatement 객체 리턴
                return pstmt;
            }
        });
    }

    public int update(String email, UserRequestDto request) {
        return jdbcTemplate.update(
                "UPDATE User SET password = ?, name = ? WHERE email = ?",
                request.getPassword(), request.getName(), email);
    }

    public int delete(String email) {
        return jdbcTemplate.update(
                "DELETE FROM User WHERE email = ?", email);
    }

}