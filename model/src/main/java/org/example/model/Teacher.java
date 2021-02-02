package org.example.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Data
@Table(name = "teacher", schema = "public")
public class Teacher extends AbstractPerson {
  @Id
  @Column(name = "id")
  private int id;
  private String login;
  private String password;
  private String name;
  private int age;
  @Column(name = "salary")
  private String salary;
//  @OneToMany(cascade = CascadeType.ALL)
//  @JoinTable(
//      name = "salary",
//      joinColumns = @JoinColumn(name = "teacher_id"),
//      inverseJoinColumns = @JoinColumn(name = "salary")
//  )
  @ElementCollection
  @CollectionTable(name = "salary",
  joinColumns = @JoinColumn(name = "teacher_id"))
  private List<Double> salary1;

  public Teacher(int id, String login, String password, String name, int age,
      String salary, List<Double> salary1) {
    super(id, login, password, name, age);
    this.salary = salary;
    this.salary1 = salary1;
  }

}
