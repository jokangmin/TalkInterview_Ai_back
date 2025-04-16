package com.example.demo.user.dto;

import lombok.Data;

@Data
public class FavoriteQuestionDTO {
	private int memberId;
    private String interviewQ;
    private String answer;
    private String feedback;
    private String category;
    private String jobTitle;
}
