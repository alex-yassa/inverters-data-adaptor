package pl.twerd.invertersdatareceiver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.twerd.invertersdatareceiver.dto.*;
import pl.twerd.invertersdatareceiver.enums.DeviceTypes;
import pl.twerd.invertersdatareceiver.mapper.BsiDataMapper;

@Service
public class LegacyDeviceService {
    private static final WebClient webClient = WebClient.create("http://localhost:8082");
    private static final String SECURITY_BASIC_URL = "/inverters-secure/api";
    private static final String INVERTERS_CORE_BASIC_URL = "/inverters-core/api";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${bsi.user}")
    private transient String bsiUsername;
    @Value("${bsi.password}")
    private transient String bsiPassword;
    private transient String token;
    private transient TokenDto cachedToken;


    private final JwtService jwtService;
    private final BsiDataMapper bsiDataMapper;


    public LegacyDeviceService(JwtService jwtService, BsiDataMapper bsiDataMapper) {
        this.jwtService = jwtService;
        this.bsiDataMapper = bsiDataMapper;
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void handleLegacyDeviceRequest(LegacyDeviceDataDto legacyDeviceDataDto) {

        DeviceTypes deviceType = null;

        try {
        deviceType = DeviceTypes.valueOf(legacyDeviceDataDto.getDevice());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown device type: " + legacyDeviceDataDto.getDevice());
        }

        switch (deviceType) {
            case BSI1000:
                sendBsiData(legacyDeviceDataDto);
                break;
            default:
                throw new IllegalArgumentException("Fail handle request. Unknown device type: " + deviceType);
        }
    }

    public void sendBsiData(LegacyDeviceDataDto legacyDeviceDataDto) {
        System.out.println("Sending data to BSI");
        String token = getToken().getToken();
        System.out.println("token: " + token);
        TestDto testDto = new TestDto("test data");
        String requestBody = null;
        ConvertedBsiData convertedBsiData = bsiDataMapper.mapToConvertedBsiData(legacyDeviceDataDto);

        try {
            requestBody = objectMapper.writeValueAsString(convertedBsiData);
            System.out.println("dataToSend: " + requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Fail to serialize BSI data", e);
        }

        webClient.post()
                .uri(INVERTERS_CORE_BASIC_URL + "/bsi-data")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(UserDto.class)
                .log()
                .block();
    }

    public TokenDto getToken() {

        System.out.println("Getting token from BSI");

        if (cachedToken != null && !jwtService.isTokenExpired(cachedToken.getToken())) {
            return cachedToken;
        }

        String authenticatePostfix = "/auth/authenticate";
        CredentialsDto credentialsDto = new CredentialsDto(bsiUsername, bsiPassword);
        String body = "";
        try {
            body = objectMapper.writeValueAsString(credentialsDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Fail to serialize BSI credentials", e);
        }

        System.out.println("body: " + body);

        TokenDto token = webClient.post()
                .uri(SECURITY_BASIC_URL + authenticatePostfix)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(TokenDto.class)
                .block();

        System.out.println("token: " + token.toString());

        return token;
    }
}
