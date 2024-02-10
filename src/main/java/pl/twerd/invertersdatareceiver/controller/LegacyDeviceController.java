package pl.twerd.invertersdatareceiver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.twerd.invertersdatareceiver.dto.LegacyDeviceDataDto;
import pl.twerd.invertersdatareceiver.service.LegacyDeviceService;

@RestController
@AllArgsConstructor
public class LegacyDeviceController {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final LegacyDeviceService legacyDeviceService;

    @PostMapping(value="/dodajMoc.php", consumes="application/json")
    public void saveDeviceData(@RequestBody LegacyDeviceDataDto legacyDeviceDataDto) {
        legacyDeviceService.handleLegacyDeviceRequest(legacyDeviceDataDto);
    }

    @GetMapping("/")
    public String test() {
        return "test";
    }
}
