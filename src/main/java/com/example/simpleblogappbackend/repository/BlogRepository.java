package com.example.simpleblogappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.simpleblogappbackend.models.Blog;

@Service
public interface BlogRepository extends JpaRepository<Blog, Integer> {
}
