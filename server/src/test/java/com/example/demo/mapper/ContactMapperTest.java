package com.example.demo.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureMybatis
public class ContactMapperTest {

  @Test
  void testGetCompanyContacts() {}

  @Test
  void testGetContactById() {}

  @Test
  void testGetContactCount() {}

  @Test
  void testGetContactsByIds() {}
}
