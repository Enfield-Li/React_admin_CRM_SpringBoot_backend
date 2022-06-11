package com.example.demo.tag;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TagsMapper {
  @Select(
    "SELECT * FROM tags"
    // +  " ORDER BY ${sort} ${order}" +
    // " LIMIT #{take}" +
    // " OFFSET #{start}"
  )
  List<Tags> getAllTags(
    // @Param("start") Integer start,
    // @Param("take") Integer take,
    // @Param("sort") String sort,
    // @Param("order") String order
  );
}
