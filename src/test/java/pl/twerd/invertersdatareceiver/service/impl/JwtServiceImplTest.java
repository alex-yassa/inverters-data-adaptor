package pl.twerd.invertersdatareceiver.service.impl;

import org.junit.jupiter.api.Test;
import pl.twerd.invertersdatareceiver.service.JwtService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceImplTest {

    @Test
    void getExpirationDateFromToken() {

        JwtService jwtService = new JwtServiceImpl();

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6W3siaWQiOjQsIm5hbWUiOiJ1c2VyIiwiYXV0aG9yaXR5IjoidXNlciJ9LHsiaWQiOjYsIm5hbWUiOiJyZWdpc3Rlci1jaGFyZ2VyIiwiYXV0aG9yaXR5IjoicmVnaXN0ZXItY2hhcmdlciJ9LHsiaWQiOjQsIm5hbWUiOiJ1c2VyIiwiYXV0aG9yaXR5IjoidXNlciJ9LHsiaWQiOjEsIm5hbWUiOiJtYW5hZ2UtYXV0aG9yaXRpZXMiLCJhdXRob3JpdHkiOiJtYW5hZ2UtYXV0aG9yaXRpZXMifSx7ImlkIjozLCJuYW1lIjoibWFuYWdlLXVzZXJzIiwiYXV0aG9yaXR5IjoibWFuYWdlLXVzZXJzIn0seyJpZCI6NiwibmFtZSI6InJlZ2lzdGVyLWNoYXJnZXIiLCJhdXRob3JpdHkiOiJyZWdpc3Rlci1jaGFyZ2VyIn0seyJpZCI6NSwibmFtZSI6Im1hbmFnZS1kZXZpY2VzIiwiYXV0aG9yaXR5IjoibWFuYWdlLWRldmljZXMifSx7ImlkIjoyLCJuYW1lIjoibWFuYWdlLXJvbGVzIiwiYXV0aG9yaXR5IjoibWFuYWdlLXJvbGVzIn1dLCJzdWIiOiJhbGV4IiwiaWF0IjoxNzA3MjA4MzQ5LCJleHAiOjE3MDcyOTQ3NDl9.Scx0j6yH-FjhyQzWlCFVw6jv2dZ4vNLnDTtGq4gT26JSOLmQVnhApO0oJgSsRp-1v7iw6Sid08YTL6-pko6kQg";

        LocalDateTime actual = jwtService.getExpirationDateFromToken(token);

        LocalDateTime expected = LocalDateTime.of(2024, 2, 7, 9, 32, 29);

        assertEquals(expected, actual);
    }

}