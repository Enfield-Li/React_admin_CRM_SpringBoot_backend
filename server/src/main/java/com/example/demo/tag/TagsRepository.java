package com.example.demo.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.tag.entity.Tags;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Integer> {}
