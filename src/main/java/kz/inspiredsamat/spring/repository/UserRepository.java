package kz.inspiredsamat.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kz.inspiredsamat.spring.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}