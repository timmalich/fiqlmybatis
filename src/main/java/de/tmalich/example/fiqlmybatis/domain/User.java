package de.tmalich.example.fiqlmybatis.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
    public static final String PROPERTY_ID="id";
    public static final String PROPERTY_FIRST_NAME="firstName";
    public static final String PROPERTY_LAST_NAME="lastName";
    public static final String PROPERTY_EMAIL_ADDRESS="emailAddress";
    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
}
