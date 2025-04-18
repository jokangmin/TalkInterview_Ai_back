package com.example.demo.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.user.entity.FavoriteQuestion;

@Repository
public interface FavoriteQuestionRepository extends JpaRepository<FavoriteQuestion, Integer>{
	List<FavoriteQuestion> findByMemberId(Integer memberId);
	
	boolean existsByMemberIdAndInterviewQ(int memberId, String interviewQ);

}
