package com.api.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.users.dto.UserCreateRequestDTO;
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
        return ResponseEntity.ok(new UserResponseDTO());
    }

    /**
     * Update a user
     * 
     * @param id ID of the user retrieve
     * @param Request DTO with data to create a user
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable String id, @RequestBody UserUpdateRequestDTO request) {
        return ResponseEntity.noContent().build();
    }

    
    /**
     * Delete a user
     * 
     * @param id ID of the user retrieve
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return ResponseEntity.noContent().build();
    }
}