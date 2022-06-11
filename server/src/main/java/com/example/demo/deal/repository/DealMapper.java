package com.example.demo.deal.repository;

import com.example.demo.deal.entity.Deal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DealMapper {
  List<Deal> getCompanyDeals(
    @Param("start") Integer start,
    @Param("take") Integer take,
    @Param("sort") String sort,
    @Param("order") String order,
    @Param("type") String type,
    @Param("query") String query,
    @Param("sales_id") Long sales_id,
    @Param("company_id") Long company_id,
    @Param("stage") String stage
  );

  String getDealCount(
    @Param("type") String type,
    @Param("query") String query,
    @Param("sales_id") Long sales_id,
    @Param("company_id") Long company_id,
    @Param("stage") String stage
  );
}
