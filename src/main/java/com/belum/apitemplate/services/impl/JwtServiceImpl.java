package com.belum.apitemplate.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.belum.apitemplate.domain.ClientInfo;
import com.belum.apitemplate.services.JwtService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Created by bel-sahn on 7/31/19
 */
@Service
public class JwtServiceImpl implements JwtService {
//region PROPERTIES
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Environment env;
//endregion

//region CONSTRUCTORS
    public JwtServiceImpl(Environment env) {
        this.env = env;
    }
//endregion

//region HELPER MTHODSE
    @Override
    public String createJwt(ClientInfo payload) throws UnsupportedEncodingException {
        Validate.notNull(payload);
        Validate.notBlank(payload.getClientId());
        Validate.notBlank(payload.getClientSecret());

        final String token = JWT.create()
                .withSubject(payload.getSubject())
                .withIssuer(payload.getIssuer())
                .withIssuedAt(Date.valueOf(LocalDate.now()))
                .withExpiresAt(Date.valueOf(LocalDate.now().plus(5L, ChronoUnit.MINUTES)))
                .sign(getAlgorithm());

        LOGGER.info("Created Token :: {}", token);
        return token;
    }

    @Override
    public ClientInfo decodeJwt(String token) throws UnsupportedEncodingException {
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        verifier.verify(token);

        final ClientInfo clientInfo = this.clientInfo(token);
        LOGGER.debug("Decoded JWT :: {}", clientInfo);
        return clientInfo;
    }

    private ClientInfo clientInfo(String token) {
        final DecodedJWT decodedJWT = JWT.decode(token);
        return new ClientInfo("",
                "",
                decodedJWT.getIssuer(),
                decodedJWT.getSubject(),
                decodedJWT.getIssuedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                decodedJWT.getExpiresAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256("");
    }
//endregion
}
