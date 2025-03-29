package com.italy.agrifood.service;

import com.italy.agrifood.entity.User;
import com.italy.agrifood.entity.Role;
import com.italy.agrifood.exception.EmailNotFoundException;
import com.italy.agrifood.repo.UserRepo;
import com.italy.agrifood.repo.RoleRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean sendPasswordResetEmail(String email) {
        // Log the email address to make sure it's received correctly
        System.out.println("Sending password reset email to: " + email);

        String resetToken = UUID.randomUUID().toString();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the following link: " +
            "http://localhost:8080/reset-password?token=" + resetToken);

        try {
            mailSender.send(message);
            System.out.println("Email sent successfully!");
            return true;
        } catch (MailException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            return false;
        }
    }

    public void createUser(String username, String password, String email, String roleName) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Encode the password
        user.setEmail(email);

        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
        user.setRole(role);

        userRepo.save(user);
    }

    public void updateUser(Long id, User updatedUser, String roleName) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Encode the new password
        }

        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
        user.setRole(role);

        userRepo.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepo.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }


    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return loadUserByEmail(email);
        } catch (EmailNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    public UserDetails loadUserByEmail(String email) throws EmailNotFoundException {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("User not found with username: " + email));
    }
}
