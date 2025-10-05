package com.api.users;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.api.users.dto.UserCreateRequestDTO;
import com.api.users.dto.UserFilterRequestDTO;
import com.api.users.dto.UserResponseDTO;
import com.api.users.dto.UserUpdateRequestDTO;
import com.api.wallets.WalletService;
import com.api.wallets.dto.WalletCreateDTO;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    public UserModel findUserOrFail(Long id){
        return this.repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    /**
     * Create a new user using a data as UserCreateQuestDTO
     * 
     * @param userDTO
     * @return UserResponseDTO
     */

    @Transactional
    public UserModel create(UserCreateRequestDTO userDTO){ 
        UserModel user = new UserModel(userDTO);
        user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        this.repository.save(user);

        this.walletService.create(new WalletCreateDTO(
            user, 
            0.0, 
            EnumCurrency.BRL.name(),
            true
        ));

        return user;
    }

    /**
     * Update a user by id
     * 
     * @param id
     * @return UserResponseDTO
     */
    public UserResponseDTO update(Long id, UserUpdateRequestDTO userDTO){ 
        var user = this.findUserOrFail(id);
        user.applyUpdateDTO(userDTO);
        this.repository.save(user);
        return new UserResponseDTO(user);
    }

    /**
     * Delete a user by id
     * @param id
     */
    public void delete(Long id){
        repository.delete(this.findUserOrFail(id));
    }

    /**
     * Get a list of users with filters
     * 
     * @param filters
     * @return
     */
    public List<UserResponseDTO> getList(UserFilterRequestDTO filters) {
        List<String> queryFilters = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        for (Field field : UserFilterRequestDTO.class.getDeclaredFields()) {
            Object value;
            try {
                value = field.get(filters);
            } catch (IllegalAccessException e) {
                continue;
            }

            if (value != null) {
                switch (field.getName()) {
                    case "fullName" -> {
                        queryFilters.add("u.fullName LIKE :fullName");
                        params.put("fullName", "%" + value + "%");
                    }
                    case "email" -> {
                        queryFilters.add("u.email LIKE :email");
                        params.put("email", "%" + value + "%");
                    }
                    case "type" -> {
                        queryFilters.add("u.enumUsersType = :type");
                        params.put("type", value); // equality para enum
                    }
                    case "taxId" -> {
                        queryFilters.add("u.taxId LIKE :taxId");
                        params.put("taxId", "%" + value + "%");
                    }
                }
            }
        }

        StringBuilder builder = new StringBuilder(
            "SELECT new com.api.users.dto.UserResponseDTO(u.id, u.fullName, u.email, u.taxId, u.enumUsersType) FROM UserModel u"
        );

        if (!queryFilters.isEmpty()) {
            builder.append(" WHERE ").append(String.join(" AND ", queryFilters));
        }

        TypedQuery<UserResponseDTO> query = this.entityManager.createQuery(
            builder.toString(), UserResponseDTO.class
        );

        params.forEach(query::setParameter);

        return query.getResultList();
    }

}