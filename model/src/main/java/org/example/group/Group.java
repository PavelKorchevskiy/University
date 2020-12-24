package org.example.group;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Student;
import org.example.model.Teacher;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Group {

  private List<Student> students = new ArrayList<>();
  private Teacher teacher;
}
