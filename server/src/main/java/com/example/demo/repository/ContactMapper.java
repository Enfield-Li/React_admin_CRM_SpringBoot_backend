package com.example.demo.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Contact;

@Mapper
public interface ContactMapper {
  @Select(
    "SELECT c.*," +
    " group_concat( ct.tag_id ) AS raw_tags" +
    " FROM contact c" +
    " LEFT JOIN contact_tag ct" +
    " ON ct.contact_id = c.id" +
    " WHERE c.id = #{id}" +
    " GROUP BY c.id"
  )
  Contact getContactById(@Param("id") Long id);

  List<Contact> getCompanyContacts(
    @Param("start") Integer start,
    @Param("take") Integer take,
    @Param("sort") String sort,
    @Param("order") String order,
    @Param("status") String status,
    @Param("tags") String tags,
    @Param("sales_id") Long sales_id,
    @Param("last_seen_gte") String last_seen_gte,
    @Param("last_seen_lte") String last_seen_lte,
    @Param("company_id") Long company_id,
    @Param("query") String query
  );

  String getContactCount(
    @Param("status") String status,
    @Param("tags") String tags,
    @Param("sales_id") Long sales_id,
    @Param("company_id") Long company_id,
    @Param("last_seen_gte") String last_seen_gte,
    @Param("last_seen_lte") String last_seen_lte,
    @Param("query") String query
  );

  List<Contact> getContactsByIds(@Param("ids") List<Long> ids);
}
