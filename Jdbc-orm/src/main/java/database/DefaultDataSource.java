package database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DefaultDataSource extends MysqlDataSource {
  public DefaultDataSource() {
    setUrl("jdbc:mysql://localhost:3306/pet");
    setEncoding("utf-8");
    setUser("root");
    setPassword("1995may16th");
  }
}
