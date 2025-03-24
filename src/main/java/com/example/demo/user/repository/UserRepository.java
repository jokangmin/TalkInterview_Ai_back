package com.example.demo.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	Optional<UserEntity> findByUserId(String user_id);
	
	//회원가입시 중복 ID 확인
    boolean existsByUserId(String user_id);

    UserEntity findByUserIdAndUserPassword(String userId, String userPassword);
}
