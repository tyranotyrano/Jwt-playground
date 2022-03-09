package com.tyranotyrano.domain.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;
    private String roles;

    private User(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public static User of(String username, String password, String roles) {
        return new User(username, password, roles);
    }

    public List<String> getRoles() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }

        return new ArrayList<>();
    }
}
