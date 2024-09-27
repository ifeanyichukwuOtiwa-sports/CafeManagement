package iwo.wintech.cafemanagement.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Component
public class CafeMgmtAuthenticationFilter extends AuthenticationFilter {
    private static final String HEADER_NAME = "x-access-token";
    private final ObjectMapper mapper;

    public CafeMgmtAuthenticationFilter(final AuthenticationManager authenticationManager, final ObjectMapper mapper) {
        super(authenticationManager, new CafeMgmtAuthenticationConverter());
        this.mapper = mapper;
        setSuccessHandler(this::handleSuccess);
        setFailureHandler(this::handleFailure);
    }

    private void handleSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("User authenticated successfully: {}", authentication.getPrincipal());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.addCookie(new Cookie("x-access-accepted", "true"));
        response.addCookie(new Cookie("x-access-timestamp", Instant.now().toString()));
    }

    private void handleFailure(HttpServletRequest request, HttpServletResponse response,
                               AuthenticationException e) throws IOException {
        log.error("Authentication failed: {}", e.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        mapper.writeValue(response.getWriter(), ErrorDetails.unAuthorized(e.getMessage(), request.getRequestURI()));
        response.addCookie(new Cookie("x-access-rejected", "true"));
    }


    private static class CafeMgmtAuthenticationConverter implements AuthenticationConverter {
        @Override
        public Authentication convert(final HttpServletRequest request) {
            final String token = request.getHeader(HEADER_NAME);
            return Optional.ofNullable(token)
                    .map(CafeMgmtAuthentication::unAuthentication)
                    .orElse(null);
        }
    }

    private static record ErrorDetails(
            String timestamp,
            int status,
            String error,
            String message,
            String path
    ) {

        public static ErrorDetails unAuthorized(final String message, final String path) {
            return new ErrorDetails(Instant.now().toString(), HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized", message, path);
        }
    }
}
