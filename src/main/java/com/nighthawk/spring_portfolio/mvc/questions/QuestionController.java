package com.nighthawk.spring_portfolio.mvc.questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/course/{courseName}")
    public List<Question> getQuestionsByCourse(@PathVariable String courseName) {
        return questionService.getQuestionsByCourse(courseName);
    }
    @GetMapping("/random/{courseName}")
    public Question getRandomQuestion(@PathVariable String courseName) {
        return questionService.getRandomQuestion(courseName);
    }
    

    // Other endpoints
}
