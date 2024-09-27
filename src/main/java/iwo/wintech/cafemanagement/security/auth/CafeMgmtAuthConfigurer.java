package iwo.wintech.cafemanagement.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CafeMgmtAuthConfigurer extends AbstractHttpConfigurer<CafeMgmtAuthConfigurer, HttpSecurity> {
    private final CafeMgmtAuthenticationProvider authenticationProvider;
    private final CafeMgmtAuthenticationFilter authenticationFilter;

    @Override
    public void init(final HttpSecurity builder) throws Exception {
        builder.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(final HttpSecurity builder) throws Exception {
        builder.addFilterBefore(
                authenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

    }
}
