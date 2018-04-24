package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import exception.NoGetterException;
import exception.NoSetterException;

public class MethodReflection {

  public static <T> void setValue(T obj, String fieldName, Object value) {
    try {
      Method setter = setterOf(fieldName, obj.getClass());
      setter.invoke(obj, value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static <T> void setValue(T obj, Field field, Object value) {
    setValue(obj, field.getName(), value);
  }

  public static <T> Object getValue(T obj, String fieldName) {
    try {
      Method getter = getterOf(fieldName, obj.getClass());
      return getter.invoke(obj);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("getting value");
    }
  }

  public static <T> Object getValue(T obj, Field field) {
    return getValue(obj, field.getName());
  }

  /**
   * Return the getter method of a {@code Field} field.
   *
   * @param field Filed
   * @param objClass Class of the Object
   * @return Method getter method
   */
  public static Method getterOf(Field field, Class<?> objClass) {
    try {
      String getterName = getterNameOf(field);
      return objClass.getMethod(getterName);
    } catch (Exception e) {
      throw new NoGetterException(objClass.getName() + " " + field.getName());
    }
  }

  public static Method getterOf(String fieldName, Class<?> objClass) {
    try {
      return getterOf(objClass.getDeclaredField(fieldName), objClass);
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }

  /**
   * Return the setter method of a {@code Field} field.
   *
   * @param field Filed
   * @param objClass Class of the Object
   * @return Method setter method
   */
  public static Method setterOf(Field field, Class<?> objClass) {
    try {
      String setterName = setterNameOf(field);
      return objClass.getMethod(setterName, field.getType());
    } catch (Exception e) {
      throw new NoSetterException(objClass.getName() + " " + field.getName());
    }
  }

  public static Method setterOf(String fieldName, Class<?> objClass) {
    try {
      return setterOf(objClass.getDeclaredField(fieldName), objClass);
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }

  /**
   * Return the getter method name of a {@code Field} field.
   *
   * @param field Filed
   * @return String getter name
   */
  private static String getterNameOf(Field field) {
    return getter_setter_name_of(field, "get");
  }

  private static String getterNameOf(String fieldName) {
    return getter_setter_name_of(fieldName, "get");
  }

  /**
   * Return the setter method name of a {@code Field} field.
   *
   * @param field Filed
   * @return String setter name
   */
  private static String setterNameOf(Field field) {
    return getter_setter_name_of(field, "set");
  }

  private static String setterNameOf(String fieldName) {
    return getter_setter_name_of(fieldName, "set");
  }

  private static String getter_setter_name_of(Field field, String type) {
    return getter_setter_name_of(field.getName(), type);
  }

  private static String getter_setter_name_of(String fieldName, String type) {
    StringBuilder str = new StringBuilder(type);
    str.append(fieldName.substring(0, 1).toUpperCase());
    if (fieldName.length() > 1) str.append(fieldName.substring(1));
    return str.toString();
  }
}
