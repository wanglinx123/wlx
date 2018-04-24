package database;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import reflection.MethodReflection;

/**
 * Inject Relational Object (List or Single Object) into target Object.
 *
 */
public class RelationObjectInjector {

  public <T> void injectObject(T obj, Field field, Object relation) {
    if (relation == null) return;

    try {
      Method setter = MethodReflection.setterOf(field, obj.getClass());
      setter.invoke(obj, relation);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
