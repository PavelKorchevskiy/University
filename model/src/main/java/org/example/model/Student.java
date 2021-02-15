package org.example.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.example.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
@Entity
@Data
@Table(name = "student")
@SequenceGenerator(name = "id_gen", sequenceName = "student_id_seq", allocationSize = 1)
public class Student extends AbstractPerson {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen")
  private int id;
  private String login;
  private String password;
  private String name;
  private int age;
  @ElementCollection()
  @CollectionTable(name = "rating",
      joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")})
  @MapKeyColumn(name = "subject")
  @MapKeyClass(Subject.class)
  @MapKeyEnumerated(EnumType.STRING)
  @Column(name = "rating")
  private final Map<Subject, Integer> ratings = new HashMap<>();

  private static final Logger log = LoggerFactory.getLogger(Student.class);

  public Student(int id, String login, String password, String name, int age,
      Set<Subject> subjects) {
    super(id, login, password, name, age);
    if (!StringUtils.isAlpha(name)) {
      setName("Petia");
    }
    //student start with rating 0
    for (Subject s : subjects) {
      ratings.put(s, 0);
    }
  }

  public Student(int id, String login, String password, String fullName, int age) {
    super(id, login, password, fullName, age);
    if (!StringUtils.isAlpha(fullName)) {
      setName("Petia");
    }
  }

  public Map<Subject, Integer> getRatings() {
    return ratings;
  }
}
