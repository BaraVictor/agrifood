package com.italy.agrifood.repo;

import com.italy.agrifood.entity.TemporaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporaryKeyRepo extends JpaRepository<TemporaryKey, Long> {
  Optional<TemporaryKey> findByKeyValue(String keyValue);
}
