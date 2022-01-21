package com.tyranotyrano.sample;

public class SampleMain {

    public static void main(String[] args) {
        User user = User.of("홍길동", 23);
        String token = JwtTokenFactory.createToken(user);

        System.out.println("token : " + token);
    }
}
