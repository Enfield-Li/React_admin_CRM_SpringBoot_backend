package com.example.demo.company.repository;

import com.example.demo.company.entity.Company;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface companyMapper {
  public List<Company> getFilteredCompany(
    @Param("start") Integer start,
    @Param("take") Integer take,
    @Param("sort") String sort,
    @Param("order") String order,
    @Param("sales_id") Long sales_id,
    @Param("sizeMin") Integer sizeMin,
    @Param("sizeMax") Integer sizeMax,
    @Param("sector") String sector,
    @Param("searchText") String searchText
  );

  public String getCompanyCount(
    @Param("sales_id") Long sales_id,
    @Param("sizeMin") Integer sizeMin,
    @Param("sizeMax") Integer sizeMax,
    @Param("sector") String sector,
    @Param("searchText") String searchText
  );
}
