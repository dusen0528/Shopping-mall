package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        /* #3-1 회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
          해당 코드는 SQL Injection이 발생합니다. SQL Injection이 발생하지 않도록 수정하세요.
         */
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = """
    SELECT user_id, user_name, user_password, user_birth, 
           user_auth, user_point, created_at, latest_login_at 
    FROM users 
    WHERE user_id = ? AND user_password = ?
    """;
        // Todo 1 : 비밀번호, 포인트등은 로그인 후 세션에서 들고 있을 필요 없음
        // 상황에 맞는 필요한 객체만 별도로 만들어서 응답해주기

        log.debug("sql:{}",sql);

        try( PreparedStatement psmt = connection.prepareStatement(sql);) {
            psmt.setString(1, userId);
            psmt.setString(2, userPassword);
            try(ResultSet rs = psmt.executeQuery()){
                if(rs.next()){
                    User user = new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        // #3-2 회원조회
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at "
                + "from users where user_id=?";

        log.debug("sql:{}", sql);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, userId);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if(rs.next()){
                    User user = new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    );
                    return Optional.of(user);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(User user) {
        // #3-3 회원등록, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO users (user_id, user_name, user_password, user_birth, user_auth, user_point, created_at) VALUES(?, ?, ?, ?, ?, ?, ?)";
        log.debug("sql:{}", sql);
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getUserPassword());
            preparedStatement.setString(4, user.getUserBirth());
            preparedStatement.setString(5, String.valueOf(user.getUserAuth()));
            preparedStatement.setInt(6, user.getUserPoint());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        // #3-4 회원삭제, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM users WHERE user_id=?";
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            preparedStatement.setString(1, userId);

            return  preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(User user) {
        // #3-5 회원수정, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE users SET user_name=?, user_password=?, user_birth=?, user_auth=?, user_point=? "+
                "WHERE user_id=?";

        try(
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2,user.getUserPassword());
            preparedStatement.setString(3, user.getUserBirth());
            preparedStatement.setString(4, String.valueOf(user.getUserAuth()));
            preparedStatement.setInt(5, user.getUserPoint());
            preparedStatement.setString(6, user.getUserId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        // #3-6, 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.

        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE users SET latest_login_at=? WHERE user_id=?";

        log.debug("sql:{}", sql);
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setTimestamp(1, Timestamp.valueOf(latestLoginAt));
            preparedStatement.setString(2,userId);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUserId(String userId) {
        // #3-7 userId와 일치하는 회원의 count를 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT COUNT(*) FROM users WHERE user_id=?";
        try(
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            preparedStatement.setString(1, userId);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    // UserRepositoryImpl
    @Override
    public List<User> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("user_birth"),
                        User.Auth.valueOf(rs.getString("user_auth")),
                        rs.getInt("user_point"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("latest_login_at") != null ?
                                rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }
}
