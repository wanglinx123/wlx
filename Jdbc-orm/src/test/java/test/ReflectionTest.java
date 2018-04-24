package test;

import java.util.Date;
import org.junit.Test;
import pojo.Pet;
import reflection.MethodReflection;

public class ReflectionTest {
  
  private Class<?> dogClz = Pet.class;
  
  @Test
  public void getValue_test() {
    Object obj =  MethodReflection.getValue(new Pet("zhi", new Date()), "birthday");
    java.sql.Date date = new java.sql.Date(new Date().getTime());
    System.out.println(date.toLocalDate() ); 
    System.out.println(obj.getClass() );
    System.out.println(obj ); 
  }
}
