package test;

import java.sql.ResultSet;
import java.util.List;
import org.junit.Test;
import database.BeanDbConnector;
import database.DbConnector;
import database.IResultSetObjectCreator;
import database.IResultSetObjectCreatorImpl;
import pojo.User;

public class ResultSetObjectCreationTest {

  private DbConnector connector = new BeanDbConnector();
  private IResultSetObjectCreator injector = new IResultSetObjectCreatorImpl();

  @Test
  public void select_test() {
    ResultSet rs = connector.query(connector.getConnection(), "select * from user");
    try {
      List<User> users = injector.createResultSetObjecs(rs, User.class);
      users.forEach(System.out::println);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
