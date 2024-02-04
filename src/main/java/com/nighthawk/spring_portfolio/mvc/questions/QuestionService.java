package com.nighthawk.spring_portfolio.mvc.questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonJpaRepository;

import java.util.stream.Collectors;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Autowired
    private CustomQuestionRepository customQuestionRepository;

    public List<Question> getQuestionsByCourse(String courseName) {
        return customQuestionRepository.findByCourse(courseName);
    }

    public Question getRandomQuestion(String courseName) {
        return customQuestionRepository.findRandomQuestion(courseName);
    }

    @Autowired
    private PersonJpaRepository personJpaRepository;

    public Question getNewRandomQuestionForUser(Long userId, String courseName) {
    Person person = personJpaRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    List<Long> solvedQuestionIds = person.getSolvedQuestions().stream()
                                         .map(Question::getId)
                                         .collect(Collectors.toList()); // Changed to toList()

    return customQuestionRepository.findRandomQuestionExcluding(courseName, solvedQuestionIds);
}

    // Other business logic methods
}
