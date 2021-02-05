package org.example.group;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.subject.Sub;
import org.example.subject.Subject;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "groups")
public class Group {

  @Id
  private int id;
  @OneToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "teacher_id", referencedColumnName = "id")
  private Teacher teacher;
  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(
      name = "group_student",
      joinColumns = {@JoinColumn (name = "group_id")},
      inverseJoinColumns = {@JoinColumn(name = "student_id")}
  )
  private volatile Set<Student> students;
  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(
      name = "groups_subject",
      joinColumns = {@JoinColumn (name = "group_id")},
      inverseJoinColumns = {@JoinColumn(name = "subject_id")}
  )
  private volatile Set<Sub> subjects;

  public Group(int id, Teacher teacher, Set<Student> students, Set<Sub> subjects) {
    this.id = id;
    this.teacher = teacher;
    this.students = students;
    this.subjects = subjects;

    for (Student student : students) {
      for (Sub subject : subjects) {
        student.getRatings().putIfAbsent(subject.getName(), 0);
      }
    }
  }
}
