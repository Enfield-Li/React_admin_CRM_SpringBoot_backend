package com.example.demo.repository;

import com.example.demo.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Integer> {
  public void deleteTagsByName(String name);
}
