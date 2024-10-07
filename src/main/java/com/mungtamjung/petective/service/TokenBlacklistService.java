package com.mungtamjung.petective.service;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistService {

    private Set<String> blacklistedTokens = new HashSet<>();

    // 블랙리스트에 토큰 추가
    public void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    // 토큰이 블랙리스트에 있는지 확인
    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
