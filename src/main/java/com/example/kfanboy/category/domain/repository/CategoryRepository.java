package com.example.kfanboy.category.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kfanboy.category.domain.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
