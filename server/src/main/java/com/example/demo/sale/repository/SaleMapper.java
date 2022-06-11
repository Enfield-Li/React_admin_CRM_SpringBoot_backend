package com.example.demo.sale.repository;

import com.example.demo.sale.entity.Sale;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SaleMapper {
  @Select(
    "SELECT * FROM sale" +
    " ORDER BY ${sort} ${order}" +
    " LIMIT #{take}" +
    " OFFSET #{start}"
  )
  List<Sale> getAllSales(
    @Param("start") Integer start,
    @Param("take") Integer take,
    @Param("sort") String sort,
    @Param("order") String order
  );

  @Select("SELECT count(*) FROM sale")
  String getSaleCount();
}
