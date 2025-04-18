package com.leang.jwtpractice.repository;

import com.leang.jwtpractice.model.entity.AppUser;
import com.leang.jwtpractice.model.request.AppUserRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppUserRepository {
    @Results(id = "appUserMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "roles", column = "user_id", many = @Many(
                    select = "getAllRolesByUserId"
            ))
    })
    @Select("""
                SELECT * FROM app_users
                WHERE email = #{email};
            """)
    AppUser getUserByEmail(String email);

    @Select("""
                SELECT name FROM roles ar
                INNER JOIN user_role ur
                ON ar.role_id = ur.role_id
                WHERE user_id = #{userId};
            """)
    List<String> getAllRolesByUserId(Long userId);

    @Select("""
                INSERT INTO app_users
                VALUES (default, #{request.fullName}, #{request.email}, #{request.password})
                RETURNING *
            """)
    @ResultMap("appUserMapper")
    AppUser register(@Param("request") AppUserRequest request);

    @Insert("""
                INSERT INTO user_role
                VALUES (#{userId}, #{roleId})
            """)
    void insertUserIdAndRoleId(Long roleId, Long userId);

    @Select("""
                SELECT * FROM app_users
                WHERE user_id = #{userId}
            """)
    @ResultMap("appUserMapper")
    AppUser getUserById(Long userId);
}
