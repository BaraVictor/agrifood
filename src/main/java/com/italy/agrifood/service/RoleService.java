package com.italy.agrifood.service;

import com.italy.agrifood.entity.Role;
import com.italy.agrifood.repo.RoleRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepo roleRepository;

    @Autowired
    public RoleService(RoleRepo roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Get all roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Find a role by its ID
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + id));
    }

    // Find a role by its name
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with name: " + roleName));
    }

    // Create a new role
    public Role createRole(String roleName) {
        if (roleRepository.findByName(roleName).isPresent()) {
            throw new IllegalArgumentException("Role already exists: " + roleName);
        }

        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    // Delete a role by its ID
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role not found with ID: " + id);
        }
        roleRepository.deleteById(id);
    }
}
