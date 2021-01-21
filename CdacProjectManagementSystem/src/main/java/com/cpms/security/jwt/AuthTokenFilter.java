package com.cpms.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cpms.exception_handler.CustomException;
import com.cpms.services.UserDetailsServiceImpl;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, CustomException {	
		
		try {
			String jwt = parseJwt(request);
//			if(jwt == null) {
//				throw new CustomException("JWT String argument cannot be null.");
//			}
			String email = jwtUtils.extractEmail(jwt);
			
//			System.out.println("UserDetailsServiceImpl:::doFilterInternal::: JWT - " + jwt);
//			System.out.println("Email From Token: " + email);
			
			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
				//System.out.println("USERDETAILS:: " + userDetails);
				if (jwtUtils.validateToken(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		} catch (Exception e) {
			e.getMessage();
			logger.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);	
	}

		private String parseJwt(HttpServletRequest request) {
			final String authorizationHeader = request.getHeader("Authorization");
	
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				return authorizationHeader.substring(7);
			}
	
			return null;
		}
}
