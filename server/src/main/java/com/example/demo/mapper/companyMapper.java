package com.example.demo.mapper;

import com.example.demo.entity.Company;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface companyMapper {
  public List<Company> getFilteredCompany(
    @Param("start") Integer start,
    @Param("take") Integer take,
    @Param("sort") String sort,
    @Param("query") String query,
    @Param("order") String order,
    @Param("sales_id") Long sales_id,
    @Param("minSize") Integer minSize,
    @Param("maxSize") Integer maxSize,
    @Param("sector") String sector,
    @Param("searchText") String searchText
  );

  public String getCompanyCount(
    @Param("query") String query,
    @Param("sales_id") Long sales_id,
    @Param("minSize") Integer minSize,
    @Param("maxSize") Integer maxSize,
    @Param("sector") String sector,
    @Param("searchText") String searchText
  );

  public List<Company> getManyReferences(@Param("ids") List<Long> ids);
}
