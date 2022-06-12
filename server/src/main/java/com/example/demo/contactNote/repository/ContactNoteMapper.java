package com.example.demo.contactNote.repository;

import com.example.demo.contactNote.entity.ContactNote;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContactNoteMapper {
  List<ContactNote> getAllContactNotes(
    @Param("start") Integer start,
    @Param("take") Integer take,
    @Param("sort") String sort,
    @Param("order") String order,
    @Param("sales_id") String sales_id,
    @Param("contact_id") String contact_id
  );

  String getContactNoteCount(
    @Param("sales_id") String sales_id,
    @Param("contact_id") String contact_id
  );
}