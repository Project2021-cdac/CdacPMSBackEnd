package com.cpms.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
	private String JWT_SECRET_CODE = "CdacPMSSecretKey";					//Salt -- Keep salt as strong as possible for more security

	/**
	 * Token Expiration time in Milliseconds
	 */
	//@Value("${cpms.jwtExpirationMs}")
	private int JWT_EXPIRATION_MS = 36000000;		
	
	public static final String ROLES = "ROLES";

	/**
	 * @param authentication	-This Object contains our Credentials as input and Principal as output
	 * @return
	 */

	//generate token for user
    public String generateToken(Authentication authentication) {
        final Map<String, Object> claims = new HashMap<>();
        final UserDetails user = (UserDetails) authentication.getPrincipal();
        //List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		//List<Role> rl =  Arrays.asList(user.getRole());
		//List<Role> roles = Arrays.asList(userAccount.getRole());
//		Role role = user.getRole();
//		 authorities.add(new SimpleGrantedAuthority(role.toString()));
//	    for (Role role: roles) {
//	        authorities.add(new SimpleGrantedAuthority(role.toString()));
//	    }
        final List<String> roles = authentication.getAuthorities()
                                                 .stream()
                                                 .map(GrantedAuthority::getAuthority)
                                                 .collect(Collectors.toList());

        claims.put(ROLES, roles);
        return createToken(claims, user.getUsername());
    }
	
//	public String generateToken(UserDetails userDetails) {
//		Map<String, Object> claims = new HashMap<>();
//		return createToken(claims, userDetails.getUsername());
//	}

	private String createToken(Map<String, Object> claims, String subject) {
		final long now = System.currentTimeMillis();
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				//.setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
				.setExpiration(new Date(now + JWT_EXPIRATION_MS * 1000))
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET_CODE)
				.compact();
	}
	public String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(JWT_SECRET_CODE).parseClaimsJws(token).getBody();
	}


	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token/* , UserDetails userDetails */) {
		try {
			final String email = extractEmail(token);
			//return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));	
			//final String username = getUsernameFromToken(token);
	        return email != null && !isTokenExpired(token);
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
