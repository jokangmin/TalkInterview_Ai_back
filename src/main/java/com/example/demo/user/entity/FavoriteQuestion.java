package com.example.demo.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorite_questions_table")
@Getter
@Setter
@Builder
@NoArgsConstructor // 추가된 부분: 인수 없는 기본 생성자를 생성합니다.
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

    @Builder // @NoArgsConstructor와 함께 사용하면 모든 필드를 인수로 받는 생성자도 생성해줍니다.
    public FavoriteQuestion(Integer id, Integer memberId, String interviewQ, String answer, String feedback, String category, String jobTitle) {
        this.id = id;
        this.memberId = memberId;
        this.interviewQ = interviewQ;
        this.answer = answer;
        this.feedback = feedback;
        this.category = category;
        this.jobTitle = jobTitle;
    }
}