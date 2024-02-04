package com.nighthawk.spring_portfolio.mvc.questions;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

import java.util.List;

@Repository
public class CustomQuestionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Question> findByCourse(String courseName) {
        String sql = "SELECT * FROM " + courseName; // Ensure courseName is validated or mapped
        Query query = entityManager.createNativeQuery(sql, Question.class);
        return query.getResultList();
    }

    public Question findRandomQuestion(String courseName) {
        String sql = "SELECT * FROM " + courseName + " ORDER BY RANDOM() LIMIT 1"; 
        Query query = entityManager.createNativeQuery(sql, Question.class);
        return (Question) query.getSingleResult();
    }

    public Question findRandomQuestionExcluding(String courseName, List<Long> excludingIds) {
        String excludingIdsString = excludingIds.stream()
                                                .map(String::valueOf)
                                                .collect(Collectors.joining(","));

        String sql = "SELECT * FROM " + courseName + " WHERE id NOT IN (" + excludingIdsString + ") ORDER BY RANDOM() LIMIT 1";
        Query query = entityManager.createNativeQuery(sql, Question.class);

        return (Question) query.getSingleResult();
    }
}
