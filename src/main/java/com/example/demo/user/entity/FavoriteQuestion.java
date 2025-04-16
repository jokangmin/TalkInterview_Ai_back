package com.example.demo.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorite_questions_table")
@Getter
@Setter
@Builder
public class FavoriteQuestion {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name = "interview_q", length = 255, nullable = false)
    private String interviewQ;

    @Lob
    @Column(nullable = false)
    private String answer;

    @Lob
    private String feedback;

    @Column(length = 50)
    private String category;

    @Column(length = 50)
    private String jobTitle;
}
