package com.example.currencyconverter.repository;

import com.example.currencyconverter.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u join fetch u.role where u.login = :login")
    User findByLogin(String login);
}
