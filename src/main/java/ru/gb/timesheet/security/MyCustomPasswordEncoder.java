package ru.gb.timesheet.security;

import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

//@Component
//public class MyCustomPasswordEncoder implements PasswordEncoder {
//
//    @Override
//    public String encode(CharSequence rawPassword) {
////        return rawPassword.toString();
//        return BCrypt.hashpw(String.valueOf(rawPassword), BCrypt.gensalt());
//    }
//
//    @Override
//    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        return Objects.equals(encode(rawPassword), encodedPassword);
//    }
//}
