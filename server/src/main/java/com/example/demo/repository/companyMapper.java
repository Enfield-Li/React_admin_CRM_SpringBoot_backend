package com.example.demo.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Company;

@Mapper
public interface companyMapper {
  public List<Company> getFilteredCompany(
    @Param("start") Integer start,
    @Param("take") Integer take,
    @Param("sort") String sort,
    @Param("query") String query,
    @Param("order") String order,
    @Param("sales_id") Long sales_id,
    @Param("sizeMin") Integer sizeMin,
    @Param("sizeMax") Integer sizeMax,
    @Param("sector") String sector,
    @Param("searchText") String searchText
  );

  public String getCompanyCount(
    @Param("query") String query,
    @Param("sales_id") Long sales_id,
    @Param("sizeMin") Integer sizeMin,
    @Param("sizeMax") Integer sizeMax,
    @Param("sector") String sector,
    @Param("searchText") String searchText
  );

  public List<Company> getManyReferences(@Param("ids") List<Long> ids);
}
