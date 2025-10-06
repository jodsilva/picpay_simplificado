package com.api.users;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import com.api.enums.EnumUsersType;
import com.api.users.dto.UserCreateRequestDTO;
import com.api.users.dto.UserUpdateRequestDTO;
import com.api.wallets.WalletModel;

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

    @Column(name = "tax_id", nullable = false, unique = true)
    private String taxId;
    
    @Column(name="enum_users_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumUsersType enumUsersType;

    @Column(name="password", nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "user")
    private List<WalletModel> wallets;

    public UserModel(UserCreateRequestDTO userDTO){
        this.fullName = userDTO.getFullName();
        this.email = userDTO.getEmail();
        this.taxId = userDTO.getTaxId();
        this.enumUsersType = EnumUsersType.valueOf(userDTO.getType());
        this.password = userDTO.getPassword();
    }

    public void applyUpdateDTO(UserUpdateRequestDTO userDTO){
        userDTO.getFullName().ifPresent(value -> this.fullName = value);
        userDTO.getEmail().ifPresent(value -> this.email = value);
        userDTO.getTaxId().ifPresent(value -> this.taxId = value);
        userDTO.getType().ifPresent(value -> this.enumUsersType = EnumUsersType.valueOf(value));
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