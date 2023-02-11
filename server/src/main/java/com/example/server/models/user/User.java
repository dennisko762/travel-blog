package com.example.server.models.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Getter
    @Setter
    @Column(name = "username")
    private String username;
    @Getter
    @Setter
    @Column(name = "password")
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter

    private Long id;

    @Getter
    @Setter
    @Column(name = "role")
    private Role role;

    public User(String username, String password, Role role) {
        this.password = password;
        this.username = username;
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User employee = (User) o;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.username, employee.username)
                && Objects.equals(this.role, employee.role) && Objects.equals(this.password, employee.password);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.username, this.role, this.password);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", username='" + this.username + '\'' + ", role='" + this.role + '\'' + '}';
    }

    public void setId(Long id) {
        this.id = id;
    }
}


