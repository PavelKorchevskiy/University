package org.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractPerson implements Person {
    private String login;
    private String password;
    private String fullName;
    private int age;
}
