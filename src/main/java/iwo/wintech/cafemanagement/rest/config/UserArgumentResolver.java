package iwo.wintech.cafemanagement.rest.config;

import iwo.wintech.cafemanagement.dto.UserDto;
import iwo.wintech.cafemanagement.security.TemporaryLoginHolder;
import iwo.wintech.cafemanagement.service.mapper.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String HEADER_NAME = "x-pawa-token";

    private final TemporaryLoginHolder loginHolder;
    private final Converter converter;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return UserDto.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public UserDto resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {

        // get x-pawa-token value from header
        final String token = webRequest.getHeader(HEADER_NAME);

        if (token == null) {
            throw new RuntimeException("Token not provided");
        }

        // from temporary login holder get user using token
        return loginHolder.getTemporaryLogin(token)
                .map(converter::convert)
                .orElseThrow(() -> new RuntimeException("User not logged in"));
    }
}
