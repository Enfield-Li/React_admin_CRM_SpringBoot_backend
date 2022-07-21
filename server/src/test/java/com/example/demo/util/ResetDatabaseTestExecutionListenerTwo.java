package com.example.demo.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

// https://stackoverflow.com/q/34617152/16648127
public class ResetDatabaseTestExecutionListenerTwo
  extends AbstractTestExecutionListener {

  @Autowired
  private DataSource dataSource;

  public final int getOrder() {
    return 2001;
  }

  private boolean alreadyCleared = false;

  @Override
  public void beforeTestClass(TestContext testContext) {
    testContext
      .getApplicationContext()
      .getAutowireCapableBeanFactory()
      .autowireBean(this);
  }

  @Override
  public void prepareTestInstance(TestContext testContext) throws Exception {
    if (!alreadyCleared) {
      cleanupDatabase();
      alreadyCleared = true;
    }
  }

  @Override
  public void afterTestClass(TestContext testContext) throws Exception {
    cleanupDatabase();
  }

  private void cleanupDatabase() throws SQLException {
    System.out.println("********************");
    Connection connection = dataSource.getConnection();
    Statement statement = connection.createStatement();

    // Disable FK
    boolean executed = statement.execute("SET REFERENTIAL_INTEGRITY FALSE");
    System.out.println(executed);

    // Find all tables and truncate them
    Set<String> tables = new HashSet<>();
    ResultSet resultSet = statement.executeQuery(
      "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES  where TABLE_SCHEMA='PUBLIC'"
    );
    while (resultSet.next()) {
      tables.add(resultSet.getString(1));
    }
    resultSet.close();
    for (String table : tables) {
      System.out.println("table: " + table);
      int executeUpdated = statement.executeUpdate("TRUNCATE TABLE " + table);
      System.out.println("executeUpdated: " + executeUpdated);
    }

    // Idem for sequences
    Set<String> sequences = new HashSet<>();
    resultSet =
      statement.executeQuery(
        "SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA='PUBLIC'"
      );
    while (resultSet.next()) {
      sequences.add(resultSet.getString(1));
    }
    resultSet.close();
    for (String seq : sequences) {
      statement.executeUpdate("ALTER SEQUENCE " + seq + " RESTART WITH 1");
    }

    // Enable FK
    statement.execute("SET REFERENTIAL_INTEGRITY TRUE");
    statement.close();
    connection.close();
  }
}
