package pl.twerd.invertersdatareceiver.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Service;
import pl.twerd.invertersdatareceiver.service.JwtService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public LocalDateTime getExpirationDateFromToken(String jwtToken) {

        String[] jwtParts = jwtToken.split("\\.");
        String encodedPayload = jwtParts[1];

        // Decode the payload
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedPayload);
        String payloadJson = new String(decodedBytes);

        LocalDateTime expirationDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault());

        // Parse the payload JSON
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JwtPayload payload = objectMapper.readValue(payloadJson, JwtPayload.class);

            expirationDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(payload.getExp()), ZoneId.systemDefault());
        } catch (Exception e) {
            new IllegalArgumentException("Invalid JWT token.");
        }

        return expirationDate;
    }

    @Override
    public boolean isTokenExpired(String token) {
        LocalDateTime expirationDate = getExpirationDateFromToken(token);
        return expirationDate.isBefore(LocalDateTime.now());
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class JwtPayload {
        private String sub; // Subject
        private Long exp;   // Expiration time (Unix timestamp)
    }
}
