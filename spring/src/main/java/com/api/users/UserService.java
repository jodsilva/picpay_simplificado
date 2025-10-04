package com.api.users;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

import com.api.users.dto.UserCreateRequestDTO;
import com.api.users.dto.UserResponseDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;

    /**
     * Create a new user using a data as UserCreateQuestDTO
     * 
     * @param userDTO
     * @return UserResponseDTO
     */
    public UserResponseDTO create(UserCreateRequestDTO userDTO){
        UserModel user = new UserModel(userDTO);
        this.repository.save(user);
        return new UserResponseDTO(user);
    }
}