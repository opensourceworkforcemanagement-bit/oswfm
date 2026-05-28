package org.oswfm.userservice.builder;

import org.oswfm.commons.model.user.enums.TokenClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenBuilder {

    public static Claims getValidClaims(String userId, String firstName) {
        Map<String, Object> mockClaimsMap = new HashMap<>();
        mockClaimsMap.put("jti", UUID.randomUUID().toString());
        mockClaimsMap.put("exp", new Date(System.currentTimeMillis() + 30 * 60 * 1000));
        mockClaimsMap.put(TokenClaims.USER_ID.getValue(), Integer.parseInt(userId));
        mockClaimsMap.put(TokenClaims.USER_FIRST_NAME.getValue(), firstName);
        return Jwts.claims(mockClaimsMap);
    }

}
