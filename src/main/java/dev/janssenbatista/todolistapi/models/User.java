package dev.janssenbatista.todolistapi.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(length = 70, nullable = false, unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
