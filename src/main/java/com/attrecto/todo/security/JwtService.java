package com.attrecto.todo.security;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.attrecto.todo.config.TodoConfigProperties;
import com.attrecto.todo.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {

	private static final String ID = "id";
	private static final String FULLNAME = "fullname";
	private static final String AUTH = "auth";
	private Algorithm alg;

	private String issuer;

	@Autowired
	private TodoConfigProperties config;

	@PostConstruct
	public void init() {
		issuer = config.getJwt().getIssuer();
		try {
			alg = (Algorithm) Algorithm.class.getMethod(config.getJwt().getAlg(), String.class).invoke(Algorithm.class,
					config.getJwt().getSecret());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String creatJwtToken(UserDetails principal) {
		User user = ((TodoUser) principal).getUser();

		Builder jwtBuilder = JWT.create().withSubject(principal.getUsername())
				.withArrayClaim(AUTH,
						principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
				.withClaim(FULLNAME, user.getName()).withClaim(ID, user.getId());

		return jwtBuilder.withExpiresAt(new Date(System.currentTimeMillis() + config.getJwt().getDuration().toMillis()))
				.withIssuer(issuer).sign(alg);

	}

	public UserDetails parseJwt(String jwtToken) {

		DecodedJWT decodedJwt = JWT.require(alg).withIssuer(issuer).build().verify(jwtToken);

		User user = new User();
		user.setId(decodedJwt.getClaim(ID).asLong());
		user.setUsername(decodedJwt.getSubject());
		user.setName(decodedJwt.getClaim(FULLNAME).asString());
		
		return new TodoUser(decodedJwt.getSubject(), "dummy", decodedJwt.getClaim(AUTH).asList(String.class).stream()
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList()), user);

	}
}
