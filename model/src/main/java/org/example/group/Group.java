package org.example.group;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.subject.Subject;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "students")
@Entity
@Table(name = "aaagroups")
public class Group {

  @Id
  @SequenceGenerator(name = "id_gen_g", sequenceName = "groups_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen_g")
  private int id;
  @OneToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "teacher_id", referencedColumnName = "id")
  private Teacher teacher;
  @ManyToMany()
  @JoinTable(
      name = "aaagroup_student",
      joinColumns = {@JoinColumn(name = "group_id")},
      inverseJoinColumns = {@JoinColumn(name = "student_id")}
  )
  private volatile Set<Student> students;
  @ElementCollection(targetClass = Subject.class)
  @CollectionTable(name = "aaagroups_subject",
      joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")})
  @Column(name = "subject")
  @Enumerated(EnumType.STRING)
  private volatile Set<Subject> subjects;

  public Group(int id, Teacher teacher, Set<Student> students, Set<Subject> subjects) {
    this.id = id;
    this.teacher = teacher;
    this.students = students;
    this.subjects = subjects;

    for (Student student : students) {
      for (Subject subject : subjects) {
        student.getRatings().putIfAbsent(subject, 0);
      }
    }
  }
}
