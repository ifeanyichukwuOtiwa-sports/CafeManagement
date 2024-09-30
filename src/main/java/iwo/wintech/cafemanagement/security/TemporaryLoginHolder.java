package iwo.wintech.cafemanagement.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import iwo.wintech.cafemanagement.entity.User;
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
    private final Cache<String, SessionData> logingCache = Caffeine.newBuilder()
            .maximumSize(MAX_SIZE)
            .expireAfterAccess(EXPIRATION)
            .build();

    public String createSession(final User user) {
        final String token = UUID.randomUUID().toString();
        final Instant lastAccessed = Instant.now();
        final SessionData sessionData = new SessionData(user, token, lastAccessed);
        logingCache.put(token, sessionData);
        return token;
    }

    public Optional<User> getTemporaryLogin(final String token) {
        return Optional.ofNullable(token)
                .flatMap(tokes -> Optional.ofNullable(logingCache.getIfPresent(tokes)))
                .map(sessionData -> {
                    logingCache.put(token, sessionData.withLastAccessed(Instant.now()));
                    return sessionData.user;
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

    @With
    private record SessionData(
            User user,
            String token,
            Instant lastAccessed
    ) {

    }
}
