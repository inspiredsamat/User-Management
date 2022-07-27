package kz.inspiredsamat.spring.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(name = "login_time")
    private LocalDateTime lastLoginTime;

    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

    @Enumerated
    private Status status;

    public User(String name, String password, String email, LocalDateTime registrationTime) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.registrationTime = registrationTime;
        this.status = Status.ACTIVE;
    }

    public String getRegistrationTime() {
        return registrationTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    public String getLastLoginTime() {
        return lastLoginTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}