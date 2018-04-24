package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import exception.SqlUpdateException;

public class DbConnector {
  
  private DataSource dataSource = new DefaultDataSource();

  
  public void setDataSource(DataSource source) {
    this.dataSource = source;
  }
  public DataSource getDataSource() {
     return dataSource;
  }
  
  public Connection getConnection() {
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("getting connection");
    }
  }

  public ResultSet query(Connection conn, String sql, Object... params) {
    try {
      PreparedStatement ps = getSql(conn, sql, params);
      return ps.executeQuery();
    } catch (SQLException e) {
      throw new SqlUpdateException(sql);
    }
  }

  public int executeUpdate(Connection conn, String sql, Object... params) {
    try (PreparedStatement ps = getSql(conn, sql, params)) {
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw new SqlUpdateException(sql);
    }
  }

  private PreparedStatement getSql(Connection conn, String sql, Object... params)
      throws SQLException {
    PreparedStatement ps = conn.prepareStatement(sql);
    for (int i = 0; i < params.length; i++) ps.setObject(i + 1, params[i]);
    return ps;
  }

  /**
   * Mannually close a resource, suggest using try-with blocks.
   *
   * @param c that implements AutoCloseable
   */
  public void close(AutoCloseable c) {
    try {
      c.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
