package pl.twerd.invertersdatareceiver.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

public interface JwtService {

    LocalDateTime getExpirationDateFromToken(String token);
    boolean isTokenExpired(String token);
}
