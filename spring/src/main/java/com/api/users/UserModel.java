package com.api.users;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.api.users.dto.UserCreateRequestDTO;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "tax_id", nullable = false,unique = true)
    private String taxId;
    @Column(name="enum_users_type", nullable = false)
    private String enumUsersType;
    @Column(name="password", nullable = false)
    private String password;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column
    private LocalDateTime deletedAt;

    public UserModel(UserCreateRequestDTO userDTO){
        this.fullName = userDTO.getFullName();
        this.email = userDTO.getEmail();
        this.taxId = userDTO.getTaxId();
        this.enumUsersType = userDTO.getType();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }
}