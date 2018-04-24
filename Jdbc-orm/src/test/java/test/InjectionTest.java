package test;

import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import database.RelationObjectInjector;
import pojo.Pet;
import pojo.User;

public class InjectionTest {
  
  private RelationObjectInjector injector = new RelationObjectInjector();
  @Test
  public void injectWithListTest() throws NoSuchFieldException, SecurityException {
    User user = new User();
    List<Pet> pets = new ArrayList<>();
    pets.add(new Pet());
    injector.injectObject(user, user.getClass().getDeclaredField("pets"), pets);
    user.getPets().forEach(System.out::println);
    
    assertNotNull(user.getPets());
  }
  
  @Test
  public void injectWithSingleObjectTest() throws NoSuchFieldException, SecurityException {
    Pet pet = new Pet();
    User user = new User("asdjfl");
    
    injector.injectObject(pet, pet.getClass().getDeclaredField("user"), user);
    System.out.println(pet.getUser() ); 
    assertNotNull(pet.getUser());
  }
  
}
