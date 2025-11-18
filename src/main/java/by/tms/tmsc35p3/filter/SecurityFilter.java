package by.tms.tmsc35p3.filter;

import by.tms.tmsc35p3.entity.Account;
import by.tms.tmsc35p3.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Value("fjwertisncmserqiplksccntrioeksjqfsd")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

            Jws<Claims> claimsJws;

            try {
                claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt);
            }catch (Exception e) {
                throw new InternalAuthenticationServiceException("Invalid JWT token" + e.getMessage(), e);
            }

            if (claimsJws.getBody().getExpiration().after(new Date())){

                Account account = new Account();
                account.setUsername(claimsJws.getBody().getSubject());
                account.setName(claimsJws.getBody().get("name", String.class));
                account.setRoles(Set.of(Role.valueOf(claimsJws.getBody().get("role", String.class))));


                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(account, jwt, account.getRoles());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                throw new CredentialsExpiredException("Token expired");
            }
        }


        //
        //
        filterChain.doFilter(request, response);
    }
}
