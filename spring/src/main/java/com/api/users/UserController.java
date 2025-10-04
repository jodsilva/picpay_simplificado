package com.api.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.users.dto.UserCreateRequestDTO;
import com.api.users.dto.UserFilterRequestDTO;
import com.api.users.dto.UserUpdateRequestDTO;
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
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserCreateRequestDTO request){
        UserResponseDTO user = this.service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * Get a user
     * 
     * @param id ID of the user retrieve
     * @return User data as UserResponseDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> get(@PathVariable Long id){
        return ResponseEntity.ok(this.service.get(id));
    }

    /**
     * Get a list of users
     * 
     * @param id ID of the user retrieve
     * @return User data as UserResponseDTO
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> list(UserFilterRequestDTO filters){
        return ResponseEntity.ok(this.service.getList(filters));
    }

    /**
     * Update a user
     * 
     * @param id ID to update user
     * @param Request DTO with data to create a user
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UserUpdateRequestDTO request) {
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