package iwo.wintech.cafemanagement.rest.config;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RestConfig implements WebMvcConfigurer {
    private final UserArgumentResolver userArgumentResolver;
    @Override
    public void addArgumentResolvers(@Nonnull final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }
}
