package com.example.demo.dealNote.repository;

import com.example.demo.dealNote.entity.DealNote;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
