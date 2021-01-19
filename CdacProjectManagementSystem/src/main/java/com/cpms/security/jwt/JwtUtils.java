package com.cpms.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cpms.services.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * This class has Secret Code and token expiration time as fileds.
 * It has a method to Generate Token on first Login and another method to Verify Token for already Logged In User
 */
@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	/**
	 * JWT Secret (like salt) to encrypt Password - final result store in DB will be encoded Password (Password+jwtSecrete)
	 */
	//@Value("${cpms.jwtSecret}")
	private String jwtSecret;

	/**
	 * Token Expiration time in Milliseconds
	 */
	//@Value("${cpms.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	public JwtUtils() {
		super();
		this.jwtSecret = "CdacPMSSecretKey";				//Salt -- Keep salt as strong as possible for more security
		this.jwtExpirationMs = 360000;								//4Days Approx
	}

	/**
	 * @param authentication	-This Object contains our Credentials as input and Principal as output
	 * @return
	 */
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		return createToken(authentication, userPrincipal);		
	}
	
	private String createToken(Authentication authentication, UserDetailsImpl userPrincipal) {
		Claims claims = Jwts.claims().setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs));
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date()).setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();		// OR SignatureAlgorithm.HS256
	}
	
//	Claims claims = Jwts.claims().setExpiration(new Date(System.currentTimeMillis() + 3600000));
//    * String jwt = Jwts.builder().setClaims(claims).compact();

	public String getEmailFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;		
//			final String username = extractUsername(authToken);
//	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(authToken));
		}  catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: ", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: ", e.getMessage());
		}catch (MalformedJwtException e) {
			logger.error("JWT token Invalid: ", e.getMessage());
		} catch (SignatureException e) { 
			logger.error("Invalid JWT signature: ",  e.getMessage()); 
		}catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: ", e.getMessage());
		}catch(JwtException e) {
			logger.error("JWT  related runtime exception: ", e.getMessage());
		}
		
		return false;
	}
}
