package com.geekbrains.septembermarket.repositories;

import com.geekbrains.septembermarket.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByCreatedAt(LocalDateTime localDateTime);
    Optional<News> findByTitle(String title);
}
