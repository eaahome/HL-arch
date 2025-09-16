package name.erzin.learn.hl.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    SecurityProvider securityProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                if (isValidToken(token)) {
                    String userId = extractUserIdFromToken(token);

                    // TODO
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userId, null, getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // TODO
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                logger.warn("No Bearer header found or it invalid format or empty");
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication", e);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isValidToken(String token) {
        boolean isValidToken = securityProvider.isValidJwt(token);
        logger.warn("Token is valid: " + isValidToken);
        return isValidToken;
    }

    // TODO
    private String extractUserIdFromToken(String token) {
        // Реализуйте извлечение user ID из токена
        // Пример для JWT: Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        return securityProvider.extractLogin(token);
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}