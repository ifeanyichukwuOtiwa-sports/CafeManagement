package iwo.wintech.cafemanagement.security.auth;

import iwo.wintech.cafemanagement.security.TemporaryLoginHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CafeMgmtAuthenticationProvider implements AuthenticationProvider {
    private final TemporaryLoginHolder loginHolder;
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof final CafeMgmtAuthentication authRequest)) {
            return null;
        }

        final String token = authRequest.getToken();

        return loginHolder.getTemporaryLogin(token)
                .map(CafeMgmtAuthentication::authenticated)
                .orElseGet(() -> resolveAdminLogin(token));
    }

    private CafeMgmtAuthentication resolveAdminLogin(final String token) {
        return loginHolder.getTemporaryAdminLogin(token)
                .map(CafeMgmtAuthentication::adminAuthenticated)
                .orElseThrow(() -> new BadCredentialsException("Invalid token"));
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return CafeMgmtAuthentication.class.isAssignableFrom(authentication);
    }
}
