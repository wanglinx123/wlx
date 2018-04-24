package sqlBuilder;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import annotation.JoinColumn;
import annotation.Relation;
import conainer.Pair;
import enumeration.EntityRelationEnum;
import exception.NoIdAnnotationException;
import exception.NoJoinColumnException;
import reflection.MethodReflection;

public class SqlParams {

  private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

  public static <T> Map<String, String> nonNullParams(T obj) throws Exception {
    Map<String, String> result = nonRelationParams(obj);

    return result
        .entrySet()
        .stream()
        .filter(e -> !e.getValue().equals("null"))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  public static <T> Map<String, String> nonRelationParams(T obj) throws Exception {
    Field[] fields = obj.getClass().getDeclaredFields();
    Map<String, String> result = new HashMap<>();

    for (Field f : fields) {
      if (isRelationField(f)) continue;
      Object val = MethodReflection.getValue(obj, f);
      if (val instanceof Date) val = formatter.format(val);
      if (val instanceof String) val = String.format("'%s'", val);
      result.put(f.getName(), val == null ? "null" : val.toString());
    }
    return result;
  }

  public static <T> List<Field> relationFields(T obj) {
    return relationFields(obj.getClass());
  }
  
  public static <T> List<Field>relationFields(Class<T> clz) {
    Field[] fields = clz.getDeclaredFields();
    List<Field> result = new ArrayList<>();

    for (Field f : fields) {
      if(isRelationField(f))    result.add(f);
    }
    
    return result;
  } 
  
  public static EntityRelationEnum getRelationTypeEnum(Field field) {
    return field.getAnnotation(Relation.class).value();
  }

  public static boolean isRelationField(Field field) {
    Relation r = field.getAnnotation(Relation.class);
    return r != null && r.value() != EntityRelationEnum.ID;
  }

  public static boolean isPrimaryKey(Field field) {
    Relation r = field.getAnnotation(Relation.class);
    return r != null && r.value() == EntityRelationEnum.ID;
  }
  
  public static String getJoinColumn(Field field) {
    JoinColumn joinColumn =  field.getAnnotation(JoinColumn.class);
    if(joinColumn == null || joinColumn.value() == null) throw new NoJoinColumnException(field.getName());
    return joinColumn.value();
  }
  
  public static String getJoinTable(Field field) {
    JoinColumn joinColumn =  field.getAnnotation(JoinColumn.class);
    if(joinColumn != null && !joinColumn.table().equals("") )
      return joinColumn.table();
    return null;
  }

  public static <T> Pair<String, Object> getPrimaryKey(T obj) {
    Field[] fields = obj.getClass().getDeclaredFields();
    Pair<String, Object> pair = null;

    try {
      for (Field f : fields) {
        if (isPrimaryKey(f))
          pair = new Pair<String, Object>(f.getName(), MethodReflection.getValue(obj, f));
      }
      if(pair == null) throw new NoIdAnnotationException(obj.getClass().getName());
      return pair;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  
  public static <T> Pair<String, Object> getPrimaryKey(Class<T> clz){
    try {
      return getPrimaryKey(clz.newInstance());
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }
  
  public static String getTableName(Class<?> clz) {
    return clz.getSimpleName();
  }
  
  public static String getTableName(Object obj) {
    return obj.getClass().getSimpleName();
  }
  
  public static <T> String getPrimaryKeyValue(T obj) {
    return getPrimaryKey(obj).getSecond().toString();
  }
  
  public static <T> String getPrimaryKeyName(T obj) {
    return getPrimaryKey(obj).getFirst();
  }
  
  public static <T> String getPrimaryKeyName(Class<T> clz) {
    return getPrimaryKey(clz).getFirst();
  }
}
