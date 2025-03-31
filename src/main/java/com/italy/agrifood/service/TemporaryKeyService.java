package com.italy.agrifood.service;

import com.italy.agrifood.entity.TemporaryKey;
import com.italy.agrifood.repo.TemporaryKeyRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class TemporaryKeyService {
  private final TemporaryKeyRepo temporaryKeyRepository;

  public TemporaryKeyService(TemporaryKeyRepo temporaryKeyRepository) {
    this.temporaryKeyRepository = temporaryKeyRepository;
  }

  public String generateKey(String role) {
    if (!role.equals("EDITOR") && !role.equals("ADMIN")) {
      throw new IllegalArgumentException("Invalid role");
    }

    String key = generateRandomKey();
    TemporaryKey tempKey = new TemporaryKey(key, role, LocalDateTime.now().plusMinutes(10));
    temporaryKeyRepository.save(tempKey);
    return key;
  }

  public boolean validateKey(String key, String role) {
    Optional<TemporaryKey> tempKey = temporaryKeyRepository.findByKeyValue(key);
    return tempKey.isPresent() && tempKey.get().getRole().equals(role) && !tempKey.get().isExpired();
  }

  private String generateRandomKey() {
    Random random = new Random();
    return String.valueOf(100000 + random.nextInt(900000)); // Generăm un cod de 6 cifre
  }
}
