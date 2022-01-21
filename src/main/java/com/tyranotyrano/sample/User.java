package com.tyranotyrano.sample;

import lombok.Getter;

@Getter
public class User {
    private String name;
    private int age;

    private User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static User of(String name, int age) {
        return new User(name, age);
    }
}
