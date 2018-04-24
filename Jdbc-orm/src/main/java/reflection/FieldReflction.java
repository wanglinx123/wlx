package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class FieldReflction {

  public static Class<?> getGenericType(Field field) {
    return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
  }

  /**
   * Get Bean Type of the Field.<br>
   * {@code List<User> -> User} <br>
   * {@code User -> User}.<br>
   * @param field
   * @return
   */
  public static Class<?> getBeanTypeOfField(Field field) {
    if (field.getType() == List.class) return getGenericType(field);
    return field.getType();
  }
}
