package test;

import java.util.List;
import org.junit.Test;
import database.BeanDbConnector;
import pojo.Pet;
import pojo.User;

public class BeanDbTest {
  private BeanDbConnector connector = new BeanDbConnector();
  
  @Test
  public void selectOne2ManyTest() {
    List<User> users =  connector.selectAll(User.class);
    users.forEach(System.out::println);
  }
  @Test
  public void selectMany2OneTest() {
    List<Pet> pets = connector.selectAll(Pet.class);
    pets.forEach(System.out::println);
  }
}
