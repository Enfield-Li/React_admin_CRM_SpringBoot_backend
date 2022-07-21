package com.example.demo.mapper;

import com.example.demo.entity.Tags;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TagsMapper {
  List<Tags> getTagsByIds(@Param("ids") List<Integer> ids);

  Tags getTagsById(Integer id);
}
