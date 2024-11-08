package com.italy.agrifood.service;

import com.italy.agrifood.entity.User;
import com.italy.agrifood.entity.Role;
import com.italy.agrifood.repo.UserRepo;
import com.italy.agrifood.repo.RoleRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(String username, String password, String email, String roleName) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Encode the password
        user.setEmail(email);

        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
        user.setRole(role);

        userRepo.save(user); // Save the user to the database
    }

    public void updateUser(Long id, User updatedUser, String roleName) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());

        // Only encode and set the password if it is provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Encode the new password
        }

        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
        user.setRole(role);

        userRepo.save(user); // Save the updated user to the database
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepo.deleteById(id); // Delete the user by id
    }

    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public List<User> getAllUsers() {
        return userRepo.findAll(); // Return all users
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
