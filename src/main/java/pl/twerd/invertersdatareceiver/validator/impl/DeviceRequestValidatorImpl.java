package pl.twerd.invertersdatareceiver.validator.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.twerd.invertersdatareceiver.validator.DeviceRequestValidator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Component
public class DeviceRequestValidatorImpl implements DeviceRequestValidator {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String STAMP_HASH = "stamp_hash";
    private static final String HASH_ALGORITHM = "SHA-256";

    @Value("${device.validation.key}")
    private String validationKey;

    @Override
    public void validateDeviceRequest(String request) {

        String sourceHash = "";
        String generatedHash ="";

        try {

            Map<String, Object> map = objectMapper.readValue(request, new TypeReference<Map<String, Object>>() {
            });
            sourceHash = map.get(STAMP_HASH).toString();
            map.put(STAMP_HASH, validationKey);
            generatedHash = generateHash(objectMapper.writeValueAsString(map));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (sourceHash.equals("")) {
            throw new IllegalArgumentException("Empty request hash");
        }

        if (!sourceHash.equals(generatedHash)) {
            throw new IllegalArgumentException("Invalid request hash");
        }
    }

    private String generateHash(String dataToHash) {

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to create hash", e);
        }

        byte[] hashBytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
