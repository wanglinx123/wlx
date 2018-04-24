package sqlTest;

import static org.junit.Assert.assertSame;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import database.BeanDbConnector;
import database.DefaultDataSource;
import pojo.Pet;
import pojo.User;
import sqlBuilder.ISqlBuilderImpl;

public class BeanSqlTest {
  
  private  ISqlBuilderImpl builder = new ISqlBuilderImpl();
  private BeanDbConnector connector = new BeanDbConnector();
  
  @Before
  public void init() {
    connector.setDataSource(new DefaultDataSource());
  }
  
  @Test
  public void select_test() throws Exception {
    Pet pet = new Pet(null, null, null, null);
    List<Pet> pets =  connector.select(pet);
    pets.forEach(System.out::println);
  }
  @Test
  public void select_id_test() {
    Pet pet = new Pet(1L, null, null, new User("nonono"));
    List<Pet> pets =  connector.select(pet);
    pets.forEach(System.out::println);
  }
  
  @Test
  public void update_test() {
    Pet pet = new Pet(7L,  "updateeeee", null, new User("nonono"));
    int result =  connector.update(pet);
    assertSame(1, result);
  }
  
  @Test
  public void insert_test() {
    Pet pet = new Pet(null,  "newwww", new Date(), null);
    int result =  connector.insert(pet);
    assertSame(1, result);
  }
  
  @Test
  public void delete_test() {
    Pet pet = new Pet();
    pet.setId(7L);
    int result = connector.delete(pet);
    assertSame(1, result);
  }
}
