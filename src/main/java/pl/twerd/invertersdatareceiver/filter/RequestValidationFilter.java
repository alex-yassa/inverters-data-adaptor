package pl.twerd.invertersdatareceiver.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.twerd.invertersdatareceiver.validator.DeviceRequestValidator;

import java.io.BufferedReader;
import java.io.IOException;

@Component
@AllArgsConstructor
public class RequestValidationFilter extends OncePerRequestFilter {

    private final DeviceRequestValidator deviceRequestValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("RequestValidationFilter");

        StringBuilder payload = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            payload.append(line);
        }

        deviceRequestValidator.validateDeviceRequest(payload.toString());

        filterChain.doFilter(request, response);
    }
}
