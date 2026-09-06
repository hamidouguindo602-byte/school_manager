package com.schoolmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = SchoolManagementApplication.class)
@ActiveProfiles("test")
class SchoolManagementApplicationTests {

  @Test
  void contextLoads() {}
}
