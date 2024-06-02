package com.example.URL_Shortener.repository;

import com.example.URL_Shortener.entity.EntityURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepositoryURL extends JpaRepository<EntityURL, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM urls WHERE shortURL = :shortURL")
    EntityURL findByShortURL(@Param("shortURL") String shortURL);

    @Query(nativeQuery = true, value = "SELECT * FROM urls WHERE finishDate >= :today")
    List<EntityURL> activeURL(@Param("today") LocalDate today);

    @Query(nativeQuery = true, value = "DELETE FROM urls WHERE shortURL = :shortURL")
    boolean deleteURL(@Param("shortURL") String shortURL);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE urls SET countUse = countUse + 1 WHERE shortURL = :shortURL")
    void increaseCount(@Param("shortURL") String shortURL);
}