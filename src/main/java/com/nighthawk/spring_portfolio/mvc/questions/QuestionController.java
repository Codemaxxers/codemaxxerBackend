package com.nighthawk.spring_portfolio.mvc.questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;
 
 
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }
 
    //Get a random question
    @GetMapping("/randomQuestion/{unit}")
    public Question getRandomQuestionByUnit(@PathVariable String unit) {
        // Fetch all questions for the given unit
        unit = unit.toLowerCase();
        List<Question> questions = questionRepository.findAllByUnit(unit);

        // Check if there are any questions for the given unit
        if (questions.isEmpty()) {
            // Handle the case when there are no questions for the given unit
            // For example, you can return an error message or throw an exception
            throw new RuntimeException("No questions found for unit: " + unit);
        }

        // Generate a random index within the range of the list size
        int randomIndex = new Random().nextInt(questions.size());

        // Return the randomly selected question
        return questions.get(randomIndex);
    }

    @GetMapping("/QuestionById/{id}")
    public Question getQuestionById(@PathVariable Integer id) {
        // Fetch all questions for the given unit
        List<Question> questions = questionRepository.findAllById(id);

        // Check if there are any questions for the given unit
        if (questions.isEmpty()) {
            // Handle the case when there are no questions for the given unit
            // For example, you can return an error message or throw an exception
            throw new RuntimeException("No question found for id: " + id);
        }
        
        return questions.get(id);
    }

    //Add Question
    @PostMapping("/makeQuestion")
    @PreAuthorize("isAuthenticated()")
    public Question makeQuestion(@RequestBody Question question) {
        return questionRepository.save(question);
    }
    
    //Delete Question
    @DeleteMapping("/deleteQuestion/{id}")
    @PreAuthorize("isAuthenticated()")
    public void deleteQuestion(@PathVariable Long id) {
        questionRepository.deleteById(id);
    }
}
