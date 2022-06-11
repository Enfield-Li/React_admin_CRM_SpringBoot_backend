package com.example.demo.contact.repository;

import com.example.demo.contact.entity.Contact;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContactMapper {
  List<Contact> getCompanyContacts(
    @Param("start") Integer start,
    @Param("take") Integer take,
    @Param("sort") String sort,
    @Param("order") String order,
    @Param("status") String status,
    @Param("sales_id") Long sales_id,
    @Param("last_seen_gte") String last_seen_gte,
    @Param("last_seen_lte") String last_seen_lte,
    @Param("company_id") Long company_id,
    @Param("query") String query
  );

  String getContactCount(
    @Param("status") String status,
    @Param("sales_id") Long sales_id,
    @Param("company_id") Long company_id,
    @Param("last_seen_gte") String last_seen_gte,
    @Param("last_seen_lte") String last_seen_lte,
    @Param("query") String query
  );

  List<Contact> getContactsByIds(@Param("ids") List<Long> ids);
}
