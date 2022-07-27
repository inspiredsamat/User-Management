package kz.inspiredsamat.spring.service;

import java.util.List;

import kz.inspiredsamat.spring.model.User;

public interface UserService {
    List<User> getUsers();

    void save(User newUser);

    void updateLoginTime();

    String delete(Long id);

    String block(Long id);

    void unblock(Long id);
}