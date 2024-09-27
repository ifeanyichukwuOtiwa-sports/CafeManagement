package iwo.wintech.cafemanagement.security.auth;

import iwo.wintech.cafemanagement.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;


@EqualsAndHashCode(callSuper = true)
@Getter
public class CafeMgmtAuthentication extends AbstractAuthenticationToken {
    private final User user;
    private String token;

    public CafeMgmtAuthentication(final User user) {
        super(user != null ? AuthorityUtils.createAuthorityList(user.getRole()) : AuthorityUtils.NO_AUTHORITIES);
        this.user = user;
        this.token = null;
    }

    public static Authentication unAuthentication(final String token) {
        final CafeMgmtAuthentication cafeMgmtAuthentication = new CafeMgmtAuthentication(null);
        cafeMgmtAuthentication.token = token;
        cafeMgmtAuthentication.setAuthenticated(false);
        return cafeMgmtAuthentication;
    }

    public static CafeMgmtAuthentication authenticated(final User user) {
        final CafeMgmtAuthentication cafeMgmtAuthentication = new CafeMgmtAuthentication(user);
        cafeMgmtAuthentication.setAuthenticated(true);
        return cafeMgmtAuthentication;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        final String maskedUserPassword = user.getMaskedUserPassword();
        return user.withPassword(maskedUserPassword);
    }
}
