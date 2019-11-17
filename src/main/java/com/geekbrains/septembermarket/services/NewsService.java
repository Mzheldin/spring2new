package com.geekbrains.septembermarket.services;

import com.geekbrains.septembermarket.entities.News;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NewsService {
    List<News> findAll();
    List<News> findAllByDate(LocalDateTime dateTime);
    Optional<News> findOneByTitle(String title);
    Optional<News> findOneById(Long id);
    News save(News news);
    void delete(Long id);
}
