package ru.fmd.task_service.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ru.fmd.task_service.repository.UserServiceDao;
import ru.fmd.task_service.service.JwtService;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final String BEARER_PREFIX = "Bearer ";
    private final String ROLE_PREFIX = "ROLE_";
    private final JwtService jwtService;
    private final UserServiceDao userServiceDao;

    private final HandlerExceptionResolver exceptionResolver;

    public JwtAuthFilter(
            JwtService jwtService,
            UserServiceDao userServiceDao,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.jwtService = jwtService;
        this.userServiceDao = userServiceDao;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal (
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
        ) throws ServletException, IOException, io.jsonwebtoken.JwtException {

        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
                String token = authHeader.substring(BEARER_PREFIX.length());
                String username = jwtService.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var userDetails = userServiceDao.getUserByLogin(username, token).getBody();

                    if (userDetails != null && jwtService.isValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                List.of((GrantedAuthority) () -> ROLE_PREFIX + userDetails.getRole().name())
                        );

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
        }catch (Exception ex){
            exceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
