package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DealNote;

@Repository
public interface DealNoteRepository extends JpaRepository<DealNote, Long> {}
