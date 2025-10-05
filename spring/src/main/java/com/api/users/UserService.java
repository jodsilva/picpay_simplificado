package com.api.users;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import com.api.common.BaseService;
import com.api.enums.EnumCurrency;
import com.api.enums.EnumUsersType;
import com.api.users.dto.UserCreateRequestDTO;
import com.api.users.dto.UserFilterRequestDTO;
import com.api.users.dto.UserResponseDTO;
import com.api.users.dto.UserUpdateRequestDTO;
import com.api.wallets.WalletService;
import com.api.wallets.dto.WalletCreateDTO;

@Service
public class UserService extends BaseService<UserModel>{

    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;
    private final WalletService walletService;  

    public UserService(
        UserRepository repository,
        PasswordEncoder passwordEncoder,
        EntityManager entityManager,
        WalletService walletService
    ) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
        this.walletService = walletService;
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
            BigDecimal.valueOf(0.0), 
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
        var user = this.findOrFail(id);
        user.applyUpdateDTO(userDTO);
        this.repository.save(user);
        return new UserResponseDTO(user);
    }

    /**
     * Delete a user by id
     * @param id
     */
    public void delete(Long id){
        repository.delete(this.findOrFail(id));
    }

    public List<UserModel> getList(UserFilterRequestDTO filters) {
        StringBuilder builder = new StringBuilder("SELECT u FROM UserModel u");

        List<String> queryFilters = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        if (filters.getFullName() != null) {
            queryFilters.add("u.fullName LIKE :fullName");
            params.put("fullName", "%" + filters.getFullName() + "%");
        }
        if (filters.getEmail() != null) {
            queryFilters.add("u.email LIKE :email");
            params.put("email", "%" + filters.getEmail() + "%");
        }
        if (filters.getType() != null) {
            queryFilters.add("u.enumUsersType = :type");
            params.put("type", EnumUsersType.valueOf(filters.getType()));
        }
        if (filters.getTaxId() != null) {
            queryFilters.add("u.taxId LIKE :taxId");
            params.put("taxId", "%" + filters.getTaxId() + "%");
        }

        if (!queryFilters.isEmpty()) {
            builder.append(" WHERE ").append(String.join(" AND ", queryFilters));
        }

        TypedQuery<UserModel> query = entityManager.createQuery(builder.toString(), UserModel.class);
        params.forEach(query::setParameter);

        return query.getResultList();
    }

}