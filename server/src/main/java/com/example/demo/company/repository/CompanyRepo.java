package com.example.demo.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.company.entity.Company;
@Repository
public interface CompanyRepo extends JpaRepository<Company, Long>{}
