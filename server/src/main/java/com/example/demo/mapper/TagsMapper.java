package com.example.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Tags;

@Mapper
public interface TagsMapper {
  List<Tags> getTagsByIds(
    @Param("ids") List<Long> ids
  );
}
