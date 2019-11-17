package com.geekbrains.septembermarket.services;

import com.geekbrains.septembermarket.entities.News;
import com.geekbrains.septembermarket.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    private NewsRepository newsRepository;

    @Autowired
    public void setNewsRepository(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public List<News> findAllByDate(LocalDateTime dateTime) {
        return newsRepository.findAllByCreatedAt(dateTime);
    }

    @Override
    public Optional<News> findOneByTitle(String title) {
        return newsRepository.findByTitle(title);
    }

    @Override
    public Optional<News> findOneById(Long id) {
        return newsRepository.findById(id);
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    public void delete(Long id) {
        if (findOneById(id).isPresent()){
            News news = findOneById(id).get();
            newsRepository.delete(news);
        }
    }
}
