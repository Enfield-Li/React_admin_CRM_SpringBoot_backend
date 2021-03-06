package com.example.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
  @Query(
    value = "SELECT * FROM sale" +
    " WHERE CONCAT_WS(' ', first_name, last_name) = :fullName",
    nativeQuery = true
  )
  Optional<Sale> findByFullName(@Param("fullName") String fullName);
}
