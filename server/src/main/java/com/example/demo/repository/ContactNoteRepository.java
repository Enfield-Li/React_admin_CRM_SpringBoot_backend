package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ContactNote;

@Repository
public interface ContactNoteRepository
  extends JpaRepository<ContactNote, Long> {}
