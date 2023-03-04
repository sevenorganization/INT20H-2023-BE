package org.sevenorganization.int20h2023be.security.filter;

import org.sevenorganization.int20h2023be.security.service.DefaultUserDetailsService;
import org.sevenorganization.int20h2023be.security.service.JwtService;
import org.sevenorganization.int20h2023be.security.userdetails.DefaultUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
//@RequiredArgsConstructor
//To avoid circular dependency
//TODO - should be refactored
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private DefaultUserDetailsService defaultUserDetailsService;
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = extractAccessTokenFromAuthorizationHeader(request);
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtService.getSubjectFromAccessToken(accessToken);
        if (jwtService.validateAccessToken(accessToken) && SecurityContextHolder.getContext().getAuthentication() == null) {
            DefaultUserDetails defaultUserDetails = (DefaultUserDetails) defaultUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(defaultUserDetails, null, defaultUserDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    private String extractAccessTokenFromAuthorizationHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.split(" ")[1];
        } else if (request.getRequestURI().contains("oauth2")){
//            Object tokenAttr = request.getSession().getAttribute(OAUTH2_JWT_TOKEN_PASS_NAME) ;
//            if (Objects.nonNull(tokenAttr)) {
//                return (String) tokenAttr;
//            }
            String token = request.getParameter("token");
            if (StringUtils.hasText(token)) {
                return token;
            }
        }
        return null;
    }
}
