package com.example.demo.deal.repository;

import com.example.demo.deal.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRespository extends JpaRepository<Deal, Long> {}
