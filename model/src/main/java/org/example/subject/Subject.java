package org.example.subject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Subject {
  MATH("math"),
  CHEMISTRY("chemistry"),
  BIOLOGY("biology"),
  GEOGRAPHY("geography"),
  ART("art"),
  HISTORY("history");

  private final String name;
  private static final Map<Subject, String> subjectStringMap = initSubjectStringMap();
  private static final Map<String, Subject> stringSubjectMap = initStringSubjectMap();

  Subject(String name) {
    this.name = name;
  }

  private static Map<String, Subject> initStringSubjectMap() {
    Map<String, Subject> map = new HashMap<>();
    for (Subject subject : Subject.values()) {
      map.put(subject.toString(), subject);
      map.put(subject.name, subject);
    }
    return Collections.unmodifiableMap(map);
  }

  private static Map<Subject, String> initSubjectStringMap() {
    Map<Subject, String> map = new HashMap<>();
    for (Subject subject : Subject.values()) {
      map.put(subject, subject.name);
    }
    return Collections.unmodifiableMap(map);
  }

  public static Subject getSubjectByString(String name) throws IllegalArgumentException {
    Subject subject = stringSubjectMap.get(name);
    if (subject == null) {
      throw new IllegalArgumentException("Subject with this name doesn't exist");
    }
    return subject;
  }

  public static String getStringBySubject(Subject name) throws IllegalArgumentException {
    String subject = subjectStringMap.get(name);
    if (subject == null) {
      throw new IllegalArgumentException("It is strange if  you see this massage");
    }
    return subject;
  }


}
