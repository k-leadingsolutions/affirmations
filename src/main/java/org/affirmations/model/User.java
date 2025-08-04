package org.affirmations.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role; // "USER" or "ADMIN"

    public User(String username, String password, String role) {
        this.username = username; this.password = password; this.role = role;
    }
}