package com.italy.agrifood.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TemporaryKey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String keyValue;

  @Column(nullable = false)
  private String role;

  @Column(nullable = false)
  private LocalDateTime expirationTime;

  public TemporaryKey() {}

  public TemporaryKey(String keyValue, String role, LocalDateTime expirationTime) {
    this.keyValue = keyValue;
    this.role = role;
    this.expirationTime = expirationTime;
  }

  public String getKeyValue() {
    return keyValue;
  }

  public String getRole() {
    return role;
  }

  public LocalDateTime getExpirationTime() {
    return expirationTime;
  }

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(expirationTime);
  }
}
