package org.example.service;

import org.example.group.Group;
import org.example.model.Student;
import org.example.model.Teacher;
import org.example.repository.producer.GroupProducer;
import org.example.repository.producer.StudentProducer;
import org.example.repository.producer.TeacherProducer;

public class SaveService {

  public static Student saveStudent(Student student) {
    return StudentProducer.getRepository().save(student);
  }

  public static Teacher saveTeacher(Teacher teacher) {
    return TeacherProducer.getRepository().save(teacher);
  }

  public static Group saveGroup(Group group) {
    return GroupProducer.getRepository().save(group);
  }

}
