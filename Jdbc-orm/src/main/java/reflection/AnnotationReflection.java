package reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class AnnotationReflection {

  public static <T extends Annotation>  Optional<?> getMetaAnnotationType(Field field, Class<T> annoType) {
    return Arrays.stream(field.getAnnotations())
            .map(e -> e.annotationType())
            .filter(e -> e.getAnnotation(annoType) != null)
            .findFirst();
  }
}
