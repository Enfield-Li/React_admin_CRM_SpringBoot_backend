package com.example.demo.contactNote.repository;

import com.example.demo.contactNote.entity.ContactNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactNoteRepository
  extends JpaRepository<ContactNote, Long> {}
