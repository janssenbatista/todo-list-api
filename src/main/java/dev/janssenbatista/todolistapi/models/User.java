package dev.janssenbatista.todolistapi.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(length = 70, nullable = false)
    private String username;
    private String password;
    private String email;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
