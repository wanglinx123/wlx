package pojo;

import annotation.JoinColumn;
import annotation.Relation;
import static enumeration.EntityRelationEnum.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dummy {
  
  @Relation(ID)
  private Long id;
  @Relation(One2One)
  @JoinColumn(value = "userid", table = "dummy")
  private User user;
}
