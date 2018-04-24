package test;

import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import database.DbConnector;
import pojo.Pet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class DbTest {

  private DataSource source;
  private DbConnector dbUtil;

  @Before
  public void init() {
    dbUtil = new DbConnector();
    source = dbUtil.getDataSource();
  }
  
  @Test
  public void query_test() {
    try (Connection conn = source.getConnection();
        ResultSet rs = dbUtil.query(conn, "select * from pet");
        Statement statement = rs.getStatement()) {
      while (rs.next()) {
        String name = rs.getString("name");
        Date birthday = rs.getDate("birthday");
        Pet dog = new Pet(name, birthday);
        System.out.println(dog);
      }
    } catch (Exception e) {
    }
  }

}
