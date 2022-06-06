package com.example.demo.dealNote.repository;

import com.example.demo.dealNote.entity.DealNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealNoteRepository extends JpaRepository<DealNote, Long> {}
