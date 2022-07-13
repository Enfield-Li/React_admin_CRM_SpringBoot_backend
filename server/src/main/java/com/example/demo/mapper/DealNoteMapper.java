package com.example.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.DealNote;

@Mapper
public interface DealNoteMapper {
  List<DealNote> getNotesByDealId(
    @Param("start") Integer start,
    @Param("take") Integer take,
    @Param("sort") String sort,
    @Param("order") String order,
    @Param("deal_id") Long deal_id
  );

  String getDealNoteCount(@Param("deal_id") Long deal_id);
}
