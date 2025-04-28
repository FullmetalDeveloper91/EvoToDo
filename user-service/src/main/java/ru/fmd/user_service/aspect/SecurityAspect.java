package ru.fmd.user_service.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import ru.fmd.user_service.model.Role;

@Aspect
@Component
public class SecurityAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void isControllerLayer(){}
    @Pointcut("@annotation(ru.fmd.user_service.aspect.AdminOrOwner)")
    public void hasSecureAnnotation(){}

    @Before("isControllerLayer()&&args(login,securityContext,..)")
    public void verifyRights(String login, SecurityContextHolderAwareRequestWrapper securityContext) {
        if(!(login.equals(securityContext.getRemoteUser()) || securityContext.isUserInRole(Role.ADMIN.name()))){
            throw new AuthorizationDeniedException("Owner or Admin has the right to this");
        }
    }
}
