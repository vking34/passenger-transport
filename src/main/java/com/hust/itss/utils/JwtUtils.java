package com.hust.itss.utils;

import com.hust.itss.constants.SecurityContants;
import com.hust.itss.models.users.SysUser;
import com.hust.itss.services.CustomUserDetailService;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final String AUTH_SERVICE_ID = "authServiceId";
    private static final String AUTH_PROVIDER = "provider";
    private static final String NAME = "name";
    private static final String ROLE = "role";

    @Value("${security.jwt.expiry-in-milliseconds}")
    private int expiryInMilliSeconds;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public String createTokenForUser(SysUser user){
        System.out.println("secret key: " + secretKey);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim(AUTH_SERVICE_ID, user.getAuthenticationServiceId())
                .claim(AUTH_PROVIDER, user.getAuthProvider())
                .claim(NAME, user.getFullName())
                .setAudience(user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiryInMilliSeconds))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthenticationFromToken(String token, CustomUserDetailService customUserDetailService){
        if(token == null)
            return null;

        String username = null;
        try {
            System.out.println("Decode token with secret key: " + SecurityContants.SECRET_KEY);
            username = Jwts.parser().setSigningKey(SecurityContants.SECRET_KEY)
                    .parseClaimsJws(token.replace(SecurityContants.TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        System.out.println(userDetails);
        return username != null ? new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) : null;
    }
}
