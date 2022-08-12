package com.example.currencyconverter.service;

import com.example.currencyconverter.model.entity.User;

public interface UserService {
    User save(User user);
    User findByLogin(String login);
    User findByLoginAndPassword(String login, String password);
}
