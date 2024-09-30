package iwo.wintech.cafemanagement.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import iwo.wintech.cafemanagement.entity.User;
import iwo.wintech.cafemanagement.entity.admin.AdminUser;
import lombok.With;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class TemporaryLoginHolder {
    private static final Duration EXPIRATION = Duration.ofMinutes(30);
    private static final long MAX_SIZE = 1000;
    private final Cache<String, SessionData<User>> logingCache = Caffeine.newBuilder()
            .maximumSize(MAX_SIZE)
            .expireAfterAccess(EXPIRATION)
            .build();
    private final Cache<String, SessionData<AdminUser>> adminLoadingCache = Caffeine.newBuilder()
            .maximumSize(MAX_SIZE)
            .expireAfterAccess(EXPIRATION)
            .build();

    public String createSession(final User user) {
        final String token = UUID.randomUUID().toString();
        final Instant lastAccessed = Instant.now();
        final SessionData<User> sessionData = new SessionData<>(user, token, lastAccessed);
        logingCache.put(token, sessionData);
        return token;
    }



    public Optional<User> getTemporaryLogin(final String token) {
        return Optional.ofNullable(token)
                .flatMap(tokes -> Optional.ofNullable(logingCache.getIfPresent(tokes)))
                .map(sessionData -> {
                    logingCache.put(token, sessionData.withLastAccessed(Instant.now()));
                    return sessionData.user();
                });
    }

    public void logout(final String token) {
        logingCache.invalidate(token);
    }

    public boolean validateToken(final String token) {
        return logingCache.getIfPresent(token) != null;
    }

    public void invalidate(final User user) {
        logingCache.asMap()
                .entrySet()
                .removeIf(entry -> Objects.equals(entry.getValue().user(), user));

    }

    public Optional<AdminUser> getTemporaryAdminLogin(final String token) {
        return Optional.ofNullable(token)
               .flatMap(tokes -> Optional.ofNullable(adminLoadingCache.getIfPresent(tokes)))
               .map(sessionData -> {
                   adminLoadingCache.put(token, sessionData.withLastAccessed(Instant.now()));
                    return sessionData.user();
                });
    }

    public void invalidateAdmin(final AdminUser adminUser) {
        adminLoadingCache.asMap()
               .entrySet()
               .removeIf(entry -> Objects.equals(entry.getValue().user(), adminUser));
    }

    public String createAdminSession(final AdminUser adminUser) {
        final String token = UUID.randomUUID().toString();
        final Instant lastAccessed = Instant.now();
        final SessionData<AdminUser> sessionData = new SessionData<>(adminUser, token, lastAccessed);
        adminLoadingCache.put(token, sessionData);
        return token;
    }

    @With
    private record SessionData<T>(
            T user,
            String token,
            Instant lastAccessed
    ) {

    }
}
