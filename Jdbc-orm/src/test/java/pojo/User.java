package pojo;

import java.util.List;
import static enumeration.EntityRelationEnum.*;
import annotation.JoinColumn;
import annotation.Relation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Relation(ID)
  private Long id;

  private String name;

  @Relation(One2Many)
  @JoinColumn("userid")
  private List<Pet> pets;

  public User(String name) {
    this.name = name;
  }
}
