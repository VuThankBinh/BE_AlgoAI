package com.nckh.algoai.dto;

import lombok.Data;
import java.util.List;

@Data
public class ExerciseSuggestionResponseDTO {
    private List<ExerciseDTO> codingExercises;
    private List<QuizQuestionDTO> quizQuestions;
    private String errorMessage;
}

@Data
class ExerciseDTO {
    private String title;
    private String description;
    private String difficultyLevel;
    private String programmingLanguage;
    private String expectedOutput;
    private List<String> testCases;
    private List<String> constraints;
    private String solution;
}

@Data
class QuizQuestionDTO {
    private String question;
    private List<String> options;
    private String correctAnswer;
    private String explanation;
} 