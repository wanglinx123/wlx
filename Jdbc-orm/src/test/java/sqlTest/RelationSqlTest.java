package sqlTest;

import org.junit.Before;
import org.junit.Test;
import database.BeanDbConnector;
import database.DefaultDataSource;
import pojo.Pet;
import pojo.User;
import relationQuery.IRelationSqlGeneratorImpl;

public class RelationSqlTest {

  private IRelationSqlGeneratorImpl rs = new IRelationSqlGeneratorImpl();
  private BeanDbConnector connector = new BeanDbConnector();

  @Before
  public void init() {
    connector.setDataSource(new DefaultDataSource());
  }

  @Test
  public void one2ManyTest() throws NoSuchFieldException, SecurityException {
    User user = new User();
    user.setId(1L);

    rs.one2ManySql(user, user.getClass().getDeclaredField("pets"));
  }

  @Test
  public void many2OneTest() throws NoSuchFieldException, SecurityException {
    Pet pet = new Pet();
    pet.setId(1L);
    pet = connector.select(pet).get(0);
    
    String sql = rs.many2OneSql(pet, pet.getClass().getDeclaredField("user"));
    System.out.println(sql ); 
  }
  
  @Test
  public void one2OneTest() throws NoSuchFieldException, SecurityException {
    User user = new User();
    user.setId(1L);
    user = connector.select(user).get(0);
    
    String sql = rs.one2OneSql(user, user.getClass().getDeclaredField("dummy"));
    System.out.println(sql ); 
  }
}
