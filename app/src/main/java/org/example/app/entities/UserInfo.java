package org.example.app.entities;


import jakarta.persistence.*;
import lombok.*;


import java.util.Set;
import java.util.HashSet;


@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Getter
@Setter
//One user can have many roles
//One role can belong to many users

public class UserInfo {

    @Id
    @Column(name = "user_id")
    private String userId;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    //the join table acts like a bridge to handle Many-to-Many cleanly and keeps the database normalized.

    private Set<UserRole> roles = new HashSet<>();



}
