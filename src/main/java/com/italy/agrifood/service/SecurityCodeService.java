// src/main/java/com/italy/agrifood/service/SecurityCodeService.java
package com.italy.agrifood.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SecurityCodeService {

    private final Map<String, String> codeStorage = new HashMap<>();
    private final Random random = new Random();

    public String generateCode(String email) {
        String code = String.format("%06d", random.nextInt(999999)); // e.g. 027451
        codeStorage.put(email, code);
        return code;
    }

    public boolean validateCode(String email, String code) {
        return code.equals(codeStorage.get(email));
    }

    public void clearCode(String email) {
        codeStorage.remove(email);
    }
}
