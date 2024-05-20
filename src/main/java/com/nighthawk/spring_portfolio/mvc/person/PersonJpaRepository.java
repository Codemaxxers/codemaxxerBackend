package com.nighthawk.spring_portfolio.mvc.person;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/*
Extends the JpaRepository interface from Spring Data JPA.
-- Java Persistent API (JPA) - Hibernate: map, store, update and retrieve database
-- JpaRepository defines standard CRUD methods
-- Via JPA the developer can retrieve database from relational databases to Java objects and vice versa.
 */
public interface PersonJpaRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);

    Person findByName(String name);

    List<Person> findAllByOrderByNameAsc();

    // JPA query, findBy does JPA magic with "Name", "Containing", "Or", "Email", "IgnoreCase"
    List<Person> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

    /* Custom JPA query articles, there are articles that show custom SQL as well
       https://springframework.guru/spring-data-jpa-query/
       https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
    */
    Person findByEmailAndPassword(String email, String password);

    // Custom JPA query
    @Query(
            value = "SELECT * FROM Person p WHERE p.name LIKE ?1 or p.email LIKE ?1",
            nativeQuery = true)
    List<Person> findByLikeTermNative(String term);
    /*
      https://www.baeldung.com/spring-data-jpa-query
    */

    List<Person> findTop5ByOrderByCspPointsDesc();

    List<Person> findTop5ByOrderByCsaPointsDesc();

    List<Person> findTop5ByOrderByCyberPointsDesc();

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
>>>>>>> 70fe2a2 (keys collected get games played post)
<<<<<<< HEAD
>>>>>>> f25cfd0 (keys collected get games played post)
=======
=======
>>>>>>> a9fc670 (keys collected get games played post)
<<<<<<< HEAD
>>>>>>> f3a7030 (keys collected get games played post)
=======
=======
=======
>>>>>>> 70fe2a2 (keys collected get games played post)
>>>>>>> 4e117c2 (keys collected get games played post)
>>>>>>> f937f0d (keys collected get games played post)
    List<Person> findTop5ByOrderByGamesPlayedDesc();

    List<Person> findTop5ByOrderByKeysCollectedDesc();

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 4e117c2 (keys collected get games played post)
=======
>>>>>>> 821fd7d (data base)
<<<<<<< HEAD
>>>>>>> 732d469 (data base)
=======
=======
>>>>>>> 70fe2a2 (keys collected get games played post)
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> f25cfd0 (keys collected get games played post)
=======
=======
>>>>>>> f937f0d (keys collected get games played post)
=======
>>>>>>> e88d8a6 (data base)
<<<<<<< HEAD
>>>>>>> 01cc54d (data base)
=======
=======
>>>>>>> a9fc670 (keys collected get games played post)
<<<<<<< HEAD
>>>>>>> f3a7030 (keys collected get games played post)
=======
=======
>>>>>>> 4e117c2 (keys collected get games played post)
>>>>>>> f937f0d (keys collected get games played post)
=======
>>>>>>> 5056e63 (data base)
    // You can use no query or query

    @Query("SELECT p FROM Person p ORDER BY p.cspPoints DESC")
    List<Person> findTop5ByCspPoints();

    @Query("SELECT p FROM Person p ORDER BY p.csaPoints DESC")
    List<Person> findTop5ByCsaPoints();

    @Query("SELECT p FROM Person p ORDER BY p.cyberPoints DESC")
    List<Person> findTop5ByCyberPoints();

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
>>>>>>> 70fe2a2 (keys collected get games played post)
>>>>>>> f25cfd0 (keys collected get games played post)
    @Query("SELECT p FROM Person p ORDER BY p.gamesPlayed DESC")
    List<Person> findByGamesPlayed();

    @Query("SELECT p FROM Person p ORDER BY p.keysCollected DESC")
    List<Person> findByKeysCollected();

<<<<<<< HEAD
=======
>>>>>>> 821fd7d (data base)
<<<<<<< HEAD
>>>>>>> 732d469 (data base)
=======
=======
>>>>>>> 70fe2a2 (keys collected get games played post)
<<<<<<< HEAD
>>>>>>> f25cfd0 (keys collected get games played post)
=======
=======
>>>>>>> e88d8a6 (data base)
<<<<<<< HEAD
>>>>>>> 01cc54d (data base)
=======
=======
=======
=======
>>>>>>> 70fe2a2 (keys collected get games played post)
>>>>>>> 4e117c2 (keys collected get games played post)
    @Query("SELECT p FROM Person p ORDER BY p.gamesPlayed DESC")
    List<Person> findTop5ByGamesPlayed();

    @Query("SELECT p FROM Person p ORDER BY p.keysCollected DESC")
    List<Person> findTop5ByKeysCollected();

<<<<<<< HEAD
>>>>>>> a9fc670 (keys collected get games played post)
<<<<<<< HEAD
>>>>>>> f3a7030 (keys collected get games played post)
=======
=======
<<<<<<< HEAD
=======
>>>>>>> 821fd7d (data base)
=======
>>>>>>> 70fe2a2 (keys collected get games played post)
>>>>>>> 4e117c2 (keys collected get games played post)
>>>>>>> f937f0d (keys collected get games played post)
=======
>>>>>>> 5056e63 (data base)
}




