package com.example.demo.entity;

import java.util.Objects;
import javax.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

@Entity(name = "roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Integer roleId;

  @Column(nullable = false)
  private String name;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Role role = (Role) o;
    return roleId != null && Objects.equals(roleId, role.roleId);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
