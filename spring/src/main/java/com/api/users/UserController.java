package com.api.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.users.dto.UserCreateRequestDTO;
import com.api.users.dto.UserFilterRequestDTO;
import com.api.users.dto.UserUpdateRequestDTO;

import jakarta.validation.Valid;

import com.api.users.dto.UserResponseDTO;

@RestController
@RequestMapping("/api/users")
public class UserController{

    private final UserService service;

    public UserController(UserService userService){
        this.service = userService;
    }

    /**
     * Create a new user
     * 
     * @param request DTO with data to create a user
     * @return User data as UserResponseDTO
     */
    @PostMapping()
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateRequestDTO request){
        UserResponseDTO response = new UserResponseDTO(this.service.create(request));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get a user
     * 
     * @param id ID of the user retrieve
     * @return User data as UserResponseDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> get(@PathVariable Long id){
        UserResponseDTO response = new UserResponseDTO(this.service.findOrFail(id));

        return ResponseEntity.ok(response);
    }

    /**
     * Get a list of users
     * 
     * @param filters values to filter
     * @return User data as UserResponseDTO
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers(UserFilterRequestDTO filters) {
        
        List<UserModel> users = service.getList(filters);
        List<UserResponseDTO> listDTO = users.stream().map(UserResponseDTO::new).toList();

        return ResponseEntity.ok(listDTO);
    }

    /**
     * Update a user
     * 
     * @param id ID to update user
     * @param Request DTO with data to create a user
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @PathVariable Long id, @RequestBody UserUpdateRequestDTO request) {
        this.service.update(id, request);

        return ResponseEntity.noContent().build();
    }
    
    /**
     * Delete a user
     * 
     * @param id ID ot delete user
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.service.delete(id);

        return ResponseEntity.noContent().build();
    }
}