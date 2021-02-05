package org.example.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.example.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ToString
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student extends AbstractPerson {
  @Id
  private int id;
  private String login;
  private String password;
  private String name;
  private int age;
  @ElementCollection
  @CollectionTable(name = "rating",
      joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")})
  @MapKeyColumn(name = "subject")
  @Column(name = "rating")
  private final Map<String, Integer> ratings = new HashMap<>();
  private static final Logger log = LoggerFactory.getLogger(Student.class);

  public Student(int id, String login, String password, String name, int age,
      Set<String> subjects) {
    super(id, login, password, name, age);
    if (!StringUtils.isAlpha(name)) {
      setName("Petia");
    }
    //student start with rating 0
    for (String s : subjects) {
      ratings.put(s, 0);
    }
  }

  public Student(int id, String login, String password, String fullName, int age) {
    super(id, login, password, fullName, age);
    if (!StringUtils.isAlpha(fullName)) {
      setName("Petia");
    }
  }

  //учитель может добавить предмет и рейтинг
  public void putRating(String subject, int rating) {
    try {
      if (rating >= 0 && rating <= 100) {
        ratings.put(subject, rating);
      }
    } catch (IllegalArgumentException e) {
      log.error("rating or subject is not valid");
    }
  }

  public String getRatingAsString() {
    StringBuffer sb = new StringBuffer();
    sb.append(getName()).append(" has rating: ");
    ratings.forEach((k, v) -> sb.append(k).append(" - ").append(v).append(", "));
    sb.deleteCharAt(sb.lastIndexOf(","));
    return sb.toString();
  }

  public Map<String, Integer> getRatings() {
    return ratings;
  }
}
