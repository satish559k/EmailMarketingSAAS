package com.example.EmailMarketingSAAS.security;

import com.example.EmailMarketingSAAS.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailService customUserDetailsService;

    private JwtUtil jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String lAuthHeader = request.getHeader("Authorization");

            if(lAuthHeader != null && lAuthHeader.startsWith("Bearer ")) {
                String lToken = lAuthHeader.substring(7).trim();
                String UserId = jwtUtils.getUserNameFromToken(lToken);

                if(UserId != null && SecurityContextHolder.getContext().getAuthentication()==null){
                    if(jwtUtils.validateToken(lToken)&&!jwtUtils.isTokenExpired(lToken)) {
                        UserDetails userDetails = customUserDetailsService.loadUserByUsername(UserId.trim());
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }else{
                        log.info("Token validation failed");
                        //System.out.println("Token validation failed");
                    }
                }
            }else {
                log.info("No Authorization header or does not start with Bearer");
                //System.out.println("No Authorization header or does not start with Bearer");
            }
        }
        catch (io.jsonwebtoken.ExpiredJwtException ex) {
            // Token is expired → send 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token has expired\"}");
            log.error("Error"+ex);
            return;
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"Error\": \"while Authenticating JWT Tokens\"}");
            log.error("Error"+e);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
