package org.example.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Data
@Table(name = "aaateacher", schema = "public")
public class Teacher extends AbstractPerson {

  @Id
  @SequenceGenerator(name = "id_gen_t", sequenceName = "teacher_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen_t")
  @Column(name = "id")
  private int id;
  @Column(name = "login")
  private String login;
  @Column(name = "password")
  private String password;
  @Column(name = "name")
  private String name;
  @Column(name = "age")
  private int age;
  @ElementCollection
  @CollectionTable(name = "aaasalary",
      joinColumns = @JoinColumn(name = "teacher_id"))
  @Column(name = "salary")
  private List<BigDecimal> salary;

  public Teacher(int id, String login, String password, String name, int age,
      List<BigDecimal> salary) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.name = name;
    this.age = age;
    this.salary = salary;
  }
}
