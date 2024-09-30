package iwo.wintech.cafemanagement.rest.config;

import iwo.wintech.cafemanagement.dto.AdminDto;
import iwo.wintech.cafemanagement.dto.UserDto;
import iwo.wintech.cafemanagement.exception.ErrorCode;
import iwo.wintech.cafemanagement.security.TemporaryLoginHolder;
import iwo.wintech.cafemanagement.service.mapper.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AdminUserArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String HEADER_NAME = "x-access-admin-token";

    private final TemporaryLoginHolder loginHolder;
    private final Converter converter;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return AdminDto.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public AdminDto resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {

        // get x-pawa-token value from header
        final String token = webRequest.getHeader(HEADER_NAME);

        if (token == null) {
            throw ErrorCode.ADMIN_TOKEN_NOT_PROVIDED.requestException("Token not provided");
        }

        // from temporary login holder get user using token
        return loginHolder.getTemporaryAdminLogin(token)
                .map(converter::convert)
                .orElseThrow(() -> new RuntimeException("Admin not logged in"));
    }
}
