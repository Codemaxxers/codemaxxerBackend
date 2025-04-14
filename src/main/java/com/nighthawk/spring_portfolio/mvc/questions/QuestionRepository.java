package com.nighthawk.spring_portfolio.mvc.questions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
 
    @Query("SELECT q FROM Question q WHERE q.unit = :unit")
    List<Question> findAllByUnit(@Param("unit") String unit);

    @Query("SELECT q FROM Question q WHERE q.id = :id")
    List<Question> findAllById(@Param("id") Integer id);
}
